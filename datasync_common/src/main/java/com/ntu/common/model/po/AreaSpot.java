package com.ntu.common.model.po;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AreaSpot implements Serializable {
    private static final long serialVersionUID = -6893071434497225334L;
    private Long infoId;
    private String areaName;
    private String areaId;
    private String spotId;
    private String spotName;
    private String spotUrl;
    private String spotHistoryurl;
    private String spotObserargs;
    private String spotObsermeth;
    private String spotPosinfo;
    private String spotRuntime;
    private String spotIntro;
}