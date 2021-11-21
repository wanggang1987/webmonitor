/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.count;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.wang.webmonitor.TKMapper;

/**
 *
 * @author wanggang
 */
@Mapper
public interface CountMapper extends TKMapper<CountPO> {

    @Insert("insert into t_count (tag, date, duration, times) "
            + "VALUES (#{tag}, #{date}, #{duration}, #{times}) "
            + "ON DUPLICATE KEY UPDATE duration = #{duration}, times = #{times}")
    public void updateCount(
            @Param("tag") String tag, @Param("date") String date, @Param("duration") Long duration, @Param("times") Long times);

}
