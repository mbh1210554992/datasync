package com.ntu.center.sync.processor;

import com.ntu.center.mapper.AreaSpotMapper;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.AreaSpot;
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
        if(msg.getDataSynchro().getSa1Status().equals("3")){
            areaSpotMapper.deleteById(msg.getDataSynchro().getBasicinfoid(),msg.getId());
            logger.debug("==========数据同步完成===========");
            return 0;
        }

        AreaSpot areaSpot = (AreaSpot)msg.getData();
        areaSpot.setAreaName(msg.getId());
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
        return null;
    }
}
