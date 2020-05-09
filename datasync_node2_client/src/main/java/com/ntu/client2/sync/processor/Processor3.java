package com.ntu.client2.sync.processor;

import com.ntu.client2.mapper.TuanJieHeBuoyDianYaMapper;
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
        TuanJieHeBuoyDianYa tuanJieHeBuoyDianYa = (TuanJieHeBuoyDianYa)msg.getData();
        TuanJieHeBuoyDianYa old = tuanJieHeBuoyDianYaMapper.selectByPrimaryKey(tuanJieHeBuoyDianYa);
        if(old !=null)
        {
            /*if(book.getUpddate() == null ||  old.getUpddate().before(msg.getDataSynchro().getSb1Time()))
            {
                bookMapper.updateSelective(book);
                return 0;
            }else if(old.getUpddate().equals(msg.getSynchro().getSb1Time()))
            {
                return 0;
            }else
            {
                return 1;
            }*/
            //return 0;
            tuanJieHeBuoyDianYaMapper.updateByPrimaryKey(tuanJieHeBuoyDianYa);
            return 0;
        }
        tuanJieHeBuoyDianYaMapper.insert( tuanJieHeBuoyDianYa);
        logger.info("==========数据同步完成===========");
        return 0;
    }

    @Override
    public Long onSend(SyncMessage msg) {
        TuanJieHeBuoyDianYa tuanJieHeBuoyDianYa = tuanJieHeBuoyDianYaMapper.findById(msg.getDataSynchro().getBasicinfoid());
        //System.out.println(book);
        msg.setData(tuanJieHeBuoyDianYa);
        return tuanJieHeBuoyDianYa==null ? null : tuanJieHeBuoyDianYa.getInfoId();
    }
}
