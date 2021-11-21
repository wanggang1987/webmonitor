/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.manage;

import org.wang.webmonitor.count.CountTodayYestoday;
import com.github.pagehelper.PageInfo;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wang.webmonitor.count.Count7Days;
import org.wang.webmonitor.count.CountModules;
import org.wang.webmonitor.count.CountPO;
import org.wang.webmonitor.error.ErrorPO;
import org.wang.webmonitor.event.EventPO;
import org.wang.webmonitor.visit.User;
import org.wang.webmonitor.visit.Visit;

/**
 *
 * @author wanggang
 */
@RestController
@RequestMapping("manage")
public class ManageController {

    @Autowired
    private ManageService manageService;

    @GetMapping("user")
    public User user() {
        return manageService.selectUser();
    }

    @GetMapping("visits")
    public PageInfo<Visit> vists() {
        return manageService.selectVisits();
    }

    @GetMapping("countTodayYestoday")
    public CountTodayYestoday getCountTodayYestoday() {
        CountTodayYestoday count = new CountTodayYestoday();
        count.setToday(new ArrayList<>());
        count.getToday().add(new CountPO("performance", "2021-11-21", 300L));
        count.getToday().add(new CountPO("pageViewDuration", "2021-11-21", 500000L));
        count.getToday().add(new CountPO("pageViewTimes", "2021-11-21", 123L));
        count.getToday().add(new CountPO("error", "2021-11-21", 10L));

        count.setYestoday(new ArrayList<>());
        count.getYestoday().add(new CountPO("performance", "2021-11-20", 200L));
        count.getYestoday().add(new CountPO("pageViewDuration", "2021-11-20", 400000L));
        count.getYestoday().add(new CountPO("pageViewTimes", "2021-11-20", 350L));
        count.getYestoday().add(new CountPO("error", "2021-11-20", 20L));
        return count;
    }
    
    @GetMapping("count7Days")
    public Count7Days getCount7Days() {
        Count7Days count = new Count7Days();
        count.setDuration(new ArrayList<>());
        count.getDuration().add(new CountPO("pageViewDuration", "2021-11-21", 500000L));
        count.getDuration().add(new CountPO("pageViewDuration", "2021-11-20", 600000L));
        count.getDuration().add(new CountPO("pageViewDuration", "2021-11-19", 700000L));
        count.getDuration().add(new CountPO("pageViewDuration", "2021-11-18", 800000L));
        count.getDuration().add(new CountPO("pageViewDuration", "2021-11-17", 300000L));
        count.getDuration().add(new CountPO("pageViewDuration", "2021-11-16", 400000L));
        count.getDuration().add(new CountPO("pageViewDuration", "2021-11-15", 500000L));

        count.setTimes(new ArrayList<>());
        count.getTimes().add(new CountPO("pageViewTimes", "2021-11-20", 123L));
        count.getTimes().add(new CountPO("pageViewTimes", "2021-11-20", 200L));
        count.getTimes().add(new CountPO("pageViewTimes", "2021-11-20", 300L));
        count.getTimes().add(new CountPO("pageViewTimes", "2021-11-20", 210L));
        count.getTimes().add(new CountPO("pageViewTimes", "2021-11-20", 100L));
        count.getTimes().add(new CountPO("pageViewTimes", "2021-11-20", 300L));
        count.getTimes().add(new CountPO("pageViewTimes", "2021-11-20", 200L));
        return count;
    }
    
    @GetMapping("countModules")
    public CountModules getCountModules() {
        CountModules count = new CountModules();
        count.setDuration(new ArrayList<>());
        count.getDuration().add(new CountPO("测试模块1", "2021-11-21", 500000L));
        count.getDuration().add(new CountPO("测试模块2", "2021-11-21", 230000L));
        count.getDuration().add(new CountPO("测试模块3", "2021-11-21", 600000L));
        count.getDuration().add(new CountPO("测试模块4", "2021-11-21", 840000L));
        count.getDuration().add(new CountPO("测试模块5", "2021-11-21", 930000L));
        count.getDuration().add(new CountPO("测试模块6", "2021-11-21", 210000L));
        count.getDuration().add(new CountPO("测试模块7", "2021-11-21", 120000L));
        count.getDuration().add(new CountPO("测试模块8", "2021-11-21", 560000L));
        count.getDuration().add(new CountPO("测试模块9", "2021-11-21", 740000L));
        count.getDuration().add(new CountPO("测试模块10", "2021-11-21", 460000L));

        count.setTimes(new ArrayList<>());
        count.getTimes().add(new CountPO("测试模块1", "2021-11-20", 200L));
        count.getTimes().add(new CountPO("测试模块2", "2021-11-20", 130L));
        count.getTimes().add(new CountPO("测试模块3", "2021-11-20", 230L));
        count.getTimes().add(new CountPO("测试模块4", "2021-11-20", 140L));
        count.getTimes().add(new CountPO("测试模块5", "2021-11-20", 50L));
        count.getTimes().add(new CountPO("测试模块6", "2021-11-20", 120L));
        count.getTimes().add(new CountPO("测试模块7", "2021-11-20", 200L));
        count.getTimes().add(new CountPO("测试模块8", "2021-11-20", 90L));
        count.getTimes().add(new CountPO("测试模块9", "2021-11-20", 170L));
        count.getTimes().add(new CountPO("测试模块10", "2021-11-20", 150L));
        return count;
    }

    @GetMapping("modules")
    public Map<String, Long> modules(@RequestParam("guid") String guid) {
        return manageService.selectModules(guid);
    }

    @GetMapping("events")
    public PageInfo<EventPO> envents(@RequestParam("guid") String guid) {
        return manageService.selectEventsByGuid(guid);
    }

    @GetMapping("errors")
    public PageInfo<ErrorPO> errors(@RequestParam("guid") String guid) {
        return manageService.selectErrorsByGuid(guid);
    }
}
