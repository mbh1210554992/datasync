package com.ntu.node.mapper;

import com.ntu.common.model.po.ChangJiangBoxEnvironment;
import org.apache.ibatis.annotations.Param;

public interface ChangJiangBoxEnvironmentMapper {
    void insert(ChangJiangBoxEnvironment changJiangBoxEnvironment);
    ChangJiangBoxEnvironment findById(@Param("infoId") Long infoId);
    void update(ChangJiangBoxEnvironment changJiangBoxEnvironment);
}
