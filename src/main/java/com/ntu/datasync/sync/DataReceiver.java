package com.ntu.datasync.sync;

import com.ntu.cst.DatasyncType;
import com.ntu.datasync.common.ApplicationContextProvider;
import com.ntu.datasync.common.MsgSerializer;
import com.ntu.datasync.config.SysConfig;
import com.ntu.datasync.mapper.DataSynchroMapper;
import com.ntu.datasync.model.SyncMessage;
import com.ntu.datasync.model.po.DataSynchro;
import com.ntu.datasync.sync.processor.IDataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @Author: baihua
 * @Date: 2019/12/4 13:51
 */
public class DataReceiver {
    private static final Logger logger = LoggerFactory.getLogger(DataReceiver.class);
    private IMQTTClient mc = null;
    private DataSynchroMapper dataSynchroMapper;
    private ApplicationContextProvider applicationContextProvider;

    public DataReceiver (IMQTTClient imqttClient, ApplicationContextProvider acp){
        this.mc = imqttClient;
        this.applicationContextProvider = acp;
        dataSynchroMapper = (DataSynchroMapper) applicationContextProvider.getBean("dataSynchroMapper");
    }

    public void onReceive(String topicName, byte[] data){
        DataSynchro remote = null;
        SyncMessage syncMessage = null;
        String tableName = null;

        try{
            syncMessage = new MsgSerializer().decode(data);
            //用于记录同步的数据属于哪一个数据表
            for(DatasyncType dataType : DatasyncType.values()){
                if(dataType.getId() == syncMessage.getMsgtype()){
                    tableName = dataType.name();
                    break;
                }
            }
            logger.debug("receive from "+topicName+" : "+syncMessage.getDataSynchro()+syncMessage.getData());
            DataSynchro local =
                    dataSynchroMapper.findById(syncMessage.getDataSynchro().getBasicinfoid(), syncMessage.getDataSynchro().getType());
            remote = syncMessage.getDataSynchro();

            if (syncMessage.getMsgtype()== 11) {
                //receive ack
                System.out.println("*******************************************************");
                logger.debug("receive ack "+topicName+" : "+syncMessage.getDataSynchro()+syncMessage.getData());
                if(local != null) {
                    local.setSa1Status(remote.getSa1Status());
                    local.setSc1Time(new Date());
                    if (local.getSa1Status().equals("0")) { //success
                        logger.debug("name-----------"+Thread.currentThread().getName());
                        local.setSd1Num(0L);
                    }
                    else if (local.getSa1Status().equals("2")) {//refuse
                        local.setSd1Num(1L);
                        local.setSe1Time(new Date());
                        local.setSf1Msg(remote.getSf1Msg());
                    }
                    else { //failed
                        local.setSd1Num((null == local.getSd1Num()?1:local.getSd1Num())+1>5?5:(null == local.getSd1Num()?0:local.getSd1Num())+1);
                        local.setSe1Time(new Date());
                        local.setSf1Msg(remote.getSf1Msg());
                    }
                    dataSynchroMapper.updateActive(local);
                }
                return;
            }
            else {//receive data
                logger.debug("receive data from "+topicName+" : "+syncMessage.getDataSynchro()+syncMessage.getData());
                boolean insertFlag = false;
                if(local == null){
                    local = new DataSynchro();
                    local.setBasicinfoid(remote.getBasicinfoid());
                    local.setType(remote.getType());
                    insertFlag = true;
                }
                IDataProcessor iDataProcessor = (IDataProcessor) applicationContextProvider.getBean("Processor"+syncMessage.getMsgtype());
                int processFlag = iDataProcessor.onReceive(syncMessage);
                if (processFlag == 0){
                    //successful
                    local.setSa2Status("0");
                    local.setSb2Time(remote.getSb1Time());
                    local.setSc2Time(new Date());

                }
                else{
                    //failed
                    local.setSa2Status("2");
                    local.setSf2Msg("data expired");
                    local.setSc2Time(new Date());
                }


                if(insertFlag){
                    dataSynchroMapper.insert(local);
                }
                else{
                    dataSynchroMapper.updatePassive(local);
                }
                logger.info(tableName+" 表中的 "+local.getBasicinfoid()+" 号数据同步成功");

                remote.setSa1Status(local.getSa2Status());
                remote.setSc1Time(local.getSc2Time());
                remote.setSf1Msg(local.getSf2Msg());
            }


        }catch (Throwable e){
            logger.info(tableName+" 表中的 " + syncMessage.getDataSynchro().getBasicinfoid()+" 号数据同步失败");
            logger.error(syncMessage.getDataSynchro().getBasicinfoid()+" 号数据同步失败原因: "+e.getMessage());
            remote = new DataSynchro();
            remote.setBasicinfoid(syncMessage.getDataSynchro().getBasicinfoid());
            remote.setType(syncMessage.getDataSynchro().getType());
            remote.setSa1Status("9");
            remote.setSc1Time(new Date());
            remote.setSf1Msg("synchronize data failed");
        }

        try{
            if(syncMessage != null){
                SyncMessage ackMessage = new SyncMessage(11,
                        syncMessage.getClientid(), remote, null);
                byte[] buf = new MsgSerializer().encode(ackMessage);
                //String topicId = (syncMessage.getClientid().startsWith("node"))?topic.TOPIC_SYNC_NODE+smsg.getClientid():topic.TOPIC_SYNC_CENTER;
                logger.debug("发送确认信息==========================");
                mc.publish(SysConfig.CENTER_TOPIC, buf, true);
                logger.debug("确认信息发送完成==========================");
                //mc.publish(topicId, buf, true);
                //LOG.debug("send ack to "+topic.TOPIC_SYNC_NODE+smsg.getClientid()+":"+remote);
            }
        }
        catch(Throwable e) {
            logger.error("确认消息发送异常： ",e);
        }
    }
}
