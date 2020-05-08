package com.ntu.center.mapper;

import com.ntu.common.model.po.ChangJiangBoxEnvironment;

public interface ChangJiangBoxEnvironmentMapper {
    void insert(ChangJiangBoxEnvironment changJiangBoxEnvironment);
    ChangJiangBoxEnvironment findById(ChangJiangBoxEnvironment changJiangBoxEnvironment);
    void update(ChangJiangBoxEnvironment changJiangBoxEnvironment);
}
