package com.ntu.client2.mapper;

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

    AreaSpot findById(@Param("infoId")Long infoId);
}