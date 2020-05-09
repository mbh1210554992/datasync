package com.ntu.common.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: baihua
 * @Date: Created in 11/11/2019 9:41 AM
 */

@Getter
@Component
public class Constant {
    public static String SERVER_URL;
    public static String CENTER_TOPIC1;
    public static String CENTER_TOPIC2;
    public static String CENTER_CLIENT_ID;
    public static String CENTER_CLIENT_USERNAME;
    public static String CENTER_CLIENT_PASSWORD;

    public static String NODE_TOPIC;
    public static String NODE_CLIENT_ID;
    public static String NODE_CLIENT_USERNAME;
    public static String NODE_CLIENT_PASSWORD;

    public static String NODE2_TOPIC;
    public static String NODE2_CLIENT_ID;
    public static String NODE2_CLIENT_USERNAME;
    public static String NODE2_CLIENT_PASSWORD;

    public static Long SCAN_INTERVAL;


    @Value("${mqtt.server.url}")
    public  void setServerUrl(String serverUrl) {
        SERVER_URL = serverUrl;
    }

    @Value("${center.client.topic1}")
    public  void setCenterTopic1(String centerTopic) {
        CENTER_TOPIC1 = centerTopic;
    }

    @Value("${center.client.topic2}")
    public  void setCenterTopic2(String centerTopic2) {
        CENTER_TOPIC2 = centerTopic2;
    }

    @Value("${node.client.topic}")
    public  void setNodeTopic(String nodeTopic) {
        NODE_TOPIC = nodeTopic;
    }

    @Value("${center.client.id}")
    public  void setCenterClientId(String centerClientId) {
        CENTER_CLIENT_ID = centerClientId;
    }

    @Value("${center.client.username}")
    public  void setCenterClientUsername(String centerClientUsername) {
        CENTER_CLIENT_USERNAME = centerClientUsername;
    }

    @Value("${center.client.password}")
    public  void setCenterClientPassword(String centerClientPassword) {
        CENTER_CLIENT_PASSWORD = centerClientPassword;
    }

    @Value("${node.client.id}")
    public  void setNodeClientId(String nodeClientId) {
        NODE_CLIENT_ID = nodeClientId;
    }

    @Value("${node.client.username}")
    public  void setNodeClientUsername(String nodeClientUsername) {
        NODE_CLIENT_USERNAME = nodeClientUsername;
    }

    @Value("${node.client.password}")
    public  void setNodeClientPassword(String nodeClientPassword) {
        NODE_CLIENT_PASSWORD = nodeClientPassword;
    }

    @Value("${task.scan.interval}")
    public  void setScanInterval(Long scanInterval) {
        SCAN_INTERVAL = scanInterval;
    }

    @Value("${node2.client.topic}")
    public  void setNode2Topic(String node2Topic) {
        NODE2_TOPIC = node2Topic;
    }

    @Value("${node2.client.id}")
    public  void setNode2ClientId(String node2ClientId) {
        NODE2_CLIENT_ID = node2ClientId;
    }

    @Value("${node2.client.username}")
    public  void setNode2ClientUsername(String node2ClientUsername) {
        NODE2_CLIENT_USERNAME = node2ClientUsername;
    }

    @Value("${node2.client.password}")
    public  void setNode2ClientPassword(String node2ClientPassword) {
        NODE2_CLIENT_PASSWORD = node2ClientPassword;
    }
}
