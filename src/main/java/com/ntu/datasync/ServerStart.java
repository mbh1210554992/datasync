package com.ntu.datasync;

import com.ntu.datasync.config.MoquetteServer;
import com.ntu.datasync.config.SysConfig;
import com.ntu.datasync.sync.CenterSync;
import com.ntu.datasync.sync.NodeSync;
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
    @Autowired
    private CenterSync centerSync;
    @Autowired
    private NodeSync nodeSync;
    private static final Logger logger = LoggerFactory.getLogger(ServerStart.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            moquetteServer.startServer();

        } catch (IOException e) {
            e.printStackTrace();
        }

        centerSync.start(moquetteServer);
        try {
            nodeSync.start(moquetteServer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                moquetteServer.stop();
                logger.info("Moquette Server stopped");
            }
        });

    }
}
