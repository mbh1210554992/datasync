package com.ntu.common.model.po;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Getter
public class DataSync implements Serializable {
    private static final long serialVersionUID = -4778298122436366465L;
    private Long infoId;
    private String infoType;
    private String syncType;
    private String syncStatus;
    private Date successTime;
    private Date failTime;
    private Integer failCount;
    private String syncMsg;
    private String infoNode;
}
