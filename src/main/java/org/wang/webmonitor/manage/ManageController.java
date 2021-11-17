/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.manage;

import com.github.pagehelper.PageInfo;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
