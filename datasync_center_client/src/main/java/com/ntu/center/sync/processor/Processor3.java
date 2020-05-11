package com.ntu.center.sync.processor;

import com.ntu.center.mapper.TuanJieHeBuoyDianYaMapper;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.TuanJieHeBuoyDianYa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "Processor3")
public class Processor3 implements IDataProcessor{
    private static final Logger logger = LoggerFactory.getLogger(Processor3.class);
    
    @Autowired
    private TuanJieHeBuoyDianYaMapper tuanJieHeBuoyDianYaMapper;
    
    @Override
    public int onReceive(SyncMessage msg) {
        if(msg.getDataSynchro().getSa1Status().equals("3")){
            tuanJieHeBuoyDianYaMapper.deleteById(msg.getDataSynchro().getBasicinfoid(),msg.getId());
            logger.debug("==========数据同步完成===========");
            return 0;
        }

        TuanJieHeBuoyDianYa tuanJieHeBuoyDianYa = (TuanJieHeBuoyDianYa)msg.getData();
        tuanJieHeBuoyDianYa.setAreaName(msg.getId());
        TuanJieHeBuoyDianYa old = tuanJieHeBuoyDianYaMapper.selectByPrimaryKey(tuanJieHeBuoyDianYa);
        if(old !=null)
        {
            tuanJieHeBuoyDianYaMapper.updateByPrimaryKey(tuanJieHeBuoyDianYa);
            return 0;
        }
        tuanJieHeBuoyDianYaMapper.insert( tuanJieHeBuoyDianYa);
        logger.debug("==========数据同步完成===========");
        return 0;
    }

    @Override
    public Long onSend(SyncMessage msg) {
        return null;
    }
}
