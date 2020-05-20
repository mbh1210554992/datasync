package com.ntu.center.mapper;

import com.ntu.common.model.po.DataSync;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataSyncMapper {
    DataSync findByKey(@Param("infoId") Long infoId, @Param("infoType") String infoType, @Param("infoNode")String nodeName);
    List<DataSync> queryData();
    void updateActive(DataSync dataSync);
    void insert(DataSync dataSync);
}
