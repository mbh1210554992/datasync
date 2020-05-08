package com.ntu.common.processor;

public interface DataReceiver {
    void onReceive(String topicName, byte[] data);
}
