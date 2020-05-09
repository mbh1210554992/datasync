package com.ntu.center.mapper;

import com.ntu.common.model.po.TuanJieHeBuoyCod;

public interface TuanJieHeBuoyCodMapper {
    int deleteByPrimaryKey(TuanJieHeBuoyCod record);

    int insert(TuanJieHeBuoyCod record);

    int insertSelective(TuanJieHeBuoyCod record);

    TuanJieHeBuoyCod selectByPrimaryKey(TuanJieHeBuoyCod record);

    int updateByPrimaryKeySelective(TuanJieHeBuoyCod record);

    int updateByPrimaryKey(TuanJieHeBuoyCod record);
}