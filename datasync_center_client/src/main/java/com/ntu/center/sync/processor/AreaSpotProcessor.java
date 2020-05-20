package com.ntu.center.sync.processor;

import com.ntu.center.mapper.AreaSpotMapper;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.AreaSpot;
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
        if(msg.getDataSync().getSyncType().equals("3")){
            areaSpotMapper.deleteById(msg.getDataSync().getInfoId(),msg.getId());
            logger.debug("==========数据同步完成===========");
            return 0;
        }

        AreaSpot areaSpot = (AreaSpot)msg.getData();
        areaSpot.setInfoNode(msg.getId());
        AreaSpot old = areaSpotMapper.selectByPrimaryKey(areaSpot);
        if(msg.getDataSync().getSyncType().equals("1")){
            if(old ==null) {
                areaSpotMapper.insert( areaSpot);
                return 0;
            }else{
                logger.debug("此条数据已存在");
                return 1;
            }
        }else if(msg.getDataSync().getSyncType().equals("4")){
            if(old != null){
                areaSpotMapper.updateByPrimaryKey(areaSpot);
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
