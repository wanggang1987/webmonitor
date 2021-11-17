/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.manage;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import org.wang.webmonitor.event.EventMapper;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wang.webmonitor.error.ErrorMapper;
import org.wang.webmonitor.error.ErrorPO;
import org.wang.webmonitor.event.EventPO;
import org.wang.webmonitor.visit.User;
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
    @Autowired
    private ErrorMapper errorMapper;

    public User selectUser() {
        PageHelper.startPage(0, 1);
        VisitPO vpo = visitMapper.selectOneByExample(Example.builder(VisitPO.class)
                .orderByDesc("id")
                .build());
        if (vpo == null) {
            log.debug("select {} VisitPO", 0);
            return null;
        }
        log.debug("select {} VisitPO", 1);
        User user = new User();
        BeanUtil.copyProperties(vpo, user);
        return user;
    }

    public PageInfo<Visit> selectVisits() {
        PageHelper.startPage(0, 20);
        List<VisitPO> vpos = visitMapper.selectByExample(Example.builder(VisitPO.class)
                .orderByDesc("id")
                .build());
        List<Visit> visits = vpos.stream().map(v -> new Visit(v)).collect(Collectors.toList());

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
        log.debug("select {} EventPO", epos.size());
        return new PageInfo<>(epos);
    }

    public Map<String, Long> selectModules(String guid) {
        List<EventPO> epos = eventMapper.selectByExample(Example.builder(EventPO.class)
                .where(Sqls.custom()
                        .andEqualTo("guid", guid))
                .orderByDesc("id")
                .build());

        Map<String, List<Timestamp>> map = new HashMap<>();
        epos.forEach(epo -> {
            String key = epo.getModule() != null ? epo.getModule() : epo.getUrl();
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            List<Timestamp> list = map.get(key);
            list.add(epo.getTime());
        });

        Map<String, Long> ret = new HashMap<>();
        map.keySet().forEach(key -> {
            List<Timestamp> list = map.get(key);
            if (list.size() >= 2) {
                Long t1 = list.get(0).getTime();
                Long t2 = list.get(list.size() - 1).getTime();
                ret.put(key, t1 - t2);
            } else {
                ret.put(key, 0L);
            }
        });

        return ret;
    }

    public PageInfo<ErrorPO> selectErrorsByGuid(String guid) {
        PageHelper.startPage(0, 100);
        List<ErrorPO> epos = errorMapper.selectByExample(Example.builder(ErrorPO.class)
                .where(Sqls.custom()
                        .andEqualTo("guid", guid))
                .orderByDesc("id")
                .build());
        log.debug("select {} ErrorPO", epos.size());
        return new PageInfo<>(epos);
    }

}
