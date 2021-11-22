/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.manage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wang.webmonitor.FuncUtils;
import org.wang.webmonitor.count.Count7Days;
import org.wang.webmonitor.count.CountMapper;
import org.wang.webmonitor.count.CountModules;
import org.wang.webmonitor.count.CountPO;
import org.wang.webmonitor.count.CountTodayYestoday;
import org.wang.webmonitor.count.countTag;
import org.wang.webmonitor.visit.User;
import org.wang.webmonitor.visit.VisitMapper;
import org.wang.webmonitor.visit.VisitPO;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

/**
 *
 * @author wanggang
 */
@Slf4j
@Service
public class ManageService {

    @Autowired
    private VisitMapper visitMapper;
    @Autowired
    private CountMapper countMapper;

    public User selectUser() {
        PageHelper.startPage(0, 1);
        VisitPO vpo = visitMapper.selectOneByExample(Example.builder(VisitPO.class)
                .orderByDesc("id")
                .build());
        if (BeanUtil.isEmpty(vpo)) {
            log.debug("select {} VisitPO", 0);
            return null;
        }
        log.debug("select {} VisitPO", 1);
        User user = new User();
        BeanUtil.copyProperties(vpo, user);
        return user;
    }

    private void findAndAddForDate(List<CountPO> from, countTag tag, String date, List<CountPO> to) {
        CountPO cpo = from.stream()
                .filter(c -> c.getTag().equals(tag.toString()) && c.getDate().equals(date))
                .findAny().orElse(null);
        if (BeanUtil.isNotEmpty(cpo)) {
            to.add(new CountPO(tag.toString(), date, cpo.getNumber()));
        } else {
            to.add(new CountPO(tag.toString(), date, 0));
        }
    }

    public CountTodayYestoday getCountTodayYestoday() {
        CountTodayYestoday count = new CountTodayYestoday();
        count.setToday(new ArrayList<>());
        count.setYestoday(new ArrayList<>());
        List<countTag> tagList = new ArrayList<>();
        tagList.add(countTag.performance);
        tagList.add(countTag.pageViewDuration);
        tagList.add(countTag.pageViewTimes);
        tagList.add(countTag.error);

        List<String> dateList = new ArrayList<>();
        dateList.add(FuncUtils.todayString());
        dateList.add(FuncUtils.yestoryString());

        List<CountPO> cpos = countMapper.selectByExample(Example.builder(CountPO.class)
                .where(Sqls.custom()
                        .andIn("tag", tagList)
                        .andIn("date", dateList))
                .build());
        log.info("select {} for {} find {}", JSONUtil.toJsonStr(tagList), JSONUtil.toJsonStr(dateList), cpos.size());
        findAndAddForDate(cpos, countTag.performance, FuncUtils.todayString(), count.getToday());
        findAndAddForDate(cpos, countTag.pageViewDuration, FuncUtils.todayString(), count.getToday());
        findAndAddForDate(cpos, countTag.pageViewTimes, FuncUtils.todayString(), count.getToday());
        findAndAddForDate(cpos, countTag.error, FuncUtils.todayString(), count.getToday());
        findAndAddForDate(cpos, countTag.performance, FuncUtils.yestoryString(), count.getYestoday());
        findAndAddForDate(cpos, countTag.pageViewDuration, FuncUtils.yestoryString(), count.getYestoday());
        findAndAddForDate(cpos, countTag.pageViewTimes, FuncUtils.yestoryString(), count.getYestoday());
        findAndAddForDate(cpos, countTag.error, FuncUtils.yestoryString(), count.getYestoday());

        return count;
    }

    public Count7Days getCount7Days() {
        Count7Days count = new Count7Days();
        List<String> dateList = FuncUtils.list7dayString();

        count.setDuration(countMapper.selectByExample(Example.builder(CountPO.class)
                .where(Sqls.custom()
                        .andEqualTo("tag", countTag.pageViewDuration)
                        .andIn("date", dateList))
                .build()));
        count.setTimes(countMapper.selectByExample(Example.builder(CountPO.class)
                .where(Sqls.custom()
                        .andEqualTo("tag", countTag.pageViewTimes)
                        .andIn("date", dateList))
                .build()));
        return count;
    }

    public CountModules getCountModules() {
        CountModules count = new CountModules();
        List<CountPO> cpos = countMapper.selectByExample(Example.builder(CountPO.class)
                .where(Sqls.custom()
                        .andEqualTo("date", FuncUtils.todayString()))
                .build());

        count.setDuration(cpos.stream()
                .filter(c -> c.getTag().contains(countTag.Duration.toString()))
                .filter(c -> !c.getTag().contains("page"))
                .map(c -> {
                    c.setTag(c.getTag().replace(countTag.Duration.toString(), ""));
                    return c;
                })
                .collect(Collectors.toList()));

        count.setTimes(cpos.stream()
                .filter(c -> c.getTag().contains(countTag.Times.toString()))
                .filter(c -> !c.getTag().contains("page"))
                .map(c -> {
                    c.setTag(c.getTag().replace(countTag.Times.toString(), ""));
                    return c;
                })
                .collect(Collectors.toList()));

        return count;
    }
}
