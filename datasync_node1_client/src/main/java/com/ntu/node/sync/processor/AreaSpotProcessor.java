package com.ntu.node.sync.processor;

import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.AreaSpot;
import com.ntu.node.mapper.AreaSpotMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "Processor5")
public class AreaSpotProcessor implements IDataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(AreaSpotProcessor.class);
    @Autowired
    private AreaSpotMapper areaSpotMapper;
    
    @Override
    public int onReceive(SyncMessage msg) {

        AreaSpot areaSpot = (AreaSpot)msg.getData();
        areaSpot.setInfoNode(msg.getId());
        AreaSpot old = areaSpotMapper.selectByPrimaryKey(areaSpot);
        if(old !=null)
        {
            areaSpotMapper.updateByPrimaryKey(areaSpot);
            return 0;
        }
        areaSpotMapper.insert( areaSpot);
        logger.debug("==========数据同步完成===========");
        return 0;
    }

    @Override
    public Long onSend(SyncMessage msg) {
        AreaSpot reaSpot = areaSpotMapper.findById(msg.getDataSync().getInfoId());
        msg.setData(reaSpot);
        return reaSpot==null ? null : reaSpot.getInfoId();
    }
}
