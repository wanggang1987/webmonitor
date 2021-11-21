/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.visit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Timestamp;
import javax.persistence.Table;
import lombok.Data;
import org.wang.webmonitor.BasicPO;

/**
 *
 * @author wanggang
 */
@Data
@Table(name = "t_visit")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisitPO extends BasicPO {

    private String guid;
    private Timestamp time;
    private String url;
    private Integer redirect;
    private Integer whiteScreen;
    private Integer dom;
    private Integer load;
    private Integer unload;
    private Integer request;
    private Integer total;
    private String userName;
    private Integer width;
    private Integer height;
    private String browser;
    private String os;
    private String language;
}
