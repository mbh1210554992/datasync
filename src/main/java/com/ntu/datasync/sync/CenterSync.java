package com.ntu.datasync.sync;

import com.ntu.datasync.common.ApplicationContextProvider;
import com.ntu.datasync.config.MoquetteServer;
import com.ntu.datasync.config.SysConfig;
import com.ntu.datasync.mapper.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: baihua
 * @Date: Created in 11/11/2019 10:41 AM
 */
@Component
public class CenterSync {

    @Autowired
    BookMapper bookMapper;
    @Autowired
    ApplicationContextProvider applicationContextProvider;
    private static final Logger logger = LoggerFactory.getLogger(CenterSync.class);
    private MoquetteServer moquetteServer = null;

    
    public void start(MoquetteServer moquetteServer){



        IMQTTClient imqttClient = new EMQTTClient(SysConfig.CENTER_CLIENT_ID,
                SysConfig.CENTER_CLIENT_USERNAME,
                SysConfig.CENTER_CLIENT_PASSWORD);

        imqttClient.connect(new DataReceiver(imqttClient,applicationContextProvider));
        imqttClient.subscribe(SysConfig.NODE_TOPIC);
//        SendThread st = new SendThread("center",applicationContextProvider,imqttClient);
//        Thread node = new Thread(st);
//        node.setName("center");
//        node.start();
        //logger.info("center:"+ bookMapper.findAll());
        
    }



    

}
