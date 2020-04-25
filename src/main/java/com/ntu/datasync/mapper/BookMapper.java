package com.ntu.datasync.mapper;

import com.ntu.datasync.model.po.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: baihua
 * @Date: Created in 11/12/2019 5:29 PM
 */
@Component
public interface BookMapper {
    List<Book> findAll();
    Book findById(@Param("bookId") String bookId);
    void insertBook(Book book);
    void updateBook(Book book);
}
