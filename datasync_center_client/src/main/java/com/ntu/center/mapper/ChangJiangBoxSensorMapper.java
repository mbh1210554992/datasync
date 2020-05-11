package com.ntu.center.mapper;

import com.ntu.common.model.po.ChangJiangBoxSensor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component("changJiangBoxSensorMapper")
public interface ChangJiangBoxSensorMapper {
    void insert(ChangJiangBoxSensor changJiangBoxSensor);
    void update(ChangJiangBoxSensor changJiangBoxSensor);
    ChangJiangBoxSensor findById(ChangJiangBoxSensor changJiangBoxSensor);
    void deleteById(@Param("infoId")Long infoId,@Param("areaName")String areaName);
}
