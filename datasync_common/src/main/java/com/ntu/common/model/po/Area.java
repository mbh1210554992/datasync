package com.ntu.common.model.po;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Area implements Serializable {
    private static final long serialVersionUID = 6941422136388731656L;
    private String areaId;
    private String areaName;
    private String areaNote;

}