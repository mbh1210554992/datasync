package com.ntu.center.mapper;

import com.ntu.common.model.po.TuanJieHeBuoyGongZuoWenDu;
import org.apache.ibatis.annotations.Param;

public interface TuanJieHeBuoyGongZuoWenDuMapper {
    int deleteByPrimaryKey(TuanJieHeBuoyGongZuoWenDu record);

    int insert(TuanJieHeBuoyGongZuoWenDu record);

    int insertSelective(TuanJieHeBuoyGongZuoWenDu record);

    TuanJieHeBuoyGongZuoWenDu selectByPrimaryKey(TuanJieHeBuoyGongZuoWenDu record);

    int updateByPrimaryKeySelective(TuanJieHeBuoyGongZuoWenDu record);

    int updateByPrimaryKey(TuanJieHeBuoyGongZuoWenDu record);

    void deleteById(@Param("infoId")Long infoId,@Param("areaName")String areaName);
}