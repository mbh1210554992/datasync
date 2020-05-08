package com.ntu.common.model.po;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChangJiangBoxSensor implements Serializable {
    private static final long serialVersionUID = 5954608679730708927L;
    private Long infoId;
    private String areaId;
    private String spotId;
    private String nodeId;
    private Date dataTime;
    private Float conductivity;
    private Float temperature;
    private Float ph;
    private Float salinity;
    private Float dissolvedOxygen;
    private Float turbidity;
}
