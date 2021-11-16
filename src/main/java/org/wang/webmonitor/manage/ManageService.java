/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.manage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.Comparator;
import org.wang.webmonitor.event.EventMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wang.webmonitor.event.EventPO;
import org.wang.webmonitor.visit.VisitMapper;
import org.wang.webmonitor.visit.Visit;
import org.wang.webmonitor.visit.VisitPO;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

/**
 *
 * @author wanggang
 */
@Slf4j
@Service
public class ManageService {

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private VisitMapper visitMapper;

    public PageInfo<Visit> selectVisits() {
        PageHelper.startPage(0, 20);
        List<VisitPO> vpos = visitMapper.selectByExample(Example.builder(VisitPO.class)
                .orderByDesc("id")
                .build());
        List<Visit> visits = vpos.stream()
                .map(v -> new Visit(v)).sorted(Comparator.comparing(Visit::getTime).reversed())
                .collect(Collectors.toList());

        log.debug("select {} visits", visits.size());
        return new PageInfo<>(visits);
    }

    public PageInfo<EventPO> selectEventsByGuid(String guid) {
        PageHelper.startPage(0, 100);
        List<EventPO> epos = eventMapper.selectByExample(Example.builder(EventPO.class)
                .where(Sqls.custom()
                        .andEqualTo("guid", guid))
                .orderByDesc("id")
                .build());
        return new PageInfo<>(epos);
    }

}
