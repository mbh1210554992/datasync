package com.ntu.common.model.po;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TuanJieHeBuoyGongZuoWenDu implements Serializable {
    private static final long serialVersionUID = -897235454215233301L;
    private Long infoId;
    private String areaId;
    private String spotId;
    private String nodeId;
    private Date dataTime;
    private String num;
    private String numQuest;
    private String state;
    private String errornum;
    private Double tem1hratur;
    private Double tem2Xical;
    private String areaName;


}