package com.ntu.center.sync.processor;

import com.ntu.center.mapper.BoxSensorMapper;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.BoxSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "Processor6")
public class BoxSensor1Processor implements IDataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(BoxSensor1Processor.class);
    @Autowired
    private BoxSensorMapper boxSensorMapper;
    
    @Override
    public int onReceive(SyncMessage msg) {
        if(msg.getDataSync().getSyncType().equals("3")){
            boxSensorMapper.deleteById(msg.getDataSync().getInfoId(),msg.getId());
            logger.debug("==========数据同步完成===========");
            return 0;
        }

        BoxSensor boxSensor = (BoxSensor)msg.getData();
        boxSensor.setInfoNode(msg.getId());
        BoxSensor old = boxSensorMapper.selectByPrimaryKey(boxSensor);
        if(msg.getDataSync().getSyncType().equals("1")){
            if(old ==null) {
                boxSensorMapper.insert( boxSensor);
                return 0;
            }else{
                logger.debug("此条数据已存在.");
                return 1;
            }
        }else if(msg.getDataSync().getSyncType().equals("4")){
            if(old != null){
                boxSensorMapper.updateByPrimaryKey(boxSensor);
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
