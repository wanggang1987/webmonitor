/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Timestamp;
import javax.persistence.Table;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.wang.webmonitor.BasicPO;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 *
 * @author wanggang
 */
@Data
@Table(name = "t_event")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventPO extends BasicPO {

    private String guid;
    @ColumnType(column = "type", jdbcType = JdbcType.VARCHAR)
    private EventType type;
    private String url;
    private String module;
    private Timestamp time;
}
