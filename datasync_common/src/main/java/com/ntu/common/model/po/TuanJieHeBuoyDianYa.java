package com.ntu.common.model.po;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TuanJieHeBuoyDianYa implements Serializable {
    private static final long serialVersionUID = -4492429742923579850L;
    private Long infoId;
    private String areaId;
    private String spotId;
    private String nodeId;
    private Date dataTime;
    private String dianya;
    private String dianliust;
    private String state;

}