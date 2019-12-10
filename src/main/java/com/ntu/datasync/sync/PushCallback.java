package com.ntu.datasync.sync;

import com.ntu.datasync.common.MsgSerializer;
import com.ntu.datasync.model.SyncMessage;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: baihua
 * @Date: Created in 11/11/2019 5:01 PM
 */
@Component
public class PushCallback implements MqttCallback {
    private static final Logger logger = LoggerFactory.getLogger(PushCallback.class);

    @Override
    public void connectionLost(Throwable throwable) {
        logger.info("-----------连接断开-----------");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        logger.info("收到的主题： "+topic);
        //logger.info("接收消息内容： "+message.getPayload());
        logger.info("接收消息Qos： "+message.getQos());
        SyncMessage syncMessage = new MsgSerializer().decode(message.getPayload());
        logger.info("接收消息内容： "+ syncMessage.getData());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.info("deliveryComplete---------"+token.isComplete());
    }
}
