package com.ntu.client2.mapper;

import com.ntu.common.model.po.TuanJieHeBuoyGongZuoWenDu;
import org.apache.ibatis.annotations.Param;

public interface TuanJieHeBuoyGongZuoWenDuMapper {
    int deleteByPrimaryKey(TuanJieHeBuoyGongZuoWenDu record);

    int insert(TuanJieHeBuoyGongZuoWenDu record);

    int insertSelective(TuanJieHeBuoyGongZuoWenDu record);

    TuanJieHeBuoyGongZuoWenDu selectByPrimaryKey(TuanJieHeBuoyGongZuoWenDu record);

    int updateByPrimaryKeySelective(TuanJieHeBuoyGongZuoWenDu record);

    int updateByPrimaryKey(TuanJieHeBuoyGongZuoWenDu record);

    TuanJieHeBuoyGongZuoWenDu findById(@Param("infoId")Long infoId);
}