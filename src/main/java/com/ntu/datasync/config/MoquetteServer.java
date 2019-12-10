package com.ntu.datasync.config;

import io.moquette.broker.Server;
import io.moquette.broker.config.ClasspathResourceLoader;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.IResourceLoader;
import io.moquette.broker.config.ResourceLoaderConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: baihua
 * @Date: Created in 11/8/2019 5:54 PM
 */

@Slf4j
@Service
public class MoquetteServer {
    @Value("${mqtt-server.config-path}")
    private String configFilePath;


    private Server mqttServer;

    public void startServer() throws IOException {
        IResourceLoader configFileResourceLoader = new ClasspathResourceLoader(configFilePath);
        final IConfig config = new ResourceLoaderConfig(configFileResourceLoader);

        mqttServer = new Server();


        mqttServer.startServer(config);
    }


    public void stop() {
        mqttServer.stopServer();
    }

}