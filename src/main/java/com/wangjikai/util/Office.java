package com.wangjikai.util;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.IOException;

/**
 * Created by 22717 on 2017/12/28.
 * 操作 Office
 * Excel的两种格式：
 * xsl=HSSFWorkbook  Horrible Spreadsheet Format
 * xslx=XSSFWorkbook
 * Word的两种格式：
 * doc=HWPFDocument
 * docx=XWPFDocument
 */
public class Office {
    public static void main(String[] args) throws IOException {
        XWPFDocument xwpfDocument = new XWPFDocument();
        XWPFParagraph paragraph = xwpfDocument.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setText("加粗的net");
        run = paragraph.createRun();
        run.setColor("FF0000");
        run.setText("FuckIdiot Microsoft");
        //====================================================
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet1 = workbook.createSheet("济南市高新区");
//        Sheet sheet2 = workbook.createSheet("济南市历下区");
//        Row row1 = sheet1.createRow(0);
//        Cell cell10 = row1.createCell(0);
//        Cell cell11 = row1.createCell(1);
//        cell10.setCellValue("中电长城网际");
//        cell11.setCellValue(2000);
//
//
//        Row row2 = sheet2.createRow(0);
//        Cell cell20 = row2.createCell(0);
//        Cell cell21 = row2.createCell(1);
//        cell20.setCellValue("支付宝");
//        cell21.setCellValue(2000);

//        FileOutputStream fileOutputStream = new FileOutputStream("E:\\英雄时刻\\人口普查.docx");
//        xwpfDocument.write(fileOutputStream);
//        fileOutputStream.close();
    }
}
