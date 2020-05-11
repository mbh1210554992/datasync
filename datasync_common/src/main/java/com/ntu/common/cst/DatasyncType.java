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
    CHANGJIANG_BOX_SENSOR("M",1),
    CHANGJIANG_BOX_ENVIRONMENT("N",2),
    TUANJIEHE_BUOY_DIANYA("D",3),
    TUANJIEHE_BUOY_GONGZUOWENDU("G",4),
    AREA_SPOT("S",5),
    BOX_SENSOR("B",6),
    SPOT_NODE("P",7);
    //TUANJIEHE_BUOY_GPS("H",8);



    private String flag;
    private Integer id;
}
