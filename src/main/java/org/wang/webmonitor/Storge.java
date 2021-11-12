/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author wanggang
 */
@Service
public class Storge {

    private Map< String, Trace> map = new HashMap<>();
}
