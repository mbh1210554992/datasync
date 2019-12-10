package com.ntu.datasync.util;

/**
 * Info
 *
 * @author: Jie.Tan
 * @verison: v1.0
 * @time: Created in 2019/11/12 10:07 PM
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtil {

  private static Logger logger = LoggerFactory.getLogger(LogUtil.class);

  public static void info(Object obj) {
    String location = "";
    StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
    location = stacks[2].getClassName() + "." + stacks[2].getMethodName()
        + "(" + stacks[2].getLineNumber() + ")";

    if (obj instanceof Exception) {
      Exception e = (Exception) obj;
      StringWriter sw = new StringWriter();
      e.printStackTrace(new PrintWriter(sw, true));
      String str = sw.toString();
      logger.info(location + str);
    } else {
      logger.info(location + obj.toString());
    }
  }

  public static void warn(Object obj) {
    String location = "";
    StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
    location = stacks[2].getClassName() + "." + stacks[2].getMethodName()
        + "(" + stacks[2].getLineNumber() + ")";

    if (obj instanceof Exception) {
      Exception e = (Exception) obj;
      StringWriter sw = new StringWriter();
      e.printStackTrace(new PrintWriter(sw, true));
      String str = sw.toString();
      logger.warn(location + str);
    } else {
      logger.warn(location + obj.toString());
    }
  }

  public static void error(Object obj) {
    String location = "";
    StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
    location = stacks[2].getClassName() + "." + stacks[2].getMethodName() + "(" + stacks[2].getLineNumber() + ")";
    if (obj instanceof Exception) {
      Exception e = (Exception)obj;
      StringWriter sw = new StringWriter();
      e.printStackTrace(new PrintWriter(sw, true));
      String str = sw.toString();
      logger.error(location + str);
    } else {
      logger.error(location + obj.toString());
    }

  }



}
