package com.ntu.center.sync;

import com.ntu.center.mapper.DataSynchroMapper;
import com.ntu.center.sync.processor.IDataProcessor;
import com.ntu.common.client.IMQTTClient;
import com.ntu.common.config.ApplicationContextProvider;
import com.ntu.common.config.MsgSerializer;
import com.ntu.common.cst.DatasyncType;
import com.ntu.common.model.po.DataSynchro;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.Constant;
import com.ntu.common.processor.DataReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @Author: baihua
 * @Date: 2019/12/4 13:51
 */
public class CenterDataReceiver implements DataReceiver {
    private static final Logger logger = LoggerFactory.getLogger(CenterDataReceiver.class);
    private IMQTTClient mc = null;
    private DataSynchroMapper dataSynchroMapper;
    private ApplicationContextProvider applicationContextProvider;

    public CenterDataReceiver(IMQTTClient imqttClient, ApplicationContextProvider acp){
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
                logger.info("*******************************************************");
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
                    local.setSa1Status("0");
                    local.setSb2Time(remote.getSb1Time());
                    local.setSc2Time(new Date());
                    local.setSc1Time(new Date());
                    local.setSf1Msg("data synchronize success");

                }
                else{
                    //failed
                    local.setSa1Status("2");
                    local.setSf1Msg("data expired");
                    local.setSe1Time(new Date());
                }


                if(insertFlag){
                    dataSynchroMapper.insert(local);
                }
                else{
                    dataSynchroMapper.updateActive(local);
                }
                logger.info(syncMessage.getClientid()+" 节点的 "+tableName+" 表中的 "+local.getBasicinfoid()+" 号数据同步成功");

                remote.setSa1Status(local.getSa1Status());
                remote.setSc1Time(local.getSc2Time());
                remote.setSf1Msg(local.getSf1Msg());
            }


        }catch (Throwable e){
            logger.info(syncMessage.getClientid()+" 节点的 "+tableName+" 表中的 " + syncMessage.getDataSynchro().getBasicinfoid()+" 号数据同步失败");
            logger.error(syncMessage.getClientid()+" 节点的 "+syncMessage.getDataSynchro().getBasicinfoid()+" 号数据同步失败原因: "+e.getMessage());
            remote = new DataSynchro();
            remote.setBasicinfoid(syncMessage.getDataSynchro().getBasicinfoid());
            remote.setType(syncMessage.getDataSynchro().getType());
            remote.setSa1Status("9");
            remote.setSc1Time(new Date());
            remote.setSf1Msg("synchronize data failed");

        }

        try{
            if(syncMessage != null){
                SyncMessage ackMessage = new SyncMessage(syncMessage.getMsgtype(),
                        syncMessage.getClientid(), remote, null);
                byte[] buf = new MsgSerializer().encode(ackMessage);
                //String topicId = (syncMessage.getClientid().startsWith("node"))?topic.TOPIC_SYNC_NODE+smsg.getClientid():topic.TOPIC_SYNC_CENTER;
                logger.debug("发送确认信息==========================");
                if(Constant.NODE_CLIENT_ID.equals(syncMessage.getClientid())){
                    mc.publish(Constant.CENTER_TOPIC1, buf, true);
                }else if(Constant.NODE2_CLIENT_ID.equals(syncMessage.getClientid())){
                    mc.publish(Constant.CENTER_TOPIC2, buf, true);
                }

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
