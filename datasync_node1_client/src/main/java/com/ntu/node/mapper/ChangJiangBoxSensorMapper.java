package com.ntu.node.mapper;

import com.ntu.common.model.po.ChangJiangBoxSensor;
import org.apache.ibatis.annotations.Param;

public interface ChangJiangBoxSensorMapper {
    void insert(ChangJiangBoxSensor changJiangBoxSensor);
    void update(ChangJiangBoxSensor changJiangBoxSensor);
    ChangJiangBoxSensor findById(@Param("infoId")Long infoId);
}
