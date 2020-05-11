package com.ntu.center.mapper;

import com.ntu.common.model.po.SpotNode;
import org.apache.ibatis.annotations.Param;

public interface SpotNodeMapper {
    int deleteByPrimaryKey(SpotNode record);

    int insert(SpotNode record);

    int insertSelective(SpotNode record);

    SpotNode selectByPrimaryKey(SpotNode record);

    int updateByPrimaryKeySelective(SpotNode record);

    int updateByPrimaryKeyWithBLOBs(SpotNode record);

    int updateByPrimaryKey(SpotNode record);

    void deleteById(@Param("infoId")Long infoId,@Param("areaName")String areaName);
}