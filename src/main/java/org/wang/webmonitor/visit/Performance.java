/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.visit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 *
 * @author wanggang
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Performance {

    private Integer redirect;
    private Integer whiteScreen;
    private Integer dom;
    private Integer load;
    private Integer unload;
    private Integer request;
    private Integer total;
}
