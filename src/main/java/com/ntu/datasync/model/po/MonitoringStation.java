package com.ntu.datasync.model.po;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MonitoringStation implements Serializable {
    private static final long serialVersionUID = 50615594556787682L;
    private String stationId;
    private String stationName;
    private String stationCode;
    private String stationLevel;
    private String ValleyName;
    private String riverCode;
    private String address;
    private String area_code;
    private String manageUnit;
    private Integer frequency;
    private Date createDate;
    private Date modifyDate;
    private String remark;
}
