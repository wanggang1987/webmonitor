/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.collect;

import org.wang.webmonitor.event.EventMapper;
import org.wang.webmonitor.event.EventPO;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.wang.webmonitor.visit.Visit;
import org.wang.webmonitor.visit.VisitMapper;
import org.wang.webmonitor.visit.VisitPO;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

/**
 *
 * @author wanggang
 */
@Slf4j
@Service
public class CollectService {

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private VisitMapper visitMapper;

    public void insertEvent(EventPO epo) {
        eventMapper.insert(epo);

        VisitPO vpo = getVisitByGuid(epo.getGuid());
        if (vpo != null) {
            vpo.setDuration(epo.getTime().getTime() - vpo.getTime().getTime());
            updateVisit(vpo);
        }

        log.debug("insert success {} ", JSONUtil.toJsonStr(epo));
    }

    public void insertVisit(Visit visit) {
        VisitPO vpo = visit.toPO();
        visitMapper.insert(vpo);
        log.debug("insert success {} ", JSONUtil.toJsonStr(vpo));
    }

    private VisitPO getVisitByGuid(String guid) {
        return visitMapper.selectOneByExample(Example.builder(VisitPO.class)
                .where(Sqls.custom()
                        .andEqualTo("guid", guid))
                .build());
    }

    private void updateVisit(VisitPO visitPO) {
        visitMapper.updateByPrimaryKey(visitPO);
    }
}
