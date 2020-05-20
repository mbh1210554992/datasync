package com.ntu.client2.sync;

import com.ntu.client2.mapper.DataSyncMapper;
import com.ntu.client2.sync.processor.IDataProcessor;
import com.ntu.common.client.IMQTTClient;
import com.ntu.common.config.ApplicationContextProvider;
import com.ntu.common.config.MsgSerializer;
import com.ntu.common.cst.DatasyncType;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.DataSync;
import com.ntu.common.processor.DataReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @Author: baihua
 * @Date: 2019/12/4 13:51
 */
public class NodeDataReceiver implements DataReceiver {
    private static final Logger logger = LoggerFactory.getLogger(NodeDataReceiver.class);
    private IMQTTClient mc = null;
    private DataSyncMapper dataSyncMapper;
    private ApplicationContextProvider applicationContextProvider;

    public NodeDataReceiver(IMQTTClient imqttClient, ApplicationContextProvider acp){
        this.mc = imqttClient;
        this.applicationContextProvider = acp;
        dataSyncMapper = (DataSyncMapper) applicationContextProvider.getBean("dataSyncMapper");
    }

    public void onReceive(String topicName, byte[] data){
        DataSync remote = null;
        SyncMessage syncMessage = null;
        String tableName = null;

        try{
            syncMessage = new MsgSerializer().decode(data);
            //用于记录同步的数据属于哪一个数据表
            for(DatasyncType dataType : DatasyncType.values()){
                if(dataType.getFlag().equals(syncMessage.getDataSync().getInfoType())){
                    tableName = dataType.name();
                    break;
                }
            }
            logger.debug("receive from "+topicName+" : "+syncMessage.getDataSync().getInfoType()+syncMessage.getData());
            DataSync local =
                    dataSyncMapper.findByKey(syncMessage.getDataSync().getInfoId(), syncMessage.getDataSync().getInfoType());
            remote = syncMessage.getDataSync();

            if(syncMessage.getMsgtype() == 11){
                //receive ack
                logger.info("收到确认消息.....");
                logger.debug("receive ack "+topicName+" : "+syncMessage.getDataSync()+syncMessage.getData());
                if(local != null) {
                    local.setSyncStatus(remote.getSyncStatus());
                    //local.setSuccessTime(remote.getSuccessTime());
                    if (local.getSyncStatus().equals("0")) { //success
                        logger.debug("name-----------"+Thread.currentThread().getName());
                        local.setSyncMsg(remote.getSyncMsg());
                        local.setSuccessTime(remote.getSuccessTime());
                        logger.info(tableName+"表中 "+local.getInfoId()+" 号数据同步成功....");
                    }
                    else if (local.getSyncStatus().equals("2")) {
                        local.setSyncMsg(remote.getSyncMsg());
                        local.setFailTime(remote.getFailTime());
                        local.setFailCount(remote.getFailCount());
                        local.setSuccessTime(null);
                        logger.error(tableName+"表中 "+local.getInfoId()+" 号数据同步失败,已失败"+local.getFailCount()+"次");
                    }
                    else { //failed
                        local.setSyncMsg(remote.getSyncMsg());
                        local.setFailTime(remote.getFailTime());
                        local.setFailCount(remote.getFailCount());
                        local.setSuccessTime(null);
                        logger.error(tableName+"表中 "+local.getInfoId()+" 号数据同步失败，本条数据将停止同步");
                    }
                    dataSyncMapper.updateActive(local);
                }
                return;
            }
            else {//receive data
                logger.debug("receive data from "+topicName+" : "+syncMessage.getDataSync()+syncMessage.getData());
                boolean insertFlag = false;
                if(local == null){
                    local = new DataSync();
                    local.setInfoId(remote.getInfoId());
                    local.setInfoType(remote.getInfoType());
                    insertFlag = true;
                }
                IDataProcessor iDataProcessor = (IDataProcessor) applicationContextProvider.getBean("Processor"+syncMessage.getMsgtype());
                int processFlag = iDataProcessor.onReceive(syncMessage);
                if (processFlag == 0){
                    //successful
                    local.setSyncStatus("0");
                    local.setSuccessTime(new Date());
                    local.setSyncMsg("数据同步成功");

                }
                else{
                    //failed
                    local.setSyncStatus("2");
                    local.setSyncMsg("数据同步失败");
                    local.setFailTime(new Date());
                }


                if(insertFlag){
                    dataSyncMapper.insert(local);
                }
                else{
                    dataSyncMapper.updateActive(local);
                }
                logger.info(syncMessage.getClientid()+" 节点的 "+tableName+" 表中的 "+local.getInfoId()+" 号数据同步成功 "+"同步时间："+local.getSuccessTime());
                remote.setSyncStatus(local.getSyncStatus());
                remote.setSuccessTime(local.getSuccessTime());
            }


        }catch (Throwable e){
            //logger.error(tableName+" 表中的 " + syncMessage.getDataSync().getInfoId()+" 号数据同步失败");
            logger.error(e.getMessage(),e);
        }

    }
}
