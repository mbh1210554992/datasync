package com.ntu.node.sync.processor;

import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.SpotNode;
import com.ntu.node.mapper.SpotNodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "Processor7")
public class SpotNodeProcessor implements IDataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(SpotNodeProcessor.class);
    @Autowired
    private SpotNodeMapper spotNodeMapper;
    
    @Override
    public int onReceive(SyncMessage msg) {
        SpotNode spotNode = (SpotNode)msg.getData();
        spotNode.setInfoNode(msg.getId());
        SpotNode old = spotNodeMapper.selectByPrimaryKey(spotNode);
        if(old !=null)
        {
            spotNodeMapper.updateByPrimaryKey(spotNode);
            return 0;
        }
        spotNodeMapper.insert( spotNode);
        logger.debug("==========数据同步完成===========");
        return 0;
    }

    @Override
    public Long onSend(SyncMessage msg) {
        SpotNode spotNode = spotNodeMapper.findById(msg.getDataSync().getInfoId());
        msg.setData(spotNode);
        return spotNode==null ? null : spotNode.getInfoId();
    }
}
