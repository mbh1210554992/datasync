package com.ntu.node.sync.processor;

import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.AreaSpot;
import com.ntu.node.mapper.AreaSpotMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "Processor5")
public class Processor5 implements IDataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(Processor5.class);
    @Autowired
    private AreaSpotMapper areaSpotMapper;
    
    @Override
    public int onReceive(SyncMessage msg) {
        return 0;
    }

    @Override
    public Long onSend(SyncMessage msg) {
        AreaSpot reaSpot = areaSpotMapper.findById(msg.getDataSynchro().getBasicinfoid());
        msg.setData(reaSpot);
        return reaSpot==null ? null : reaSpot.getInfoId();
    }
}
