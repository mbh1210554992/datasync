package com.ntu.center.sync.processor;

import com.ntu.center.mapper.ChangJiangBoxSensorMapper;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.ChangJiangBoxSensor;
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
        if(msg.getDataSynchro().getSa1Status().equals("3")){
            changJiangBoxSensorMapper.deleteById(msg.getDataSynchro().getBasicinfoid(),msg.getId());
            logger.debug("==========数据同步完成===========");
            return 0;
        }
        ChangJiangBoxSensor changJiangBoxSensor = (ChangJiangBoxSensor)msg.getData();
        changJiangBoxSensor.setAreaName(msg.getId());
        ChangJiangBoxSensor old = changJiangBoxSensorMapper.findById(changJiangBoxSensor);
        if(old !=null)
        {
            changJiangBoxSensorMapper.update(changJiangBoxSensor);
            return 0;
        }
        changJiangBoxSensorMapper.insert(changJiangBoxSensor);
        logger.debug("==========数据同步完成===========");
        return 0;
    }

    @Override
    public Long onSend(SyncMessage msg) {
        //ChangJiangBoxSensor  changJiangBoxSensor = changJiangBoxSensorMapper.findById((ChangJiangBoxSensor)msg.getData());
        //System.out.println(book);
       // msg.setData(changJiangBoxSensor);
        //return changJiangBoxSensor==null ? null : changJiangBoxSensor.getInfoId();
        return 0l;
    }
}
