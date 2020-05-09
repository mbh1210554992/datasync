package com.ntu.client2.mapper;

import com.ntu.common.model.po.TuanJieHeBuoyDianYa;
import org.apache.ibatis.annotations.Param;

public interface TuanJieHeBuoyDianYaMapper {
    int deleteByPrimaryKey(TuanJieHeBuoyDianYa record);

    int insert(TuanJieHeBuoyDianYa record);

    int insertSelective(TuanJieHeBuoyDianYa record);

    TuanJieHeBuoyDianYa selectByPrimaryKey(TuanJieHeBuoyDianYa record);

    int updateByPrimaryKeySelective(TuanJieHeBuoyDianYa record);

    int updateByPrimaryKey(TuanJieHeBuoyDianYa record);

    TuanJieHeBuoyDianYa findById(@Param("infoId")Long infoId);
}