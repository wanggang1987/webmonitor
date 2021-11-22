/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.count;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wang.webmonitor.FuncUtils;
import org.wang.webmonitor.error.ErrorMapper;
import org.wang.webmonitor.error.ErrorPO;
import org.wang.webmonitor.event.EventMapper;
import org.wang.webmonitor.event.EventPO;
import org.wang.webmonitor.event.EventType;
import org.wang.webmonitor.visit.VisitMapper;
import org.wang.webmonitor.visit.VisitPO;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

/**
 *
 * @author gangwang
 */
@Slf4j
@Service
public class CountService {

    @Autowired
    private CountMapper countMapper;
    @Autowired
    private VisitMapper visitMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private ErrorMapper errorMapper;

    @Scheduled(fixedRate = 1000 * 60)
    private void count() {
        log.debug("start to count todayString:{} getStartTime:{} getEndTime:{}",
                FuncUtils.todayString(), FuncUtils.todayStartTime(), FuncUtils.todayEndTime());
        countPerformance();
        countPageDuration();
        countPageViewtimes();
        countError();
    }

    private void insertCount(String tag, Integer n) {
        //countMapper.updateCount("performance", todayString(), avg.longValue(), 0L); h2数据库不支持ON DUPLICATE KEY UPDATE
        CountPO cpo = countMapper.selectOneByExample(Example.builder(CountPO.class)
                .where(Sqls.custom()
                        .andEqualTo("tag", tag)
                        .andEqualTo("date", FuncUtils.todayString()))
                .build());
        if (cpo == null) {
            CountPO newOne = new CountPO();
            newOne.setTag(tag);
            newOne.setDate(FuncUtils.todayString());
            newOne.setNumber(n);
            countMapper.insert(newOne);
        } else {
            cpo.setNumber(n);
            countMapper.updateByPrimaryKey(cpo);
        }

    }

    private void countPerformance() {
        List<VisitPO> vpos = visitMapper.selectByExample(Example.builder(VisitPO.class)
                .select("total")
                .where(Sqls.custom()
                        .andBetween("time", FuncUtils.todayStartTime(), FuncUtils.todayEndTime()))
                .orderByDesc("id")
                .build());
        log.debug("find {} vpos", vpos.size());
        Double avg = vpos.stream().map(v -> v.getTotal()).filter(t -> (t != null && t >= 0))
                .mapToInt(Long::intValue).average().orElse(0);
        insertCount(countTag.performance.toString(), avg.intValue());
    }

    private void addMap(Map<String, Integer> mapDuration, Map<String, Integer> mapTimes, String module, Long time) {
        if (!mapDuration.containsKey(module)) {
            mapDuration.put(module, 0);
        }
        mapDuration.put(module, mapDuration.get(module) + time.intValue());

        if (!mapTimes.containsKey(module)) {
            mapTimes.put(module, 0);
        }
        mapTimes.put(module, mapTimes.get(module) + 1);
        log.debug("add {} {}", module, time);
    }

    private void countPageDuration() {
        List<EventPO> epos = eventMapper.selectByExample(Example.builder(EventPO.class)
                .where(Sqls.custom()
                        .andBetween("time", FuncUtils.todayStartTime(), FuncUtils.todayEndTime()))
                .orderBy("id")
                .build());
        log.debug("find {} epos", epos.size());

        Map<String, Integer> mapDuration = new HashMap<>();
        Map<String, Integer> mapTimes = new HashMap<>();

        Map<String, List<EventPO>> guidMap = epos.stream().collect(Collectors.groupingBy(EventPO::getGuid));
        guidMap.keySet().forEach(guid -> {
            List<EventPO> gEpos = guidMap.get(guid);
            Stack<EventPO> stack = new Stack();
            gEpos.forEach(epo -> {
                if (!stack.isEmpty()) {
                    EventPO pre = stack.peek();
                    String module = pre.getModule();
                    Long time = epo.getTime().getTime() - pre.getTime().getTime();
                    addMap(mapDuration, mapTimes, module, time);
                }
                stack.push(epo);
            });
            String module = countTag.pageView.toString();
            Long time = gEpos.get(gEpos.size() - 1).getTime().getTime() - gEpos.get(0).getTime().getTime();
            addMap(mapDuration, mapTimes, module, time);
        });

        mapDuration.keySet().forEach(module -> insertCount(module + countTag.Duration.toString(), mapDuration.get(module)));
        mapTimes.keySet().forEach(module -> insertCount(module + countTag.Times.toString(), mapTimes.get(module)));
    }

    private void countPageViewtimes() {
        Integer count = eventMapper.selectCountByExample(Example.builder(EventPO.class)
                .select("id")
                .where(Sqls.custom()
                        .andEqualTo("type", EventType.ENTER_PAGE)
                        .andBetween("time", FuncUtils.todayStartTime(), FuncUtils.todayEndTime()))
                .build());
        log.debug("find {} countPageViewtimes", count);
        insertCount(countTag.pageViewTimes.toString(), count);
    }

    private void countError() {
        Integer count = errorMapper.selectCountByExample(Example.builder(ErrorPO.class)
                .select("id")
                .where(Sqls.custom()
                        .andBetween("time", FuncUtils.todayStartTime(), FuncUtils.todayEndTime()))
                .build());
        log.debug("find {} countError", count);
        insertCount(countTag.error.toString(), count);
    }

    public List<CountPO> getTodayCount() {
        List<CountPO> cpos = countMapper.selectByExample(Example.builder(CountPO.class)
                .where(Sqls.custom()
                        .andEqualTo("date", FuncUtils.todayString()))
                .build());
        log.debug("find {} today cpos", cpos.size());
        return cpos;
    }
}
