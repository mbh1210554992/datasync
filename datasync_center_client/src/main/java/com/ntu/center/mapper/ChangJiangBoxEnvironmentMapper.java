package com.ntu.center.mapper;

import com.ntu.common.model.po.ChangJiangBoxEnvironment;
import org.apache.ibatis.annotations.Param;

public interface ChangJiangBoxEnvironmentMapper {
    void insert(ChangJiangBoxEnvironment changJiangBoxEnvironment);
    ChangJiangBoxEnvironment findById(ChangJiangBoxEnvironment changJiangBoxEnvironment);
    void update(ChangJiangBoxEnvironment changJiangBoxEnvironment);
    void deleteById(@Param("infoId")Long infoId,@Param("areaName")String areaName);
}
