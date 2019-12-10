package com.ntu.datasync.aop;

import com.ntu.datasync.config.DataSourceType;
import com.ntu.datasync.sync.NodeSync;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: baihua
 * @Date: Created in 11/12/2019 3:55 PM
 */
@Aspect
@Component
public class DataSourceAop {
//    private static final Logger logger = LoggerFactory.getLogger(DataSourceAop.class);
//
//    //在primary方法前执行
//    @Before("execution(* com.ntu.datasync.sync.CenterSync.start(..))")
//    public void setDataSource2test01() {
//        logger.info("Center业务");
//        DataSourceType.setDataBaseType(DataSourceType.DataBaseType.Primary);
//    }
//
//    //在secondary方法前执行
//    @Before("execution(* com.ntu.datasync.sync.NodeSync.start(..))")
//    public void setDataSource2test02() {
//        logger.info("Node业务");
//        DataSourceType.setDataBaseType(DataSourceType.DataBaseType.Secondary);
//    }
}