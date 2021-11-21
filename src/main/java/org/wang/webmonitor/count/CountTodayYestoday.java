/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor.count;

import java.util.List;
import lombok.Data;

/**
 *
 * @author gangwang
 */
@Data
public class CountTodayYestoday {

    List<CountPO> today;
    List<CountPO> yestoday;
}
