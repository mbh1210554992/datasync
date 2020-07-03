package com.ntu.client2.controller;

import com.ntu.client2.mapper.BoxSensorMapper;
import com.ntu.client2.mapper.MonitorDataMapper;
import com.ntu.common.model.Result;
import com.ntu.common.model.ResultCode;
import com.ntu.common.model.po.BoxSensor;
import com.ntu.common.model.po.MonitorData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api("长江站点模拟数据采集")
public class DataController {

    @Autowired
    private MonitorDataMapper monitorDataMapper;
    @Autowired
    private BoxSensorMapper boxSensorMapper;
    /**
     * 通过excel批量上传数据
     *      文件上传
     */
    @ApiOperation(value = "模拟monitor_data表采集数据接口")
    @PostMapping(value = "/addMonitorData")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name="file",value="模拟采集的数据文件",dataType = "file", paramType = "form")
    })
    public Result addMonitorData(@RequestParam(name = "file") MultipartFile file) throws IOException {
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


        return new Result(ResultCode.SUCCESS,"数据新增成功");
    }

    @ApiOperation(value = "模拟box_sensor表采集数据接口")
    @PostMapping(value = "/addBoxSensor")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name="file",value="模拟采集的数据文件",dataType = "file", paramType = "form")
    })
    public Result addBoxSensor(@RequestParam(name = "file") MultipartFile file) throws IOException {
        //1.根据Excel文件创建工作簿
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        //2.获取Sheet
        Sheet sheet = workbook.getSheetAt(0);

        List<BoxSensor> list = new ArrayList<>();
        BoxSensor boxSensor = new BoxSensor();
        //3.获取每一行
        boolean flag = true;
        for (Row row : sheet) {
            //跳过第一行
            if(flag){
                flag = false;
                continue;
            }
            Object[] values = new Object[row.getLastCellNum()];
            for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                Cell cell = row.getCell(cellNum);
                values[cellNum] = getCellValue(cell);
            }
            boxSensor.setAreaId(values[0].toString());
            boxSensor.setSpotId(values[1].toString());
            boxSensor.setNodeId(values[2].toString());
            boxSensor.setDataTime((Date)values[3]);
            boxSensor.setConductivity(convertToFloat((Double) values[4]));
            boxSensor.setTemperature(convertToFloat((Double) values[5]));
            boxSensor.setPressure(convertToFloat((Double) values[6]));
            boxSensor.setSalinity(convertToFloat((Double) values[7]));
            boxSensor.setDissolvedoxygen(convertToFloat((Double) values[8]));
            boxSensor.setSpad(convertToFloat((Double) values[9]));
            boxSensor.setTurbidity(convertToFloat((Double) values[10]));
            boxSensor.setAndan(convertToFloat((Double) values[11]));
            boxSensor.setYaxiaodan(convertToFloat((Double) values[12]));
            boxSensor.setLinsuanyan(convertToFloat((Double) values[13]));
            boxSensor.setOrp(convertToFloat((Double) values[14]));
            boxSensorMapper.insert(boxSensor);
            //list.add(monitorData);
        }


        return new Result(ResultCode.SUCCESS,"数据新增成功");
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
