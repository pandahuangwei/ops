/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
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

public class PickManyExcelView extends AbstractExcelViewXlsx {
//public class PickManyExcelView extends AbstractExcelView {
    private static String title = "捡货单-合拼多个出货单总汇 (PICK LIST GROUPED SUMMARY)";
    private static String groupBy = "Group By:";
    private static String SiRef = "SiRef:";
    private static String[] titles = new String[]{"库位", "Carton ID", "WMS 出库单号","P/N#",  "Qty (EA)", "SQTY", "多少内箱" };

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        Date date = new Date();
        String filename = Tools.date2Str(date, "yyMMddHHmmss");
        Sheet sheet;
        Cell cell;
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" +"ManyPick"+Const.SPLIT_LINE+filename + ".xlsx");
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

        /*
         * 设定合并单元格区域范围
         *  firstRow  0-based
         *  lastRow   0-based
         *  firstCol  0-based
         *  lastCol   0-based
         */
        CellRangeAddress cra=new CellRangeAddress(0, 0, 0, 6);
        /*合并标题的单元格设置标题信息及样式 */
        sheet.addMergedRegion(cra);
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(headerStyle);

        cra=new CellRangeAddress(1, 1, 1, 6);
        sheet.addMergedRegion(cra);
        titleRow = sheet.createRow(1);
        titleCell = titleRow.createCell(1);
        titleCell.setCellValue((String)model.get("stockNo"));



        cra=new CellRangeAddress(2, 2, 1, 6);
        sheet.addMergedRegion(cra);
        titleRow = sheet.createRow(2);
        titleCell = titleRow.createCell(1);
        titleCell.setCellValue((String)model.get("SiRef"));

        cell = getCell(sheet, 1, 0);
        cell.setCellStyle(headerStyle);
        setText(cell, groupBy);

        cell = getCell(sheet, 2, 0);
        cell.setCellStyle(headerStyle);
        setText(cell, SiRef);


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
            cell = getCell(sheet, 3, i);
            cell.setCellStyle(headerStyle);
            setText(cell, title);
        }
        //sheet.getRow(4).setHeight(height);

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
        //库位	Carton ID	WMS 出库单号	P/N#	Qty (EA)	SQTY	多少内箱
        List<PageData> varList = (List<PageData>) model.get("varList");
        int varCount = varList.size();
        int lastCnt = varCount - 1;
        for (int i = 0; i < varCount; i++) {
            PageData vpd = varList.get(i);


            for (int j = 0; j < len; j++) {
                String varstr = vpd.getString("var" + (j)) != null ? vpd.getString("var" + (j)) : "";
                cell = getCell(sheet, i + 4, j);

                if(j ==4 || j==5 || j==6 || i==lastCnt) {
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

  /*  @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        Date date = new Date();
        String filename = Tools.date2Str(date, "yyMMddHHmmss");
        HSSFSheet sheet;
        HSSFCell cell;
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" +"ManyPick"+Const.SPLIT_LINE+filename + ".xls");
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

        *//*
         * 设定合并单元格区域范围
         *  firstRow  0-based
         *  lastRow   0-based
         *  firstCol  0-based
         *  lastCol   0-based
         *//*
        CellRangeAddress cra=new CellRangeAddress(0, 0, 0, 6);
        *//*合并标题的单元格设置标题信息及样式 *//*
        sheet.addMergedRegion(cra);
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(headerStyle);

        cra=new CellRangeAddress(1, 1, 1, 6);
        sheet.addMergedRegion(cra);
        titleRow = sheet.createRow(1);
        titleCell = titleRow.createCell(1);
        titleCell.setCellValue((String)model.get("stockNo"));



        cra=new CellRangeAddress(2, 2, 1, 6);
        sheet.addMergedRegion(cra);
        titleRow = sheet.createRow(2);
        titleCell = titleRow.createCell(1);
        titleCell.setCellValue((String)model.get("SiRef"));

        cell = getCell(sheet, 1, 0);
        cell.setCellStyle(headerStyle);
        setText(cell, groupBy);

        cell = getCell(sheet, 2, 0);
        cell.setCellStyle(headerStyle);
        setText(cell, SiRef);


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
            cell = getCell(sheet, 3, i);
            cell.setCellStyle(headerStyle);
            setText(cell, title);
        }
        //sheet.getRow(4).setHeight(height);

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
       //库位	Carton ID	WMS 出库单号	P/N#	Qty (EA)	SQTY	多少内箱
        List<PageData> varList = (List<PageData>) model.get("varList");
        int varCount = varList.size();
        int lastCnt = varCount - 1;
        for (int i = 0; i < varCount; i++) {
            PageData vpd = varList.get(i);


            for (int j = 0; j < len; j++) {
                String varstr = vpd.getString("var" + (j)) != null ? vpd.getString("var" + (j)) : "";
                cell = getCell(sheet, i + 4, j);

                if(j ==4 || j==5 || j==6 || i==lastCnt) {
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
