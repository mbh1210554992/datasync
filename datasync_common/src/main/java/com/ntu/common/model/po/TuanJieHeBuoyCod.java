package com.ntu.common.model.po;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TuanJieHeBuoyCod implements Serializable {
    private static final long serialVersionUID = -4174570655096567451L;
    private Long infoId;
    private String areaId;
    private String spotId;
    private String nodeId;
    private Date dataTime;
    private String num;
    private String num1;
    private String state;
    private String error;
    private Double cod;
    private String areaName;

}