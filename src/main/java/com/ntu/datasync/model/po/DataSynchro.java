package com.ntu.datasync.model.po;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: baihua
 * @Date: Created in 11/12/2019 10:52 AM
 */
@ToString
@Getter
public class DataSynchro implements Serializable {
    private static final long serialVersionUID = -1549770061749535714L;

    private String basicinfoid;
    private String type;
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

    public void setBasicinfoid(String basicinfoid) {
        this.basicinfoid = basicinfoid == null ? null : basicinfoid.trim();
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public void setSa1Status(String sa1Status) {
        this.sa1Status = sa1Status == null ? null : sa1Status.trim();
    }

    public void setSb1Time(Date sb1Time) {
        this.sb1Time = sb1Time;
    }

    public void setSc1Time(Date sc1Time) {
        this.sc1Time = sc1Time;
    }

    public void setSd1Num(Long sd1Num) {
        this.sd1Num = sd1Num;
    }

    public void setSe1Time(Date se1Time) {
        this.se1Time = se1Time;
    }

    public void setSf1Msg(String sf1Msg) {
        this.sf1Msg = sf1Msg == null ? null : sf1Msg;
    }

    public void setSa2Status(String sa2Status) {
        this.sa2Status = sa2Status == null ? null : sa2Status;
    }

    public void setSb2Time(Date sb2Time) {
        this.sb2Time = sb2Time;
    }

    public void setSc2Time(Date sc2Time) {
        this.sc2Time = sc2Time;
    }

    public void setSf2Msg(String sf2Msg) {
        this.sf2Msg = sf2Msg == null ? null : sf2Msg;
    }
}
