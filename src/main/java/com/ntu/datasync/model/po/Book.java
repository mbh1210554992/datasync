package com.ntu.datasync.model.po;

/**
 * @Author: baihua
 * @Date: Created in 11/12/2019 5:29 PM
 */


import lombok.Data;

import java.io.Serializable;

@Data
public class Book implements Serializable {

    private Integer book_id;
    private String book_name;
}

