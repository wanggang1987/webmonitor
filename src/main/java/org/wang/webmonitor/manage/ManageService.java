/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.manage;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import org.wang.webmonitor.event.EventMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wang.webmonitor.error.ErrorMapper;
import org.wang.webmonitor.visit.User;
import org.wang.webmonitor.visit.VisitMapper;
import org.wang.webmonitor.visit.VisitPO;
import tk.mybatis.mapper.entity.Example;

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

}
