package com.ntu.datasync;

import com.ntu.datasync.config.SysConfig;
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
		//System.out.println("sysConfig: "+sysConfig.getClintid());
	}

}
