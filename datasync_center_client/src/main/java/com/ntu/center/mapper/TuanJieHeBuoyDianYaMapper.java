package com.ntu.center.mapper;

import com.ntu.common.model.po.TuanJieHeBuoyDianYa;

public interface TuanJieHeBuoyDianYaMapper {
    int deleteByPrimaryKey(TuanJieHeBuoyDianYa record);

    int insert(TuanJieHeBuoyDianYa record);

    int insertSelective(TuanJieHeBuoyDianYa record);

    TuanJieHeBuoyDianYa selectByPrimaryKey(TuanJieHeBuoyDianYa record);

    int updateByPrimaryKeySelective(TuanJieHeBuoyDianYa record);

    int updateByPrimaryKey(TuanJieHeBuoyDianYa record);
}