package com.ntu.datasync.sync;

import com.ntu.cst.DatasyncType;
import com.ntu.datasync.common.ApplicationContextProvider;
import com.ntu.datasync.common.MsgSerializer;
import com.ntu.datasync.config.DataSourceType;
import com.ntu.datasync.config.SysConfig;
import com.ntu.datasync.mapper.DataSynchroMapper;
import com.ntu.datasync.model.SyncMessage;
import com.ntu.datasync.model.po.DataSynchro;
import com.ntu.datasync.sync.processor.Processor1;
import com.ntu.datasync.sync.processor.IDataProcessor;
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

    public SendThread(String role, ApplicationContextProvider context,IMQTTClient emqttClient){
        applicationContextProvider = context;
        this.role = role;
        //this.sysConfig = new SysConfig();
        this.imqttClient = emqttClient;

    }
    @Override
    public void run() {
        if(role.equals("center")){
            DataSourceType.setDataBaseType(DataSourceType.DataBaseType.Primary);
        }else {
            DataSourceType.setDataBaseType(DataSourceType.DataBaseType.Secondary);
        }
        DataSynchroMapper dataSynchroMapper = (DataSynchroMapper) applicationContextProvider.getBean("dataSynchroMapper");

        while(true){
            try {
                Thread.sleep(SysConfig.SCAN_INTERVAL);
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

                    IDataProcessor dr =(Processor1)applicationContextProvider.getBean("Processor"+type);
                    SyncMessage sm = new SyncMessage();
                    sm.setDataSynchro(ds);
                    //sm.setClientid(role.equals("node") ? );
                    sm.setMsgtype(type);
                    String target = dr.onSend(sm);
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
                    message = new MsgSerializer().encode(sm);
                    String topic = null;
                    if(role.equals("center")){
                        topic = SysConfig.CENTER_TOPIC;
                    }else{
                        topic = SysConfig.NODE_TOPIC;
                    }
                    imqttClient.publish(topic,message,true);
                    logger.info("发送消息"+":" + sm.getData());
                }
            }catch (Exception e){
                logger.error(e.getMessage());

            }




           /* if(role.equals("center")){

            }else{
               // Processor processor = (Processor) applicationContextProvider.getBean("processor");
                SyncMessage syncMessage = new SyncMessage();
                syncMessage.setClientid(sysConfig.getClintid());
                syncMessage.setMsgtype(1);
                syncMessage.setData(books);
                //String target  = processor.onSend(syncMessage);

                byte[] message =new MsgSerializer().encode(syncMessage);

                imqttClient.publish("/sync/test",message,true);
            }
*/
        }
    }
}
