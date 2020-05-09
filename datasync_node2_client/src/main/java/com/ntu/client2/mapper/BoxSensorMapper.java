package com.ntu.client2.mapper;

import com.ntu.common.model.po.BoxSensor;

public interface BoxSensorMapper {
    int deleteByPrimaryKey(BoxSensor record);

    int insert(BoxSensor record);

    int insertSelective(BoxSensor record);

    BoxSensor selectByPrimaryKey(BoxSensor record);

    int updateByPrimaryKeySelective(BoxSensor record);

    int updateByPrimaryKey(BoxSensor record);
}