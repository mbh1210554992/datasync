package com.ntu.node.sync.processor;

import com.ntu.common.model.SyncMessage;
import org.springframework.stereotype.Component;

/**
 * @Author: baihua
 * @Date: 2019/12/4 09:09
 */
@Component
//数据同步接口
public interface IDataProcessor
{
    public int onReceive(SyncMessage msg);

    public Long onSend(SyncMessage msg);
}
