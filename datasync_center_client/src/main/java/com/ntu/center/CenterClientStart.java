package com.ntu.center;

import com.ntu.center.sync.CenterDataReceiver;
import com.ntu.common.client.EMQTTClient;
import com.ntu.common.client.IMQTTClient;
import com.ntu.common.config.ApplicationContextProvider;
import com.ntu.common.model.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class CenterClientStart implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(CenterClientStart.class);
    @Autowired
    ApplicationContextProvider applicationContextProvider;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        IMQTTClient imqttClient = new EMQTTClient(Constant.CENTER_CLIENT_ID,
                Constant.CENTER_CLIENT_USERNAME,
                Constant.CENTER_CLIENT_PASSWORD);

        imqttClient.connect(new CenterDataReceiver(imqttClient,applicationContextProvider));
        imqttClient.subscribe(Constant.NODE_TOPIC);
        imqttClient.subscribe(Constant.NODE2_TOPIC);
        logger.info("中央数据库节点成功连接至MQTT服务器.....");
    }
}
