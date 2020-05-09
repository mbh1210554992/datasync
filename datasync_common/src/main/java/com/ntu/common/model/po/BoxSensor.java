package com.ntu.common.model.po;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BoxSensor implements Serializable {
    private static final long serialVersionUID = 3044090487918509605L;
    private String areaId;
    private String spotId;
    private String nodeId;
    private Date dataTime;
    private Float conductivity;
    private Float temperature;
    private Float pressure;
    private Float salinity;
    private Float dissolvedoxygen;
    private Float spad;
    private Float turbidity;
    private Float andan;
    private Float yaxiaodan;
    private Float linsuanyan;
    private Float orp;

}