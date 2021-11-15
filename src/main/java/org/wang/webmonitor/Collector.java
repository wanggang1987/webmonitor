/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wanggang
 */
@Slf4j
@RestController
@RequestMapping("monitor")
public class Collector {

    @PostMapping("info")
    public void beanconInfo(@RequestBody String trace) {
        log.info("beanconInfo:" + trace);
    }

    @GetMapping("info")
    public void pxpointInfo(@RequestBody String trace) {
        log.info("pxpointInfo:" + trace);
    }

    @PostMapping("error")
    public void beanconError(@RequestBody String trace) {
        log.info("beanconError:" + trace);
    }

    @GetMapping("error")
    public void pxpointError(@RequestBody String trace) {
        log.info("pxpointError:" + trace);
    }
    
    @PostMapping("event")
    public void beanconEvent(@RequestBody String trace) {
        log.info("beanconEvent:" + trace);
    }

    @GetMapping("event")
    public void pxpointEvent(@RequestBody String trace) {
        log.info("pxpointEvent:" + trace);
    }
}
