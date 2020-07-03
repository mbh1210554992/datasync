package com.ntu.common.client;

import com.ntu.common.config.MsgSerializer;
import com.ntu.common.model.Constant;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.processor.DataReceiver;
import com.ntu.common.util.SslUtil;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: baihua
 * @Date: Created in 11/11/2019 10:30 AM
 */
public class EMQTTClient implements IMQTTClient {
    private static final Logger logger = LoggerFactory.getLogger(EMQTTClient.class);
    private static final boolean CLEAN_START = true;
    private static final short KEEP_ALIVE = 30;
    private static final long RECONNECTION_DELAY = 5000;
    public  ExecutorService thread = Executors.newCachedThreadPool();

   /* @Autowired
    private SysConfig sysConfig;*/

    private DataReceiver dr;




    private MqttClient mqttClient= null;
    private String clientid = null;
    private String username = null;
    private String password = null;


    public EMQTTClient(String clientid, String username, String password){
        this.clientid = clientid;
        this.password = password;
        this.username = username;
        //this.sysConfig = new SysConfig();
        try{
            mqttClient = new MqttClient(Constant.SERVER_URL, clientid);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }


    public void connect(DataReceiver dr) {
        logger.info("connecting to server: "+ Constant.SERVER_URL);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(CLEAN_START);
        options.setPassword(password.toCharArray());
        options.setUserName(username);
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(KEEP_ALIVE);
        this.dr = dr;
        while(true){
            try{
                Thread.sleep(RECONNECTION_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try{
                options.setSocketFactory(SslUtil.getSocketFactory("d://ca.crt","d://client.crt","d://client.key","123455"));
                mqttClient.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable throwable) {
                        logger.error("-----------连接断开-----------");
                        connect(EMQTTClient.this.dr);
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        logger.debug("收到的主题： "+topic);
                        //logger.info("接收消息内容： "+message.getPayload());
                        logger.debug("接收消息Qos： "+message.getQos());
                        SyncMessage syncMessage = new MsgSerializer().decode(message.getPayload());
                        logger.debug("接收消息内容： "+ syncMessage.getData());
                        thread.execute(new Runnable() {
                            public void run() {
                                if(EMQTTClient.this.dr!=null)
                                    EMQTTClient.this.dr.onReceive(topic, message.getPayload());
                            }
                        });

                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {
                        logger.debug("deliveryComplete---------"+token.isComplete());
                    }
                });
                mqttClient.connect(options);
                if(mqttClient.isConnected()){
                    break;
                }
            } catch (Exception e) {
                if(mqttClient.isConnected()){
                    break;
                }
                logger.error("connect error", e);
            }
        }

        logger.debug("connect successful");
    }

    @Override
    public void subscribe(String topics) {
        try{
            mqttClient.subscribe(topics);
        } catch (MqttException e) {
            logger.error(e.getMessage(),e);
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
            logger.debug("MQTTServer Message Topic="+topicName+" Content: "+new MsgSerializer().decode(message).getData());
        } catch (MqttPersistenceException e) {
            logger.error(e.getMessage(),e);
        } catch (MqttException e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Override
    public void disconnect() {
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            logger.error(e.getMessage(),e);
        }
    }
}
