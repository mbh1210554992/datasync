package com.ntu.node.controller;

import com.ntu.common.model.Result;
import com.ntu.common.model.po.MonitorData;
import com.ntu.node.mapper.MonitorDataMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class DataController {

    @Autowired
    private MonitorDataMapper monitorDataMapper;
    /**
     * 通过excel批量上传员工
     *      文件上传
     */
    //@ApiOperation(value = "模拟采集数据接口")
    @PostMapping(value = "/user/import")
    @ResponseBody
    public Result importUser(@RequestParam(name = "file") MultipartFile file) throws IOException {
        //1.根据Excel文件创建工作簿
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        //2.获取Sheet
        Sheet sheet = workbook.getSheetAt(0);

        List<MonitorData> list = new ArrayList<>();
        MonitorData monitorData = new MonitorData();
        //3.获取每一行
        int i = 0;
        for (Row row : sheet) {
            if(i == 0){
                i++;
                continue;
            }
            Object[] values = new Object[row.getLastCellNum()];
            for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                Cell cell = row.getCell(cellNum);
                values[cellNum] = getCellValue(cell);
            }
            monitorData.setSpotId(values[0].toString());
            monitorData.setAreaId(values[1].toString());
            monitorData.setNodeId(values[2].toString());
            monitorData.setDataTime((Date)values[3]);
            monitorData.setConductivity(convertToFloat((Double) values[4]));
            monitorData.setTemperature(convertToFloat((Double)values[5]));
            monitorData.setPh(convertToFloat((Double)values[6]));
            monitorData.setSalinity(convertToFloat((Double)values[7]));
            monitorData.setDissolvedOxygen(convertToFloat((Double)values[8]));
            monitorData.setTurbidity(convertToFloat((Double)values[9]));
            monitorDataMapper.insert(monitorData);
            //list.add(monitorData);
        }


        return null;
    }

    public static Object getCellValue(Cell cell){
        //1.获取单元格的数据类型
        CellType type = cell.getCellType();
        Object value = null;
        //2.根据单元格类型获取数据
        switch(type){
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                //日期格式
                if(DateUtil.isCellDateFormatted(cell)){
                    value = cell.getDateCellValue();
                }else{//数字
                    value = cell.getNumericCellValue();
                }
                break;
        }
        return value;
    }

    public  Float convertToFloat(Double doubleValue) {
        return doubleValue == null ? null : doubleValue.floatValue();
    }
}
