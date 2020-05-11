package com.ntu.common.model.po;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChangJiangBoxEnvironment implements Serializable {
    private static final long serialVersionUID = -8439658921437124590L;
    private Long infoId;
    private String areaId;
    private String spotId;
    private String nodeId;
    private Date dataTime;
    private Float gInVoltage;
    private Float gInCurrent;
    private Float gTemperature;
    private Float zInVoltage;
    private Float zInCurrent;
    private Float zTemperature;
    private Float zJueYuan;
    private Float zLouShui;
    private Float cInVoltage;
    private Float cYunTaiDianLiu;
    private Float cDengDianLiu;
    private Float cTemperature;
    private Float cJueYuan;
    private Float cLouShui;
    private Float waterDeep;
    private String areaName;
}
