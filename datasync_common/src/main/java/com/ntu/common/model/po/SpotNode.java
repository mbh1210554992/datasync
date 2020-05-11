package com.ntu.common.model.po;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SpotNode implements Serializable {
    private static final long serialVersionUID = 2735755106973982580L;
    private Long infoId;
    private String nodeId;
    private String spotId;
    private String areaId;
    private String nodeName;
    private String nodeType;
    private String nodeNote;
    private String areaName;
}