package com.ntu.client2.mapper;

import com.ntu.common.model.po.MonitorData;
import org.apache.ibatis.annotations.Param;

public interface MonitorDataMapper {
    void insert(MonitorData monitorData);
    void update(MonitorData monitorData);
    MonitorData findById(@Param("infoId") Long infoId);
}
