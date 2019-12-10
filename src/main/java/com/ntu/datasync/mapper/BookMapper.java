package com.ntu.datasync.mapper;

import com.ntu.datasync.model.po.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: baihua
 * @Date: Created in 11/12/2019 5:29 PM
 */
@Mapper
public interface BookMapper {
    List<Book> findAll();
    void insertBook();
}
