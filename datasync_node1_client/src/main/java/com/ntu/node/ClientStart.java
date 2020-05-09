package com.ntu.node;

import com.ntu.common.client.EMQTTClient;
import com.ntu.common.client.IMQTTClient;
import com.ntu.common.config.ApplicationContextProvider;
import com.ntu.common.model.Constant;
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

        imqttClient = new EMQTTClient(Constant.NODE_CLIENT_ID,
                Constant.NODE_CLIENT_USERNAME,
                Constant.NODE_CLIENT_PASSWORD);
        imqttClient.connect(new NodeDataReceiver(imqttClient,applicationContextProvider));
        imqttClient.subscribe(Constant.CENTER_TOPIC1);
        SendThread st = new SendThread("node1",applicationContextProvider,imqttClient);
        Thread node = new Thread(st);
        node.setName("node1");
        node.start();
        logger.info("长江结点成功连接至MQTT服务器.........");

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                imqttClient.disconnect();
                logger.info("长江节点断开连接");
            }
        });
    }
}
