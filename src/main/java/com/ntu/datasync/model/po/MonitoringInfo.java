package com.ntu.datasync.model.po;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MonitoringInfo implements Serializable {
    private static final long serialVersionUID = 7646002428651426689L;
    private Integer infoId;
    private String SegmentId;
    private Date createDate;
    private Float temperature;
    private Double pressure;
    private Double illumination;
    private Float waterTemperature;
    private Float ph;
    private Double conductance;
    private Float suspend;
    private Float acidity;
    private Float alkalescence;
    private Float layerCarbonate;
    private Float carbonate;
    private Integer solid;
    private Integer soluteSolid;
    private String remark;

}
