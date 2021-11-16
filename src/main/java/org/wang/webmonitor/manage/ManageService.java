/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.manage;

import java.util.Comparator;
import org.wang.webmonitor.event.EventMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wang.webmonitor.visit.VisitMapper;
import org.wang.webmonitor.visit.Visit;
import org.wang.webmonitor.visit.VisitPO;

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

    public List<Visit> selectVisits() {
        List<VisitPO> vpos = visitMapper.selectAll();
        List<Visit> visits = vpos.stream()
                .map(v -> new Visit(v)).sorted(Comparator.comparing(Visit::getTime).reversed())
                .collect(Collectors.toList());

        log.debug("select {} visits", visits.size());
        return visits;
    }

}
