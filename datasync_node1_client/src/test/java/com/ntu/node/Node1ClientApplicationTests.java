package com.ntu.node;

import com.ntu.node.mapper.ChangJiangBoxEnvironmentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Node1ClientApplicationTests {
    @Autowired
    ChangJiangBoxEnvironmentMapper changJiangBoxEnvironmentMapper;

    @Test
    void contextLoads() {
//        ChangJiangBoxSensor changJiangBoxSensor = new ChangJiangBoxSensor();
//        changJiangBoxSensor.setAreaId("123");
//        changJiangBoxSensor.setConductivity(123.22f);
//        changJiangBoxSensor.setDataTime(new Date());
//        changJiangBoxSensor.setNodeId("456");
//        changJiangBoxSensor.setPh(5.52f);
//        changJiangBoxSensor.setSpotId("789");
//        changJiangBoxSensor.setTemperature(30.92f);
//        changJiangBoxSensor.setDissolvedOxygen(42f);
//        changJiangBoxSensor.setSalinity(43.22f);
//        changJiangBoxSensor.setTurbidity(23.22f);
//        changJiangBoxSensorMapper.insert(changJiangBoxSensor);
//        changJiangBoxSensor.setTurbidity(55.55f);
//        changJiangBoxSensorMapper.update(changJiangBoxSensor);
        //System.out.println(changJiangBoxEnvironmentMapper.findById(1L));
    }

}
