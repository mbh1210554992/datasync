package com.ntu.center.sync.processor;

import com.ntu.center.mapper.BoxSensorMapper;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.BoxSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "Processor6")
public class Processor6 implements IDataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(Processor6.class);
    @Autowired
    private BoxSensorMapper boxSensorMapper;
    
    @Override
    public int onReceive(SyncMessage msg) {
        if(msg.getDataSynchro().getSa1Status().equals("3")){
            boxSensorMapper.deleteById(msg.getDataSynchro().getBasicinfoid(),msg.getId());
            logger.debug("==========数据同步完成===========");
            return 0;
        }

        BoxSensor boxSensor = (BoxSensor)msg.getData();
        boxSensor.setAreaName(msg.getId());
        BoxSensor old = boxSensorMapper.selectByPrimaryKey(boxSensor);
        if(old !=null)
        {
            boxSensorMapper.updateByPrimaryKey(boxSensor);
            return 0;
        }
        boxSensorMapper.insert( boxSensor);
        logger.debug("==========数据同步完成===========");
        return 0;
    }

    @Override
    public Long onSend(SyncMessage msg) {
        return null;
    }
}
