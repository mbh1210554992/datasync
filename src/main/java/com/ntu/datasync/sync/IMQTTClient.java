package com.ntu.datasync.sync;

import org.springframework.stereotype.Component;

/**
 * @Author: baihua
 * @Date: Created in 11/11/2019 10:23 AM
 */
@Component
public interface IMQTTClient {
    public void connect();

    public void subscribe(String topics);

    public void publish(String topicName, byte[] message, boolean retain);
}
