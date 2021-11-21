/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.count;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
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

    private String performance = "performance";

    @Autowired
    private CountMapper countMapper;
    @Autowired
    private VisitMapper visitMapper;

    private String todayString() {
        Calendar today = Calendar.getInstance();
        TimeZone zone = TimeZone.getTimeZone("GMT+8:00");
        today.setTimeZone(zone);
        Date currentTime = today.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(currentTime);
    }

    private static Date getStartTime() {
        Calendar today = Calendar.getInstance();
        TimeZone zone = TimeZone.getTimeZone("GMT+8:00");
        today.setTimeZone(zone);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today.getTime();
    }

    private static Date getEndTime() {
        Calendar today = Calendar.getInstance();
        TimeZone zone = TimeZone.getTimeZone("GMT+8:00");
        today.setTimeZone(zone);
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);
        today.set(Calendar.MILLISECOND, 999);
        return today.getTime();
    }

    @Scheduled(fixedRate = 1000 * 60)
    private void count() {
        log.debug("start to count todayString:{} getStartTime:{} getEndTime:{}", todayString(), getStartTime(), getEndTime());
        countPerformance();
    }

    private void countPerformance() {
        List<VisitPO> vpos = visitMapper.selectByExample(Example.builder(VisitPO.class)
                .where(Sqls.custom()
                        .andBetween("time", getStartTime(), getEndTime()))
                .orderByDesc("id")
                .build());
        log.debug("find {} vpos", vpos.size());
        Double avg = vpos.stream().map(v -> v.getTotal()).filter(t -> t >= 0).mapToInt(Long::intValue).average().orElse(0);

        //countMapper.updateCount("performance", todayString(), avg.longValue(), 0L); h2数据库不支持ON DUPLICATE KEY UPDATE
        CountPO cpo = countMapper.selectOneByExample(Example.builder(CountPO.class)
                .where(Sqls.custom()
                        .andEqualTo("tag", performance)
                        .andEqualTo("date", todayString()))
                .orderByDesc("id")
                .build());
        if (cpo == null) {
            CountPO newOne = new CountPO();
            newOne.setTag(performance);
            newOne.setDate(todayString());
            newOne.setNumber(avg.longValue());
            countMapper.insert(newOne);
        } else {
            cpo.setNumber(avg.longValue());
            countMapper.updateByPrimaryKey(cpo);
        }
    }

    public List<CountPO> getTodayCount() {
        List<CountPO> cpos = countMapper.selectByExample(Example.builder(CountPO.class)
                .where(Sqls.custom()
                        .andEqualTo("date", todayString()))
                .build());
        log.debug("find {} today cpos", cpos.size());
        return cpos;
    }
}
