package com.ntu.datasync;

import com.ntu.datasync.config.MoquetteServer;
import com.ntu.datasync.config.SysConfig;
import com.ntu.datasync.sync.CenterSync;
import com.ntu.datasync.sync.NodeSync;
import io.moquette.broker.Server;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class DatasyncApplicationTests {

	@Autowired
	private SysConfig sysConfig;
	@Test
	void contextLoads() throws IOException, InterruptedException {
		System.out.println("sysConfig: "+sysConfig.getClintid());
	}

}
