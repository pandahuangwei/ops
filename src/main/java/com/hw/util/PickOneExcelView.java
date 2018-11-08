/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 拣货单的导出
 * Created : Panda.HuangWei
 * Date : 2016/9/5 14:55
 */
public class PickOneExcelView  extends AbstractExcelViewXlsx {
//public class PickOneExcelView extends AbstractExcelView {
    private static String siRef = "SI Ref:";
    private static String sendTm = "預計發貨時間:";
    private static String stockNo = "WMS出库单号：";
    private static String[] titles = new String[]{"Location", "Carton ID (外箱）", "Carton ID （内箱）", "P/N#", "DC", "LOT", "BIN", "COO", "Qty", "SQTY", "多少内箱" };

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        Date date = new Date();
        String filename = Tools.date2Str(date, "yyMMddHHmm");
        Sheet sheet;
        Cell cell;
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" +(String)model.get("stockNo")+"-Pick"+Const.SPLIT_LINE+filename + ".xlsx");
        sheet = workbook.createSheet("sheet1");

        CellStyle headerStyle = workbook.createCellStyle(); //标题样式
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        Font headerFont = workbook.createFont();    //标题字体
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setFontHeightInPoints((short) 11);
        headerStyle.setFont(headerFont);
        short width = 20, height = 25 * 20;
        sheet.setDefaultColumnWidth(width);
        //第二行，第一列 siRef,第二列value
        cell = getCell(sheet, 1, 0);
        cell.setCellStyle(headerStyle);
        setText(cell, siRef);

        cell = getCell(sheet, 1, 1);
        cell.setCellStyle(headerStyle);
        setText(cell, (String)model.get("siRef"));

        //第3行，第1列 sendTm,第2列value
        cell = getCell(sheet, 2, 0);
        cell.setCellStyle(headerStyle);
        setText(cell, sendTm);

        cell = getCell(sheet, 2, 1);
        cell.setCellStyle(headerStyle);
        setText(cell, (String)model.get("sendTm"));

        //第4行，第1列 sendTm,第2列value
        cell = getCell(sheet, 3, 0);
        cell.setCellStyle(headerStyle);
        setText(cell, stockNo);

        cell = getCell(sheet, 3, 1);
        cell.setCellStyle(headerStyle);
        setText(cell, (String)model.get("stockNo"));

        headerStyle = workbook.createCellStyle(); //标题样式
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        headerFont = workbook.createFont();    //标题字体
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setFontHeightInPoints((short) 11);

        headerStyle.setFont(headerFont);

        int len = titles.length;
        for (int i = 0; i < len; i++) { //设置标题
            String title = titles[i];
            cell = getCell(sheet, 4, i);
            cell.setCellStyle(headerStyle);
            setText(cell, title);
        }
        sheet.getRow(4).setHeight(height);

       CellStyle contentStyle = workbook.createCellStyle(); //内容样式
        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        CellStyle numStyle = workbook.createCellStyle(); //内容样式
        numStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        numStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        numStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        numStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

        List<PageData> varList = (List<PageData>) model.get("varList");
        int varCount = varList.size();
        int lastCnt = varCount - 1;
        for (int i = 0; i < varCount; i++) {
            PageData vpd = varList.get(i);


            for (int j = 0; j < len; j++) {
                String varstr = vpd.getString("var" + (j)) != null ? vpd.getString("var" + (j)) : "";
                cell = getCell(sheet, i + 5, j);

                if(j ==8 || j==9|| j==10 || i==lastCnt) {
                    numStyle.setFont(headerFont);
                    cell.setCellStyle(numStyle);
                } else {
                    contentStyle.setFont(headerFont);
                    cell.setCellStyle(contentStyle);
                }
                setText(cell, varstr);
            }
        }

    }

   /* @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        Date date = new Date();
        String filename = Tools.date2Str(date, "yyMMddHHmm");
        HSSFSheet sheet;
        HSSFCell cell;
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" +(String)model.get("stockNo")+"-Pick"+Const.SPLIT_LINE+filename + ".xls");
        sheet = workbook.createSheet("sheet1");

        HSSFCellStyle headerStyle = workbook.createCellStyle(); //标题样式
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont headerFont = workbook.createFont();    //标题字体
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setFontHeightInPoints((short) 11);
        headerStyle.setFont(headerFont);
        short width = 20, height = 25 * 20;
        sheet.setDefaultColumnWidth(width);
         //第二行，第一列 siRef,第二列value
        cell = getCell(sheet, 1, 0);
        cell.setCellStyle(headerStyle);
        setText(cell, siRef);

        cell = getCell(sheet, 1, 1);
        cell.setCellStyle(headerStyle);
        setText(cell, (String)model.get("siRef"));

        //第3行，第1列 sendTm,第2列value
        cell = getCell(sheet, 2, 0);
        cell.setCellStyle(headerStyle);
        setText(cell, sendTm);

        cell = getCell(sheet, 2, 1);
        cell.setCellStyle(headerStyle);
        setText(cell, (String)model.get("sendTm"));

        //第4行，第1列 sendTm,第2列value
        cell = getCell(sheet, 3, 0);
        cell.setCellStyle(headerStyle);
        setText(cell, stockNo);

        cell = getCell(sheet, 3, 1);
        cell.setCellStyle(headerStyle);
        setText(cell, (String)model.get("stockNo"));

        headerStyle = workbook.createCellStyle(); //标题样式
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        headerFont = workbook.createFont();    //标题字体
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setFontHeightInPoints((short) 11);

        headerStyle.setFont(headerFont);

        int len = titles.length;
        for (int i = 0; i < len; i++) { //设置标题
            String title = titles[i];
            cell = getCell(sheet, 4, i);
            cell.setCellStyle(headerStyle);
            setText(cell, title);
        }
        sheet.getRow(4).setHeight(height);

        HSSFCellStyle contentStyle = workbook.createCellStyle(); //内容样式
        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        HSSFCellStyle numStyle = workbook.createCellStyle(); //内容样式
        numStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        numStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        numStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        numStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

        List<PageData> varList = (List<PageData>) model.get("varList");
        int varCount = varList.size();
        int lastCnt = varCount - 1;
        for (int i = 0; i < varCount; i++) {
            PageData vpd = varList.get(i);


            for (int j = 0; j < len; j++) {
                String varstr = vpd.getString("var" + (j)) != null ? vpd.getString("var" + (j)) : "";
                cell = getCell(sheet, i + 5, j);

                if(j ==8 || j==9|| j==10 || i==lastCnt) {
                    numStyle.setFont(headerFont);
                    cell.setCellStyle(numStyle);
                } else {
                    contentStyle.setFont(headerFont);
                    cell.setCellStyle(contentStyle);
                }
                setText(cell, varstr);
            }
        }

    }*/
}
