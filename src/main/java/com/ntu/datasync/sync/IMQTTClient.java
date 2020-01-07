package com.ntu.datasync.sync;

/**
 * @Author: baihua
 * @Date: Created in 11/11/2019 10:23 AM
 */

public interface IMQTTClient {
    public void connect(DataReceiver dr);

    public void subscribe(String topics);

    public void publish(String topicName, byte[] message, boolean retain);
}
