package com.ntu.datasync.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: baihua
 * @Date: Created in 11/11/2019 9:41 AM
 */

@Setter
@Getter
@Component
public class SysConfig {
    public   String serverurl;
    public  String clintid;
    public  String password;
    public  int role;

    public SysConfig(){
        this.serverurl = "tcp://0.0.0.0:6666";
        this.clintid = "node88888888";
        this.password = "client1";
        this. role = 1;
    }
}
