package com.ntu.center.mapper;

import com.ntu.common.model.po.MonitorData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component("changJiangBoxSensorMapper")
public interface ChangJiangBoxSensorMapper {
    void insert(MonitorData monitorData);
    void update(MonitorData monitorData);
    MonitorData findById(MonitorData monitorData);
    void deleteById(@Param("infoId")Long infoId,@Param("infoNode")String areaName);
}
