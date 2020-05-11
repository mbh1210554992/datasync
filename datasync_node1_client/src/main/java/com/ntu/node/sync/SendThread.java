package com.ntu.node.sync;

import com.ntu.common.client.IMQTTClient;
import com.ntu.common.config.ApplicationContextProvider;
import com.ntu.common.config.MsgSerializer;
import com.ntu.common.cst.DatasyncType;
import com.ntu.common.model.Constant;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.DataSynchro;
import com.ntu.node.mapper.DataSynchroMapper;
import com.ntu.node.sync.processor.IDataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: baihua
 * @Date: 2019/11/25 11:48
 */
public class SendThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(SendThread.class);

    private ApplicationContextProvider applicationContextProvider;

    private String role;
    //private SysConfig sysConfig;
    private IMQTTClient imqttClient;

    public SendThread(String role, ApplicationContextProvider context, IMQTTClient emqttClient){
        applicationContextProvider = context;
        this.role = role;
        //this.sysConfig = new SysConfig();
        this.imqttClient = emqttClient;

    }
    @Override
    public void run() {

        DataSynchroMapper dataSynchroMapper = (DataSynchroMapper) applicationContextProvider.getBean("dataSynchroMapper");

        while(true){
            try {
                Thread.sleep(Constant.SCAN_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try{
                List<DataSynchro> data = dataSynchroMapper.querySyncData();
                for(DataSynchro ds : data){
                    if(ds.getSa1Status().equals("9")){
                        //主动同步失败，检查重发时间
                        Long min = ds.getSd1Num()*6;
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(ds.getSe1Time());
                        cal.add(Calendar.MINUTE, min.intValue());
                        if(cal.after(new Date()))
                        {
                            continue;
                        }
                    }

                    byte[] message = null;
                    int type = 0;
                    for(DatasyncType dataType : DatasyncType.values()){
                         if(dataType.getFlag().equals(ds.getType())){
                             type = dataType.getId();
                             break;
                         }
                    }

                    if(type == 0){
                        logger.error("同步数据不存在："+ds);
                        ds.setSa1Status("2");
                        ds.setSc1Time(new Date());
                        ds.setSf1Msg("data not found");
                        ds.setSd1Num(5L);
                        dataSynchroMapper.updateActive(ds);
                        continue;
                    }

                    IDataProcessor dr =(IDataProcessor)applicationContextProvider.getBean("Processor"+type);
                    SyncMessage sm = new SyncMessage();
                    sm.setDataSynchro(ds);
                    sm.setMsgtype(type);
                    //如果是删除操作，则不需要去对应的表里查询数据
                    if(!ds.getSa1Status().equals("3")){
                        dr.onSend(sm);
                        //logger.info("查询到新增的数据:"+sm.getData());
                        if(sm.getData() == null){
                            logger.error("同步数据不存在："+ds);
                            ds.setSa1Status("2");
                            ds.setSc1Time(new Date());
                            ds.setSf1Msg("data not found");
                            ds.setSd1Num(5L);
                            dataSynchroMapper.updateActive(ds);
                            continue;
                        }
                    }

                    String topic = Constant.NODE_TOPIC;
                    sm.setClientid(Constant.NODE_CLIENT_ID);
                    message = new MsgSerializer().encode(sm);

                    imqttClient.publish(topic,message,true);
                    logger.info("发送消息"+":" + sm.getData());
                }
            }catch (Exception e){
                logger.error(e.getMessage());

            }

        }
    }
}
