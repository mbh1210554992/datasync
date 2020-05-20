package com.ntu.center.sync.processor;

import com.ntu.center.mapper.ChangJiangBoxSensorMapper;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.MonitorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: baihua
 * @Date: 2019/12/4 09:05
 */
@Component(value = "Processor1")
public class BoxSenSorProcessor implements IDataProcessor{

    private static final Logger logger = LoggerFactory.getLogger(BoxSenSorProcessor.class);

    @Autowired
    private ChangJiangBoxSensorMapper monitorDataMapper;
    @Override
    public int onReceive(SyncMessage msg) {
        if(msg.getDataSync().getSyncType().equals("3")){
            monitorDataMapper.deleteById(msg.getDataSync().getInfoId(),msg.getId());
            logger.debug("==========数据同步完成===========");
            return 0;
        }

        MonitorData monitorData = (MonitorData)msg.getData();
        monitorData.setInfoNode(msg.getId());
        MonitorData old = monitorDataMapper.findById(monitorData);
        if(msg.getDataSync().getSyncType().equals("1")){
            if(old ==null) {
                monitorDataMapper.insert( monitorData);
                return 0;
            }else{
                logger.debug("此条数据已存在!!");
                return 1;
            }
        }else if(msg.getDataSync().getSyncType().equals("4")){
            if(old != null){
                monitorDataMapper.update(monitorData);
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
        //MonitorData  changJiangBoxSensor = changJiangBoxSensorMapper.findById((MonitorData)msg.getData());
        //System.out.println(book);
       // msg.setData(changJiangBoxSensor);
        //return changJiangBoxSensor==null ? null : changJiangBoxSensor.getInfoId();
        return 0l;
    }
}
