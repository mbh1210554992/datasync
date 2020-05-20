package com.ntu.node.sync.processor;

import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.MonitorData;
import com.ntu.node.mapper.MonitorDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: baihua
 * @Date: 2019/12/4 09:05
 */
@Component(value = "Processor1")
public class MonitorDataProcessor implements IDataProcessor{
    private static final Logger logger = LoggerFactory.getLogger(MonitorDataProcessor.class);

    @Autowired
    private MonitorDataMapper monitorDataMapper;
    @Override
    public int onReceive(SyncMessage msg) {
        MonitorData monitorData = (MonitorData)msg.getData();
        MonitorData old = monitorDataMapper.findById(monitorData.getInfoId());
        if(old !=null)
        {
            monitorDataMapper.update(monitorData);
            return 0;
        }
        monitorDataMapper.insert(monitorData);
        logger.info("==========数据同步完成===========");
        return 0;
    }

    @Override
    public Long onSend(SyncMessage msg) {
        MonitorData monitorData = monitorDataMapper.findById(msg.getDataSync().getInfoId());
        //System.out.println(book);
        msg.setData(monitorData);
        return monitorData ==null ? null : monitorData.getInfoId();
    }
}
