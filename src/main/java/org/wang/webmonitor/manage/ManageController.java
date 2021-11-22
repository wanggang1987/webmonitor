/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.manage;

import org.wang.webmonitor.count.CountTodayYestoday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wang.webmonitor.count.Count7Days;
import org.wang.webmonitor.count.CountModules;
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
        return manageService.getCountTodayYestoday();
    }

    @GetMapping("count7Days")
    public Count7Days getCount7Days() {
        return manageService.getCount7Days();
    }

    @GetMapping("countModules")
    public CountModules getCountModules() {
        return manageService.getCountModules();
    }
}
