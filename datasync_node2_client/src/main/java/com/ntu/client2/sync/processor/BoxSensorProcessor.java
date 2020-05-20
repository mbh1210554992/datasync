package com.ntu.client2.sync.processor;

import com.ntu.client2.mapper.BoxSensorMapper;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.BoxSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "Processor6")
public class BoxSensorProcessor implements IDataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(BoxSensorProcessor.class);
    @Autowired
    private BoxSensorMapper boxSensorMapper;
    
    @Override
    public int onReceive(SyncMessage msg) {
        return 0;
    }

    @Override
    public Long onSend(SyncMessage msg) {
        BoxSensor boxSensor = boxSensorMapper.findById(msg.getDataSync().getInfoId());
        msg.setData(boxSensor);
        return boxSensor==null ? null : boxSensor.getInfoId();
    }
}
