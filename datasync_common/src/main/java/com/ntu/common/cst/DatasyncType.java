package com.ntu.common.cst;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *

 M  监测站信息
 N  收寄信息—关联收寄信息表
 S 区域信息
 I 检测数据信息


 * @author hajime
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public enum DatasyncType {
    MONITOR_DATA("M",1),
    AREA_SPOT("S",5),
    BOX_SENSOR("B",6),
    SPOT_NODE("P",7);

    private String flag;
    private Integer id;
}
