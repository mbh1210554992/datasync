package com.ntu.node.sync.processor;

import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.BoxSensor;
import com.ntu.node.mapper.BoxSensorMapper;
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
        return 0;
    }

    @Override
    public Long onSend(SyncMessage msg) {
        BoxSensor boxSensor = boxSensorMapper.findById(msg.getDataSynchro().getBasicinfoid());
        msg.setData(boxSensor);
        return boxSensor==null ? null : boxSensor.getInfoId();
    }
}
