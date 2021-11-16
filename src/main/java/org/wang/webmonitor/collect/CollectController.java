/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.collect;

import org.wang.webmonitor.event.EventPO;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("monitor")
public class CollectController {

    @Autowired
    private CollectService collectService;

    @PostMapping("info")
    public void beanconInfo(@RequestBody String json) {
        log.debug("beanconInfo {}", json);
        collectService.insertVisit(JSONUtil.toBean(json, Visit.class));
    }

    @GetMapping("info")
    public void pxpointInfo(@RequestBody String json) {
        log.debug("pxpointInfo {}", json);
        collectService.insertVisit(JSONUtil.toBean(json, Visit.class));
    }

    @PostMapping("error")
    public void beanconError(@RequestBody String json) {
        log.debug("beanconError {}", json);
    }

    @GetMapping("error")
    public void pxpointError(@RequestBody String json) {
        log.debug("pxpointError {}", json);
    }

    @PostMapping("event")
    public void beanconEvent(@RequestBody String json) {
        log.debug("beanconEvent {}", json);
        collectService.insertEvent(JSONUtil.toBean(json, EventPO.class));
    }

    @GetMapping("event")
    public void pxpointEvent(@RequestBody String json) {
        log.debug("pxpointEvent {}", json);
        collectService.insertEvent(JSONUtil.toBean(json, EventPO.class));
    }
}
