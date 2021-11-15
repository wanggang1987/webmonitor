/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor;

import java.sql.Timestamp;
import lombok.Data;

/**
 *
 * @author wanggang
 */
@Data
public class Event {

    private EventType event;
    private String url;
    private String module;
    private Timestamp time;
}
