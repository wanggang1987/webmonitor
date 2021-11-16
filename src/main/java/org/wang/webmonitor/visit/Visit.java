/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.visit;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Timestamp;
import lombok.Data;

/**
 *
 * @author wanggang
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Visit {

    private String guid;
    private Timestamp time;
    private Performance performance;
    private User user;
    private Long duration;

    public Visit() {

    }

    public Visit(VisitPO vpo) {
        this.performance = new Performance();
        this.user = new User();
        BeanUtil.copyProperties(vpo, this);
        BeanUtil.copyProperties(vpo, this.performance);
        BeanUtil.copyProperties(vpo, this.user);
    }

    public VisitPO toPO() {
        VisitPO vpo = new VisitPO();
        BeanUtil.copyProperties(this, vpo);
        BeanUtil.copyProperties(this.performance, vpo);
        BeanUtil.copyProperties(this.user, vpo);
        return vpo;
    }
}
