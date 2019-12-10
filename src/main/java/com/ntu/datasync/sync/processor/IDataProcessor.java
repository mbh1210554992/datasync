package com.ntu.datasync.sync.processor;

import com.ntu.datasync.model.SyncMessage;

/**
 * @Author: baihua
 * @Date: 2019/12/4 09:09
 */
//数据同步接口
public interface IDataProcessor
{
    public int onReceive(SyncMessage msg);

    public String onSend(SyncMessage msg);
}
