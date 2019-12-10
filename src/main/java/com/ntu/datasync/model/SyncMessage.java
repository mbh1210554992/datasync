package com.ntu.datasync.model;

import com.ntu.datasync.model.po.DataSynchro;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * @Author: baihua
 * @Date: Created in 11/12/2019 11:06 AM
 */
@Data
@Getter
@Setter
public class SyncMessage implements Serializable {

    private static final long serialVersionUID = -6468546683623763722L;
    private String id = "";
    private int msgtype = 0;
    private String clientid = "";
    private DataSynchro dataSynchro;
    private Object data;

    public SyncMessage(){
        id = UUID.randomUUID().toString();
    }

    public SyncMessage(int msgtype, String clientid, DataSynchro dataSynchro, Object data){
        this.clientid = clientid;
        this.msgtype = msgtype;
        this.dataSynchro = dataSynchro;
        this.data = data;
    }
}
