package org.wang.webmonitor;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
public class BasicPO {

    @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY) 由于H2不支持last_insert_id暂时屏蔽
    private Integer id;
    @Column(name = "create_time", updatable = false, insertable = false)
    private Timestamp createTime;
    @Column(name = "update_time", updatable = false, insertable = false)
    private Timestamp updateTime;
}
