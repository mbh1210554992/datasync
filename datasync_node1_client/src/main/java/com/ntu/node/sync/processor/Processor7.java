package com.ntu.node.sync.processor;

import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.SpotNode;
import com.ntu.node.mapper.SpotNodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "Processor7")
public class Processor7 implements IDataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(Processor7.class);
    @Autowired
    private SpotNodeMapper spotNodeMapper;
    
    @Override
    public int onReceive(SyncMessage msg) {
        return 0;
    }

    @Override
    public Long onSend(SyncMessage msg) {
        SpotNode spotNode = spotNodeMapper.findById(msg.getDataSynchro().getBasicinfoid());
        msg.setData(spotNode);
        return spotNode==null ? null : spotNode.getInfoId();
    }
}
