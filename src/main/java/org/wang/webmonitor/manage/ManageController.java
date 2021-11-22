/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.manage;

import org.wang.webmonitor.count.CountTodayYestoday;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wang.webmonitor.count.Count7Days;
import org.wang.webmonitor.count.CountModules;
import org.wang.webmonitor.count.CountPO;
import org.wang.webmonitor.visit.User;

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

    @GetMapping("countTodayYestoday")
    public CountTodayYestoday getCountTodayYestoday() {
        CountTodayYestoday count = new CountTodayYestoday();
        count.setToday(new ArrayList<>());
        count.getToday().add(new CountPO("performance", "2021-11-21", 300));
        count.getToday().add(new CountPO("pageViewDuration", "2021-11-21", 50000));
        count.getToday().add(new CountPO("pageViewTimes", "2021-11-21", 123));
        count.getToday().add(new CountPO("error", "2021-11-21", 10));

        count.setYestoday(new ArrayList<>());
        count.getYestoday().add(new CountPO("performance", "2021-11-20", 200));
        count.getYestoday().add(new CountPO("pageViewDuration", "2021-11-20", 40000));
        count.getYestoday().add(new CountPO("pageViewTimes", "2021-11-20", 350));
        count.getYestoday().add(new CountPO("error", "2021-11-20", 20));
        return count;
    }

    @GetMapping("count7Days")
    public Count7Days getCount7Days() {
        Count7Days count = new Count7Days();
        count.setDuration(new ArrayList<>());
        count.getDuration().add(new CountPO("pageViewDuration", "2021-11-21", 500000));
        count.getDuration().add(new CountPO("pageViewDuration", "2021-11-20", 600000));
        count.getDuration().add(new CountPO("pageViewDuration", "2021-11-19", 700000));
        count.getDuration().add(new CountPO("pageViewDuration", "2021-11-18", 800000));
        count.getDuration().add(new CountPO("pageViewDuration", "2021-11-17", 300000));
        count.getDuration().add(new CountPO("pageViewDuration", "2021-11-16", 400000));
        count.getDuration().add(new CountPO("pageViewDuration", "2021-11-15", 500000));

        count.setTimes(new ArrayList<>());
        count.getTimes().add(new CountPO("pageViewTimes", "2021-11-21", 123));
        count.getTimes().add(new CountPO("pageViewTimes", "2021-11-20", 200));
        count.getTimes().add(new CountPO("pageViewTimes", "2021-11-19", 300));
        count.getTimes().add(new CountPO("pageViewTimes", "2021-11-18", 210));
        count.getTimes().add(new CountPO("pageViewTimes", "2021-11-17", 100));
        count.getTimes().add(new CountPO("pageViewTimes", "2021-11-16", 300));
        count.getTimes().add(new CountPO("pageViewTimes", "2021-11-15", 200));
        return count;
    }

    @GetMapping("countModules")
    public CountModules getCountModules() {
        CountModules count = new CountModules();
        count.setDuration(new ArrayList<>());
        count.getDuration().add(new CountPO("测试模块1", "2021-11-21", 500000));
        count.getDuration().add(new CountPO("测试模块2", "2021-11-21", 230000));
        count.getDuration().add(new CountPO("测试模块3", "2021-11-21", 600000));
        count.getDuration().add(new CountPO("测试模块4", "2021-11-21", 840000));
        count.getDuration().add(new CountPO("测试模块5", "2021-11-21", 930000));
        count.getDuration().add(new CountPO("测试模块6", "2021-11-21", 210000));
        count.getDuration().add(new CountPO("测试模块7", "2021-11-21", 120000));
        count.getDuration().add(new CountPO("测试模块8", "2021-11-21", 560000));
        count.getDuration().add(new CountPO("测试模块9", "2021-11-21", 740000));
        count.getDuration().add(new CountPO("测试模块10", "2021-11-21", 460000));

        count.setTimes(new ArrayList<>());
        count.getTimes().add(new CountPO("测试模块1", "2021-11-20", 200));
        count.getTimes().add(new CountPO("测试模块2", "2021-11-20", 130));
        count.getTimes().add(new CountPO("测试模块3", "2021-11-20", 230));
        count.getTimes().add(new CountPO("测试模块4", "2021-11-20", 140));
        count.getTimes().add(new CountPO("测试模块5", "2021-11-20", 50));
        count.getTimes().add(new CountPO("测试模块6", "2021-11-20", 120));
        count.getTimes().add(new CountPO("测试模块7", "2021-11-20", 200));
        count.getTimes().add(new CountPO("测试模块8", "2021-11-20", 90));
        count.getTimes().add(new CountPO("测试模块9", "2021-11-20", 170));
        count.getTimes().add(new CountPO("测试模块10", "2021-11-20", 150));
        return count;
    }
}
