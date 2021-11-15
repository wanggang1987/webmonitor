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
@RequestMapping("api")
public class Collector {

    @PostMapping("event")
    public void beancon(@RequestBody String trace) {
        log.info("beancon:" + trace);
    }

    @GetMapping("event")
    public void pxpoint(@RequestBody String trace) {
        log.info("pxpoint:" + trace);
    }
}
