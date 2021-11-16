/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.manage;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("visits")
    public List<Visit> vists() {
        return manageService.selectVisits();
    }
}