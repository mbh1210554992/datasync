package com.ntu.center.mapper;

import com.ntu.common.model.po.DataSynchro;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DataSynchroMapper {
    int insert(DataSynchro record);

    int insertSelective(DataSynchro record);
    
    DataSynchro findById(@Param("basicinfoid") Long basicinfoid, @Param("type") String type,@Param("areaName")String areaName);
    
    List<DataSynchro> querySyncData();
    
    int updatePassive(DataSynchro status);
    
    int updateActive(DataSynchro status);
}