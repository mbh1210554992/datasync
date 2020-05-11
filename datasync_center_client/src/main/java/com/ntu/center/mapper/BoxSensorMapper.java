package com.ntu.center.mapper;

import com.ntu.common.model.po.BoxSensor;
import org.apache.ibatis.annotations.Param;

public interface BoxSensorMapper {
    int deleteByPrimaryKey(BoxSensor record);

    int insert(BoxSensor record);

    int insertSelective(BoxSensor record);

    BoxSensor selectByPrimaryKey(BoxSensor record);

    int updateByPrimaryKeySelective(BoxSensor record);

    int updateByPrimaryKey(BoxSensor record);

    void deleteById(@Param("infoId")Long infoId,@Param("areaName")String areaName);
}