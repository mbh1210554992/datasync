package com.ntu.node.sync.processor;

import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.ChangJiangBoxSensor;
import com.ntu.node.mapper.ChangJiangBoxSensorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: baihua
 * @Date: 2019/12/4 09:05
 */
@Component(value = "Processor1")
public class Processor1 implements IDataProcessor{
    private static final Logger logger = LoggerFactory.getLogger(Processor1.class);

    @Autowired
    private ChangJiangBoxSensorMapper changJiangBoxSensorMapper;
    @Override
    public int onReceive(SyncMessage msg) {
        ChangJiangBoxSensor changJiangBoxSensor = (ChangJiangBoxSensor)msg.getData();
        ChangJiangBoxSensor old = changJiangBoxSensorMapper.findById(changJiangBoxSensor.getInfoId());
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
            changJiangBoxSensorMapper.update(changJiangBoxSensor);
            return 0;
        }
        changJiangBoxSensorMapper.insert(changJiangBoxSensor);
        logger.info("==========数据同步完成===========");
        return 0;
    }

    @Override
    public Long onSend(SyncMessage msg) {
        ChangJiangBoxSensor  changJiangBoxSensor = changJiangBoxSensorMapper.findById(msg.getDataSynchro().getBasicinfoid());
        //System.out.println(book);
        msg.setData(changJiangBoxSensor);
        return changJiangBoxSensor==null ? null : changJiangBoxSensor.getInfoId();
    }
}
