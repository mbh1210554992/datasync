package com.ntu.center.controller;

import com.ntu.common.exception.CommonException;
import com.ntu.common.model.Result;
import com.ntu.common.model.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class LogInfoController {

    private static final Logger logger = LoggerFactory.getLogger(LogInfoController.class);

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @ResponseBody
    @PostMapping("/getLogInfo")
    public Result getLogInfo(@RequestParam("date") String dateStr) throws CommonException {
        List<String> list;
        FileInputStream is = null;
        try{
            is = new FileInputStream("logs/center/data_sync-info." + dateStr + ".log");
        }catch (FileNotFoundException e){
            logger.error(e.getMessage(),e);
            throw new CommonException(ResultCode.FAIL);
        }
        list = getText(is,"info");
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            String[] temp = str.split("\\.");
            String[] temp2 = str.split(" - ");
            if (temp2.length == 1) {
                continue;
            }
            sb.append(temp[0]).append(" - ").append(temp2[temp2.length - 1]);
            sb.append("\n");
        }
        return new Result(ResultCode.SUCCESS,sb.toString());
    }

    @ResponseBody
    @PostMapping("/getLogError")
    public Result getLogError(@RequestParam("date") String dateStr) throws CommonException {
        FileInputStream is = null;
        try{
            is = new FileInputStream("logs/center/data_sync-error." + dateStr + ".log");

        }catch (FileNotFoundException e){
            logger.error(e.getMessage(),e);
            throw new CommonException(ResultCode.ERROR_NOT_FOUND);
        }
        List<String> list = getText(is,"error");
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str).append("\n");
        }
        //System.out.println(sb.toString());
        return new Result(ResultCode.SUCCESS,sb.toString());

    }


    public List<String> getText(InputStream is,String role){
        List<String> list = new LinkedList<>();
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);
        String line;
        try{
            while ((line = br.readLine()) != null) {
                if(!isContainChinese(line) && role.equals("info")){
                    continue;
                }
                if (line.equals(""))
                    continue;
                else
                    list.add(line);
            }
        }catch (IOException e){
            logger.error(e.getMessage());
        }
        return list;
    }

    public  boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

}
