package com.ntu.center.sync.processor;

import com.ntu.center.mapper.SpotNodeMapper;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.SpotNode;
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
        if(msg.getDataSynchro().getSa1Status().equals("3")){
            spotNodeMapper.deleteById(msg.getDataSynchro().getBasicinfoid(),msg.getId());
            logger.debug("==========数据同步完成===========");
            return 0;
        }

        SpotNode spotNode = (SpotNode)msg.getData();
        spotNode.setAreaName(msg.getId());
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
        return null;
    }
}
