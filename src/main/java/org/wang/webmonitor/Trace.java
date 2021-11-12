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
public class Trace {

    private String user;
    private Event event;
    private String module;
    private String url;
    private String platform;
    private String version;
    private Timestamp timestamp;
}
