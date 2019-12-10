package com.ntu.datasync.mapper;

import com.ntu.datasync.model.po.DataSynchro;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface DataSynchroMapper {
    int insert(DataSynchro record);

    int insertSelective(DataSynchro record);
    
    DataSynchro findById(@Param("basicinfoid") String basicinfoid, @Param("type") String type);
    
    List<DataSynchro> querySyncData();
    
    int updatePassive(DataSynchro status);
    
    int updateActive(DataSynchro status);
}