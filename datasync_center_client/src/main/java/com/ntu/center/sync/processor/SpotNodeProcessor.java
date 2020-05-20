package com.ntu.center.sync.processor;

import com.ntu.center.mapper.SpotNodeMapper;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.SpotNode;
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
        if(msg.getDataSync().getSyncType().equals("3")){
            spotNodeMapper.deleteById(msg.getDataSync().getInfoId(),msg.getId());
            logger.debug("==========数据同步完成===========");
            return 0;
        }

        SpotNode spotNode = (SpotNode)msg.getData();
        spotNode.setInfoNode(msg.getId());
        SpotNode old = spotNodeMapper.selectByPrimaryKey(spotNode);
        if(msg.getDataSync().getSyncType().equals("1")){
            if(old ==null) {
                spotNodeMapper.insert( spotNode);
                return 0;
            }else{
                logger.debug("此条数据已存在!");
                return 1;
            }
        }else if(msg.getDataSync().getSyncType().equals("4")){
            if(old != null){
                spotNodeMapper.updateByPrimaryKey(spotNode);
                return 0;
            }else{
                logger.debug("此条数据不存在不能进行更新");
                return 2;
            }

        }


        return 0;
    }

    @Override
    public Long onSend(SyncMessage msg) {
        return null;
    }
}
