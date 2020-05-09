package com.ntu.common.client;

import com.ntu.common.processor.DataReceiver;

/**
 * @Author: baihua
 * @Date: Created in 11/11/2019 10:23 AM
 */

public interface IMQTTClient {
    void connect(DataReceiver dr);

    void subscribe(String topics);

    void publish(String topicName, byte[] message, boolean retain);

    void disconnect();
}
