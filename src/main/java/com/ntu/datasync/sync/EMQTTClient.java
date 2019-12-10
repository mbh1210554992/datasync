package com.ntu.datasync.sync;

import com.ntu.datasync.common.MsgSerializer;
import com.ntu.datasync.config.SysConfig;
import lombok.NoArgsConstructor;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


/**
 * @Author: baihua
 * @Date: Created in 11/11/2019 10:30 AM
 */
@NoArgsConstructor
@Service
public class EMQTTClient implements IMQTTClient {
    private static final Logger LOG = LoggerFactory.getLogger(EMQTTClient.class);
    private static final boolean CLEAN_START = true;
    private static final short KEEP_ALIVE = 30;
    private static final long RECONNECTION_DELAY = 5000;

    private SysConfig sysConfig;



    private MqttClient mqttClient= null;
    private String clientid = null;
    private String username = null;
    private String password = null;


    public EMQTTClient(String clientid, String username, String password){
        this.clientid = clientid;
        this.password = password;
        this.username = username;
        this.sysConfig = new SysConfig();
        try{
            mqttClient = new MqttClient(sysConfig.getServerurl(), clientid);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void connect() {
        LOG.info("connecting to server: "+sysConfig.getServerurl());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(CLEAN_START);
        options.setPassword(password.toCharArray());
        options.setUserName(username);
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(KEEP_ALIVE);
        while(true){
            try{
                Thread.sleep(RECONNECTION_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try{
                mqttClient.setCallback(new PushCallback());
                mqttClient.connect(options);
                if(mqttClient.isConnected()){
                    break;
                }
            } catch (Exception e) {
                if(mqttClient.isConnected()){
                    break;
                }
                LOG.error("connect error", e);
            }
        }

        LOG.info("connect successful");
    }

    @Override
    public void subscribe(String topics) {
        try{
            mqttClient.subscribe(topics);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void publish(String topicName, byte[] message, boolean retain) {
        try{
            MqttTopic mt =mqttClient.getTopic(topicName);
            MqttMessage mm =new MqttMessage(message);
            mm.setQos(1);
            mm.setRetained(false);
            MqttDeliveryToken token = mt.publish(mm);
            token.waitForCompletion(3000);
            LOG.info("MQTTServer Message Topic="+topicName+" Content: "+new MsgSerializer().decode(message).getData());
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
