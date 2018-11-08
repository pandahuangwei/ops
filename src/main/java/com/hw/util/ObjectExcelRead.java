/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created : Panda.HuangWei
 * Date : 2016/9/5 14:54
 */
public class ObjectExcelRead {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ObjectExcelRead.class);

    public static List<Object> readExcels(String filepath, String filename, int startrow, int startcol, int sheetnum) {
        String extName = ""; // 扩展名格式：
        if (filename.lastIndexOf(".") >= 0) {
            extName = filename.substring(filename.lastIndexOf("."));
        }
        List<Object> rs = new ArrayList<>();
        if (".xls".equals(extName.toLowerCase())) {
            rs = readExcel(filepath, filename, startrow, startcol, sheetnum);
        } else if (".xlsx".equals(extName.toLowerCase())) {
            rs = readExcelByXlsx(filepath, filename, startrow, startcol, sheetnum);
        }
        return rs;
    }
    /**
     * @param filepath //文件路径
     * @param filename //文件名
     * @param startrow //开始行号
     * @param startcol //开始列号
     * @param sheetnum //sheet
     * @return list
     */
    public static List<Object> readExcel(String filepath, String filename, int startrow, int startcol, int sheetnum) {
        List<Object> varList = new ArrayList<Object>();

        try {
            File target = new File(filepath, filename);
            FileInputStream fi = new FileInputStream(target);
            HSSFWorkbook wb = new HSSFWorkbook(fi);
            HSSFSheet sheet = wb.getSheetAt(sheetnum); 					//sheet 从0开始
            int rowNum = sheet.getLastRowNum() + 1; 					//取得最后一行的行号

            for (int i = startrow; i < rowNum; i++) {					//行循环开始

                PageData varpd = new PageData();
                HSSFRow row = sheet.getRow(i); 							//行
                int cellNum = row.getLastCellNum(); 					//每行的最后一个单元格位置

                for (int j = startcol; j < cellNum; j++) {				//列循环开始

                    HSSFCell cell = row.getCell(Short.parseShort(j + ""));
                    String cellValue = null;
                    if (null != cell) {
                        switch (cell.getCellType()) { 					// 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
                            case 0:
                                cellValue = String.valueOf((int) cell.getNumericCellValue());
                                break;
                            case 1:
                                cellValue = cell.getStringCellValue();
                                break;
                            case 2:

                                cellValue = cell.getNumericCellValue() + "";
                                // cellValue = String.valueOf(cell.getDateCellValue());
                                break;
                            case 3:
                                cellValue = "";
                                break;
                            case 4:
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case 5:
                                cellValue = String.valueOf(cell.getErrorCellValue());
                                break;
                        }
                    } else {
                        cellValue = "";
                    }

                    varpd.put("var"+j, cellValue);

                }
                varList.add(varpd);
            }

        } catch (Exception e) {
            logger.error("",e);
        }

        return filter(varList);
    }

    public static List<Object> readOutStockExcel(String filepath, String filename, int startrow, int startcol, int sheetnum) {
        List<Object> varList = new ArrayList<Object>();

        try {
            File target = new File(filepath, filename);
            FileInputStream fi = new FileInputStream(target);
            HSSFWorkbook wb = new HSSFWorkbook(fi);
            HSSFSheet sheet = wb.getSheetAt(sheetnum); 					//sheet 从0开始
            int rowNum = sheet.getLastRowNum() + 1; 					//取得最后一行的行号

            for (int i = startrow; i < rowNum; i++) {					//行循环开始

                PageData varpd = new PageData();
                HSSFRow row = sheet.getRow(i); 							//行
                int cellNum = row.getLastCellNum(); 					//每行的最后一个单元格位置

                for (int j = startcol; j < cellNum; j++) {				//列循环开始

                    HSSFCell cell = row.getCell(Short.parseShort(j + ""));
                    String cellValue = null;
                    if (null != cell) {
                        switch (cell.getCellType()) { 					// 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
                            case 0:
                                cellValue = String.valueOf((int) cell.getNumericCellValue());
                                break;
                            case 1:
                                cellValue = cell.getStringCellValue();
                                break;
                            case 2:

                                cellValue = cell.getNumericCellValue() + "";
                                // cellValue = String.valueOf(cell.getDateCellValue());
                                break;
                            case 3:
                                cellValue = "";
                                break;
                            case 4:
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case 5:
                                cellValue = String.valueOf(cell.getErrorCellValue());
                                break;
                            case 6:
                                cellValue =cell.getStringCellValue(); //String.valueOf(cell.getErrorCellValue());
                                break;

                        }
                    } else {
                        cellValue = "";
                    }

                    varpd.put("var"+j, cellValue);

                }
                varList.add(varpd);
            }

        } catch (Exception e) {
            logger.error("",e);
        }

        return filter(varList);
    }


    public static List<Object> readLocatorExcel(String filepath, String filename, int startrow, int startcol, int sheetnum) throws Exception{
        List<Object> varList = new ArrayList<Object>();


            File target = new File(filepath, filename);
            FileInputStream fi = new FileInputStream(target);
            HSSFWorkbook wb = new HSSFWorkbook(fi);
            HSSFSheet sheet = wb.getSheetAt(sheetnum); 					//sheet 从0开始
            int rowNum = sheet.getLastRowNum() + 1; 					//取得最后一行的行号

            for (int i = startrow; i < rowNum; i++) {					//行循环开始

                PageData varpd = new PageData();
                HSSFRow row = sheet.getRow(i); 							//行
                int cellNum = row.getLastCellNum(); 					//每行的最后一个单元格位置

                for (int j = startcol; j < cellNum; j++) {				//列循环开始

                    HSSFCell cell = row.getCell(Short.parseShort(j + ""));
                    String cellValue = null;
                    if (null != cell) {
                        switch (cell.getCellType()) { 					// 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
                            case 0:
                                cellValue = String.valueOf((int) cell.getNumericCellValue());
                                break;
                            case 1:
                                cellValue = cell.getStringCellValue();
                                break;
                            case 2:
                                // cellValue = cell.getNumericCellValue() + "";
                                cell.setCellType(cell.CELL_TYPE_STRING);
                                cellValue = cell.getStringCellValue();
                                break;
                            case 3:
                                cellValue = "";
                                break;
                            case 4:
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case 5:
                                cellValue = String.valueOf(cell.getErrorCellValue());
                                break;
                        }
                    } else {
                        cellValue = "";
                    }

                    varpd.put("var"+j, cellValue);

                }
                varList.add(varpd);
            }


        return filter(varList);
    }


    /**
     * 是操作Excel2007的版本，扩展名是.xlsx
     * @param filepath 文件
     * @param startrow 开始行号
     * @param startcol 开始列号
     * @param sheetnum sheet
     * @return list
     */
    public static List<Object> readExcelByXlsx(String filepath, String filename, int startrow, int startcol, int sheetnum) {
        List<Object> varList = new ArrayList<Object>();

        try {
            File target = new File(filepath, filename);
            FileInputStream fi = new FileInputStream(target);
            XSSFWorkbook wb = new XSSFWorkbook(fi);
            XSSFSheet sheet = wb.getSheetAt(sheetnum);                  //sheet 从0开始
            int rowNum = sheet.getLastRowNum() + 1;                     //取得最后一行的行号

            for (int i = startrow; i < rowNum; i++) {                    //行循环开始
                PageData varpd = new PageData();
                XSSFRow row = sheet.getRow(i);                          //行
                int cellNum = row.getLastCellNum();                     //每行的最后一个单元格位置

                for (int j = startcol; j < cellNum; j++) {               //列循环开始

                    XSSFCell cell = row.getCell(Integer.parseInt(j + ""));
                    String cellValue = null;
                    if (null != cell) {
                        switch (cell.getCellType()) {                   // 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
                            case 0:
                                if(HSSFDateUtil.isCellDateFormatted(cell)){
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    cellValue=sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                                } else {
                                    cell.setCellType(1);
                                    cellValue = cell.getStringCellValue();
                                }
                                break;
                            case 1:
                                cellValue = cell.getStringCellValue();
                                break;
                            case 2:
                                cell.setCellType(cell.CELL_TYPE_STRING);
                                cellValue = cell.getStringCellValue();
                                //cellValue = cell.getNumericCellValue() + "";
                                // cellValue = String.valueOf(cell.getDateCellValue());
                                break;
                            case 3:
                                cellValue = "";
                                break;
                            case 4:
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case 5:
                                cellValue = String.valueOf(cell.getErrorCellValue());
                                break;
                        }
                    } else {
                        cellValue = "";
                    }
                    varpd.put("var"+j, cellValue);
                }
                varList.add(varpd);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return filter(varList);
    }

    private static List<Object> filter(List<Object> srcList) {
        List<Object> list = new ArrayList<>();
        if(srcList == null || srcList.isEmpty()) {
            return list;
        }
        List<PageData> listPd = (List)srcList;
        for(PageData pd :listPd) {
            String var0 = pd.getString("var0");
            if(StringUtil.isEmpty(var0)) {
                continue;
            }
            list.add(pd);
        }
        return list;
    }
}
