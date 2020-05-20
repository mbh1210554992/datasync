package com.ntu.center.sync;

import com.ntu.center.mapper.DataSyncMapper;
import com.ntu.center.sync.processor.IDataProcessor;
import com.ntu.common.client.IMQTTClient;
import com.ntu.common.config.ApplicationContextProvider;
import com.ntu.common.config.MsgSerializer;
import com.ntu.common.cst.DatasyncType;
import com.ntu.common.model.Constant;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.DataSync;
import com.ntu.common.processor.DataReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author: baihua
 * @Date: 2019/12/4 13:51
 */
public class CenterDataReceiver implements DataReceiver {
    private static final Logger logger = LoggerFactory.getLogger(CenterDataReceiver.class);
    private IMQTTClient mc = null;
    private DataSyncMapper dataSyncMapper;
    private ApplicationContextProvider applicationContextProvider;

    public CenterDataReceiver(IMQTTClient imqttClient, ApplicationContextProvider acp){
        this.mc = imqttClient;
        this.applicationContextProvider = acp;
        dataSyncMapper = (DataSyncMapper) applicationContextProvider.getBean("dataSyncMapper");
    }
    @Transactional
    public void onReceive(String topicName, byte[] data){
        DataSync remote = null;
        SyncMessage syncMessage = null;
        String tableName = null;
        String nodeName = null;
        Boolean expFlag = false;
        try{
            syncMessage = new MsgSerializer().decode(data);
            //用于记录同步的数据属于哪一个数据表
            for(DatasyncType dataType : DatasyncType.values()){
                if(dataType.getId() == syncMessage.getMsgtype()){
                    tableName = dataType.name();
                    break;
                }
            }
            if(Constant.NODE_CLIENT_ID.equals(syncMessage.getClientid())){
                nodeName ="changjiang";
            }else if(Constant.NODE2_CLIENT_ID.equals(syncMessage.getClientid())){
                nodeName ="tuanjiehe";
            }
            logger.debug("receive from "+topicName+" : "+syncMessage.getDataSync()+syncMessage.getData());
            DataSync local =
                    dataSyncMapper.findByKey(syncMessage.getDataSync().getInfoId(), syncMessage.getDataSync().getInfoType(),nodeName);
            remote = syncMessage.getDataSync();

            if (syncMessage.getMsgtype()== 11) {
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
                    else if (local.getSyncType().equals("2")) {
                        local.setSyncMsg(remote.getSyncMsg());
                        local.setFailTime(remote.getFailTime());
                        local.setFailCount(remote.getFailCount());
                        logger.error(tableName+"表中 "+local.getInfoId()+" 号数据同步失败,已失败"+local.getFailCount()+"次");
                    }
                    else { //failed
                        local.setSyncMsg(remote.getSyncMsg());
                        local.setFailTime(remote.getFailTime());
                        local.setFailCount(remote.getFailCount());
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
                    local.setSyncType(remote.getSyncType());
                    local.setInfoNode(nodeName);
                    insertFlag = true;
                }
                IDataProcessor iDataProcessor = (IDataProcessor) applicationContextProvider.getBean("Processor"+syncMessage.getMsgtype());
                syncMessage.setId(nodeName);
                int processFlag = iDataProcessor.onReceive(syncMessage);
                if (processFlag == 0){
                    //successful
                    local.setSyncStatus("0");
                    local.setSuccessTime(new Date());
                    local.setSyncMsg("数据同步成功");
                    remote.setSyncStatus(local.getSyncStatus());
                    remote.setSuccessTime(local.getSuccessTime());
                    remote.setSyncMsg("数据同步成功");
                    logger.info(syncMessage.getClientid()+" 节点的 "+tableName+" 表中的 "+local.getInfoId()+" 号数据同步成功 "+"同步时间："+local.getSuccessTime());
                }
                else if (processFlag == 1){
                    //failed
                    local.setSyncStatus("9");
                    local.setSyncMsg("中央数据库中已存在此条数据");
                    local.setFailTime(new Date());
                    local.setSuccessTime(null);
                    remote.setSyncStatus(local.getSyncStatus());
                    remote.setFailTime(local.getFailTime());
                    remote.setSyncMsg(local.getSyncMsg());
                    remote.setSuccessTime(local.getSuccessTime());
                    logger.info(syncMessage.getClientid()+" 节点的 "+tableName+" 表中的 "+syncMessage.getDataSync().getInfoId()+" 号数据新增失败,失败原因："+local.getSyncMsg());
                    logger.error(syncMessage.getClientid()+" 节点的 "+tableName+" 表中的 "+syncMessage.getDataSync().getInfoId()+" 号数据同步失败原因: "+local.getSyncMsg());
                }else if(processFlag == 2){
                    //failed
                    local.setSyncStatus("9");
                    local.setSyncMsg("中央数据库没有此条数据");
                    local.setFailTime(new Date());
                    local.setSuccessTime(null);
                    remote.setSyncStatus(local.getSyncStatus());
                    remote.setFailTime(local.getFailTime());
                    remote.setSyncMsg(local.getSyncMsg());
                    remote.setSuccessTime(local.getSuccessTime());
                    logger.info(syncMessage.getClientid()+" 节点的 "+tableName+" 表中的 "+syncMessage.getDataSync().getInfoId()+" 号数据更新失败,失败原因："+local.getSyncMsg());
                    logger.error(syncMessage.getClientid()+" 节点的 "+tableName+" 表中的 "+syncMessage.getDataSync().getInfoId()+" 号数据同步失败原因:  "+local.getSyncMsg());
                }

                local.setInfoNode(nodeName);

                if(insertFlag){
                    dataSyncMapper.insert(local);
                }
                else{
                    dataSyncMapper.updateActive(local);
                }


            }


        }catch (Throwable e){
            int failedNum = syncMessage.getDataSync().getFailCount()+1;
            StringBuilder info = new StringBuilder();
            String syncStatus;
            if(failedNum < 5){
                syncStatus = "2";
                info = info.append("已失败").append(failedNum).append("次");
            }else {
                syncStatus = "9";
                info = info.append("已失败").append(failedNum).append("次,本条数据将停止同步");
            }
            remote = new DataSync();
            remote.setInfoId(syncMessage.getDataSync().getInfoId());
            remote.setInfoType(syncMessage.getDataSync().getInfoType());
            remote.setSyncStatus(syncStatus);
            remote.setSyncType(syncMessage.getDataSync().getSyncType());
            remote.setFailTime(new Date());
            remote.setFailCount(failedNum);
            remote.setSuccessTime(null);
            remote.setSyncMsg("中央数据库端发生异常");
            expFlag = true;
            logger.info(syncMessage.getClientid()+" 节点的 "+tableName+" 表中的 "+syncMessage.getDataSync().getInfoId()+" 号数据同步失败,"+info);
            logger.error(syncMessage.getClientid()+" 节点的 "+tableName+" 表中的 "+syncMessage.getDataSync().getInfoId()+" 号数据同步失败原因: ");
            logger.error(e.getMessage(),e);
        }

        try{
            if(syncMessage != null){
                SyncMessage ackMessage = new SyncMessage(11,
                        syncMessage.getClientid(), remote, null);
                byte[] buf = new MsgSerializer().encode(ackMessage);
                logger.debug("发送确认信息==========================");
                if(Constant.NODE_CLIENT_ID.equals(syncMessage.getClientid())){
                    mc.publish(Constant.CENTER_TOPIC1, buf, true);
                }else if(Constant.NODE2_CLIENT_ID.equals(syncMessage.getClientid())){
                    mc.publish(Constant.CENTER_TOPIC2, buf, true);
                }

                logger.debug("确认信息发送完成==========================");
                if(expFlag){
                    DataSync local =
                            dataSyncMapper.findByKey(syncMessage.getDataSync().getInfoId(), syncMessage.getDataSync().getInfoType(),nodeName);
                    remote.setInfoNode(nodeName);
                    if(local == null){
                        dataSyncMapper.insert(remote);
                    }else{
                        dataSyncMapper.updateActive(remote);
                    }
                }
            }
        }
        catch(Throwable e) {
            logger.error(e.getMessage(),e);
        }
    }
}
