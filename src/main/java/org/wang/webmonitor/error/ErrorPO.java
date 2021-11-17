/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.error;

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
@Table(name = "t_error")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorPO extends BasicPO {

    private String guid;
    private Timestamp time;
    private String type;
    private Long rowNum;
    private Long colNum;
    private String msg;
    private String url;
}
