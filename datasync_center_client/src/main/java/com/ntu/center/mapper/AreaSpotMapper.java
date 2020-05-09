package com.ntu.center.mapper;

import com.ntu.common.model.po.AreaSpot;

public interface AreaSpotMapper {
    int deleteByPrimaryKey(AreaSpot record);

    int insert(AreaSpot record);

    int insertSelective(AreaSpot record);

    AreaSpot selectByPrimaryKey(AreaSpot record);

    int updateByPrimaryKeySelective(AreaSpot record);

    int updateByPrimaryKeyWithBLOBs(AreaSpot record);

    int updateByPrimaryKey(AreaSpot record);
}