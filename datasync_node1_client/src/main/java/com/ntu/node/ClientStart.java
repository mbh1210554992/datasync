package com.ntu.node;

import com.ntu.common.client.EMQTTClient;
import com.ntu.common.client.IMQTTClient;
import com.ntu.common.config.ApplicationContextProvider;
import com.ntu.common.model.SysConfig;
import com.ntu.node.sync.NodeDataReceiver;
import com.ntu.node.sync.SendThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ClientStart implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ClientStart.class);
    @Autowired
    ApplicationContextProvider applicationContextProvider;
    IMQTTClient imqttClient;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        imqttClient = new EMQTTClient(SysConfig.NODE_CLIENT_ID,
                SysConfig.NODE_CLIENT_USERNAME,
                SysConfig.NODE_CLIENT_PASSWORD);
        imqttClient.connect(new NodeDataReceiver(imqttClient,applicationContextProvider));
        imqttClient.subscribe(SysConfig.CENTER_TOPIC);
        SendThread st = new SendThread("node",applicationContextProvider,imqttClient);
        Thread node = new Thread(st);
        node.setName("node");
        node.start();
        logger.info("长江结点成功连接至MQTT服务器.........");
    }
}
