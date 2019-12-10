package com.ntu.datasync.sync;

import com.ntu.datasync.common.ApplicationContextProvider;
import com.ntu.datasync.common.MsgSerializer;
import com.ntu.datasync.config.DataSourceType;
import com.ntu.datasync.config.SysConfig;
import com.ntu.datasync.mapper.BookMapper;
import com.ntu.datasync.model.SyncMessage;
import com.ntu.datasync.model.po.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author: baihua
 * @Date: 2019/11/25 11:48
 */
public class SendThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(SendThread.class);

    private ApplicationContextProvider applicationContextProvider;

    private String role;
    private SysConfig sysConfig;
    private IMQTTClient imqttClient;

    public SendThread(String role, ApplicationContextProvider context,IMQTTClient emqttClient){
        applicationContextProvider = context;
        this.role = role;
        this.sysConfig = new SysConfig();
        this.imqttClient = emqttClient;

    }
    @Override
    public void run() {
        if(role.equals("center")){
            DataSourceType.setDataBaseType(DataSourceType.DataBaseType.Primary);
        }else {
            DataSourceType.setDataBaseType(DataSourceType.DataBaseType.Secondary);
        }
        BookMapper bookMapper = (BookMapper) applicationContextProvider.getBean("bookMapper");

        while(true){
            List<Book> books = bookMapper.findAll();
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(role.equals("center")){

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

        }
    }
}
