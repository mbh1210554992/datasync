package com.ntu.common.model.po;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: baihua
 * @Date: Created in 11/12/2019 10:52 AM
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Getter
public class DataSynchro implements Serializable {
    private static final long serialVersionUID = -1549770061749535714L;

    private Long basicinfoid;
    private String type;
    private String areaName;
    private String sa1Status;
    private Date sb1Time;
    private Date sc1Time;
    private Long sd1Num;
    private Date se1Time;
    private String sf1Msg;
    private String sa2Status;
    private Date sb2Time;
    private Date sc2Time;
    private String sf2Msg;


}
