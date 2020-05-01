package com.ntu.server;

import com.ntu.server.config.MoquetteServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: baihua
 * @Date: Created in 11/12/2019 1:48 PM
 */
@Component
public class ServerStart implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private MoquetteServer moquetteServer;

    private static final Logger logger = LoggerFactory.getLogger(ServerStart.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        try {
            moquetteServer.startServer();
            logger.info("MQTT 服务器启动成功");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                moquetteServer.stop();
                logger.info("MQTT 服务器停止");
            }
        });

    }
}
