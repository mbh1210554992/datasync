package com.ntu.datasync.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: baihua
 * @Date: Created in 11/11/2019 9:41 AM
 */

@Getter
@Component
public class SysConfig {
    public static String SERVER_URL;
    public static String CENTER_TOPIC;
    public static String CENTER_CLIENT_ID;
    public static String CENTER_CLIENT_USERNAME;
    public static String CENTER_CLIENT_PASSWORD;

    public static String NODE_TOPIC;
    public static String NODE_CLIENT_ID;
    public static String NODE_CLIENT_USERNAME;
    public static String NODE_CLIENT_PASSWORD;

    public static Long SCAN_INTERVAL;


    @Value("${mqtt.server.url}")
    public  void setServerUrl(String serverUrl) {
        SERVER_URL = serverUrl;
    }

    @Value("${center.client.topic}")
    public  void setCenterTopic(String centerTopic) {
        CENTER_TOPIC = centerTopic;
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
}
