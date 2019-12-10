package com.ntu.datasync.sync.processor;

import com.ntu.datasync.model.SyncMessage;
import com.ntu.datasync.model.po.Book;
import org.springframework.stereotype.Component;

/**
 * @Author: baihua
 * @Date: 2019/12/4 09:05
 */
@Component
public class Processor implements IDataProcessor{

    @Override
    public int onReceive(SyncMessage msg) {
//        Book book = (Book)msg.getData();
//        //CollectPhotoinfo old = collectPhotoinfoMapper.queryInfo(book.getBasicinfoid(), book.getType());
//        if(book !=null)
//        {
//            if(book.getUpddate() == null ||  old.getUpddate().before(msg.getSynchro().getSb1Time()))
//            {
//                collectPhotoinfoMapper.updateSelective(book);
//                return 0;
//            }else if(old.getUpddate().equals(msg.getSynchro().getSb1Time()))
//            {
//                return 0;
//            }else
//            {
//                return 1;
//            }
//        }
//        collectPhotoinfoMapper.insert(book);
        return 0;
    }

    @Override
    public String onSend(SyncMessage msg) {
        return null;
    }
}
