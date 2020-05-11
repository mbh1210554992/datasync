package com.ntu.center.mapper;

import com.ntu.common.model.po.TuanJieHeBuoyCod;
import org.apache.ibatis.annotations.Param;

public interface TuanJieHeBuoyCodMapper {
    int deleteByPrimaryKey(TuanJieHeBuoyCod record);

    int insert(TuanJieHeBuoyCod record);

    int insertSelective(TuanJieHeBuoyCod record);

    TuanJieHeBuoyCod selectByPrimaryKey(TuanJieHeBuoyCod record);

    int updateByPrimaryKeySelective(TuanJieHeBuoyCod record);

    int updateByPrimaryKey(TuanJieHeBuoyCod record);

    void deleteById(@Param("infoId")Long infoId,@Param("areaName")String areaName);
}