package com.ntu.client2;

import com.ntu.client2.sync.NodeDataReceiver;
import com.ntu.client2.sync.SendThread;
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
public class ClientStart implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ClientStart.class);
    @Autowired
    ApplicationContextProvider applicationContextProvider;
    IMQTTClient imqttClient;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        imqttClient = new EMQTTClient(Constant.NODE2_CLIENT_ID,
                Constant.NODE2_CLIENT_USERNAME,
                Constant.NODE2_CLIENT_PASSWORD);
        imqttClient.connect(new NodeDataReceiver(imqttClient,applicationContextProvider));
        imqttClient.subscribe(Constant.CENTER_TOPIC2);
        SendThread st = new SendThread("node2",applicationContextProvider,imqttClient);
        Thread node = new Thread(st);
        node.setName("node2");
        node.start();
        logger.info("团结河节点成功连接至MQTT服务器.........");


        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                imqttClient.disconnect();
                logger.info("团结河节点断开连接");
            }
        });
    }
}
