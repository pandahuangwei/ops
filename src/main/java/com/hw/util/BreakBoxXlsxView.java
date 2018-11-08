/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created : Panda.HuangWei
 * Date : 2016/9/5 14:55
 */
public class BreakBoxXlsxView extends AbstractExcelViewXlsx{
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Date date = new Date();
        String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
        Sheet sheet;
        Cell cell;
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename="+model.get("titleName")+filename+".xlsx");
        sheet = workbook.createSheet("sheet1");

        List<String> titles = (List<String>) model.get("titles");
        int len = titles.size();

        //Style for header cell
        CellStyle style = workbook.createCellStyle(); //标题样式
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
        //style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
       // style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);

        CellStyle headerStyle = workbook.createCellStyle();
       headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
       // headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
       // headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font headerFont = workbook.createFont();//标题字体
       // headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short)11);
        headerStyle.setFont(headerFont);
        short width = 20,height=25*20;
        sheet.setDefaultColumnWidth(width);

        for(int i=0; i<len; i++){ //设置标题
            String title = titles.get(i);
            cell = getCell(sheet, 0, i);
            cell.setCellStyle(headerStyle);
            setText(cell,title);
        }
        sheet.getRow(0).setHeight(height);

        CellStyle contentStyle = workbook.createCellStyle(); //内容样式
       contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //  contentStyle.setAlignment(HorizontalAlignment.CENTER);

        List<PageData> varList = (List<PageData>) model.get("varList");
        int varCount = varList.size();
        for(int i=0; i<varCount; i++){
            PageData vpd = varList.get(i);
            for(int j=0;j<len;j++){
                String varstr = vpd.getString("var"+(j+1)) != null ? vpd.getString("var"+(j+1)) : "";
                cell = getCell(sheet, i+1, j);
                cell.setCellStyle(contentStyle);
                setText(cell,varstr);
            }
        }
    }
}
