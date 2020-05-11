package com.ntu.center.sync.processor;

import com.ntu.center.mapper.TuanJieHeBuoyGongZuoWenDuMapper;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.TuanJieHeBuoyGongZuoWenDu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "Processor4")
public class Processor4 implements IDataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(Processor4.class);

    @Autowired
    private TuanJieHeBuoyGongZuoWenDuMapper tuanJieHeBuoyGongZuoWenDuMapper;

    @Override
    public int onReceive(SyncMessage msg) {
        if(msg.getDataSynchro().getSa1Status().equals("3")){
            tuanJieHeBuoyGongZuoWenDuMapper.deleteById(msg.getDataSynchro().getBasicinfoid(),msg.getId());
            logger.debug("==========数据同步完成===========");
            return 0;
        }

        TuanJieHeBuoyGongZuoWenDu tuanJieHeBuoyGongZuoWenDu = (TuanJieHeBuoyGongZuoWenDu)msg.getData();
        tuanJieHeBuoyGongZuoWenDu.setAreaName(msg.getId());
        TuanJieHeBuoyGongZuoWenDu old = tuanJieHeBuoyGongZuoWenDuMapper.selectByPrimaryKey(tuanJieHeBuoyGongZuoWenDu);
        if(old !=null)
        {
            tuanJieHeBuoyGongZuoWenDuMapper.updateByPrimaryKey(tuanJieHeBuoyGongZuoWenDu);
            return 0;
        }
        tuanJieHeBuoyGongZuoWenDuMapper.insert( tuanJieHeBuoyGongZuoWenDu);
        logger.debug("==========数据同步完成===========");
        return 0;
    }

    @Override
    public Long onSend(SyncMessage msg) {
        return null;
    }
}
