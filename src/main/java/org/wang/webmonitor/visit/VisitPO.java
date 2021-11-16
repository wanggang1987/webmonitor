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
    private Long redirect;
    private Long whiteScreen;
    private Long dom;
    private Long load;
    private Long unload;
    private Long request;
    private String userName;
    private Long width;
    private Long height;
    private String browser;
    private String os;
    private String language;
}
