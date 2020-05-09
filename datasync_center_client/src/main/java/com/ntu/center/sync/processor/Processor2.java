package com.ntu.center.sync.processor;

import com.ntu.center.mapper.ChangJiangBoxEnvironmentMapper;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.ChangJiangBoxEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "Processor2")
public class Processor2 implements IDataProcessor{
    private static final Logger logger = LoggerFactory.getLogger(Processor2.class);
    
    @Autowired
    ChangJiangBoxEnvironmentMapper changJiangBoxEnvironmentMapper;
    
    @Override
    public int onReceive(SyncMessage msg) {
        ChangJiangBoxEnvironment  changJiangBoxEnvironment = (ChangJiangBoxEnvironment)msg.getData();
        ChangJiangBoxEnvironment old = changJiangBoxEnvironmentMapper.findById( changJiangBoxEnvironment);
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
            changJiangBoxEnvironmentMapper.update( changJiangBoxEnvironment);
            return 0;
        }
        changJiangBoxEnvironmentMapper.insert( changJiangBoxEnvironment);
        logger.debug("==========数据同步完成===========");
        return 0;
    }

    @Override
    public Long onSend(SyncMessage msg) {
//        ChangJiangBoxEnvironment  changJiangBoxEnvironment = changJiangBoxEnvironmentMapper.findById(msg.getDataSynchro().getBasicinfoid());
//        //System.out.println(book);
//        msg.setData( changJiangBoxEnvironment);
//        return  changJiangBoxEnvironment==null ? null :  changJiangBoxEnvironment.getInfoId();
        return 0l;
    }
}
