package com.ntu.center.mapper;

import com.ntu.common.model.po.AreaSpot;
import org.apache.ibatis.annotations.Param;

public interface AreaSpotMapper {
    int deleteByPrimaryKey(AreaSpot record);

    int insert(AreaSpot record);

    int insertSelective(AreaSpot record);

    AreaSpot selectByPrimaryKey(AreaSpot record);

    int updateByPrimaryKeySelective(AreaSpot record);

    int updateByPrimaryKeyWithBLOBs(AreaSpot record);

    int updateByPrimaryKey(AreaSpot record);

    void deleteById(@Param("infoId")Long infoId,@Param("infoNode")String areaName);
}