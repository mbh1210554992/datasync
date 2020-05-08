package com.ntu.center.mapper;

import com.ntu.common.model.po.ChangJiangBoxSensor;
import org.springframework.stereotype.Component;

@Component("changJiangBoxSensorMapper")
public interface ChangJiangBoxSensorMapper {
    void insert(ChangJiangBoxSensor changJiangBoxSensor);
    void update(ChangJiangBoxSensor changJiangBoxSensor);
    ChangJiangBoxSensor findById(ChangJiangBoxSensor changJiangBoxSensor);
}
