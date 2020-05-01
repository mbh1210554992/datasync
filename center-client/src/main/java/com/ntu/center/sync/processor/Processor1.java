package com.ntu.center.sync.processor;

import com.ntu.center.mapper.BookMapper;
import com.ntu.common.model.SyncMessage;
import com.ntu.common.model.po.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: baihua
 * @Date: 2019/12/4 09:05
 */
@Component(value = "Processor1")
public class Processor1 implements IDataProcessor{

    @Autowired
    private BookMapper bookMapper;
    @Override
    public int onReceive(SyncMessage msg) {
        Book book = (Book)msg.getData();
        Book old = bookMapper.findById(book.getBookId());
        if(old !=null)
        {
            /*if(book.getUpddate() == null ||  old.getUpddate().before(msg.getDataSynchro().getSb1Time()))
            {
                bookMapper.updateSelective(book);
                return 0;
            }else if(old.getUpddate().equals(msg.getSynchro().getSb1Time()))
            {
                return 0;
            }else
            {
                return 1;
            }*/
            //return 0;
            bookMapper.updateBook(book);
            return 0;
        }
        bookMapper.insertBook(book);
        System.out.println("==============================================================");
        return 0;
    }

    @Override
    public String onSend(SyncMessage msg) {
        Book book = bookMapper.findById(msg.getDataSynchro().getBasicinfoid());
        //System.out.println(book);
        msg.setData(book);
        return book==null ? null : book.getBookId();
    }
}
