package com.wangjikai.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
        InputStream inputStream = new FileInputStream("E:\\test\\b.xls");
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
        } catch (InvalidFormatException e) {
            System.out.println("无效的格式");
            e.printStackTrace(); //堆栈跟踪信息打印
        }
        excelSheetsNumber(workbook);
//        XWPFDocument xwpfDocument = new XWPFDocument();
//        XWPFParagraph paragraph = xwpfDocument.createParagraph();
//        XWPFRun run = paragraph.createRun();
//        run.setBold(true);
//        run.setText("加粗的net");
//        run = paragraph.createRun();
//        run.setColor("FF0000");
//        run.setText("FuckIdiot Microsoft");
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
    public static void excelSheetsNumber(Workbook workbook){
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) { //获取每个sheet表
            Sheet sheet = workbook.getSheetAt(i);
            System.out.println("sheet:"+sheet.getSheetName());
            for (int j = 0; j <= sheet.getPhysicalNumberOfRows() && sheet.getPhysicalNumberOfRows()!= 0; j++) { //获取最后一行的行标
                Row row = sheet.getRow(j);
                if (row != null && !checkRowIsEmpty(row)){
                    for (int k = 0; k < row.getLastCellNum() + 1; k++) { //获取最后一个不为空的列
                        if (row.getCell(k) != null && "".equals(row.getCell(k).getStringCellValue())){
                            continue;
                        }
                        else if (row.getCell(k) != null && !"".equals(row.getCell(k).getStringCellValue())){
                            System.out.print(row.getCell(k)+"\t\t");
                        }else {
                            System.out.print("\t\t");
                        }
                    }
                    System.out.println();
                }else {
                }
            }
        }
    }

    /**
     * 针对一行Row进行检测，如果任何一个cell不为空，则返回false
     * @param row 需要判断的行
     * @return
     */
    public static boolean checkRowIsEmpty(Row row){
        boolean result = true;
        int total = row.getLastCellNum() + 1;
        for (int i = 0; i < total; i++) {
            Cell cell = row.getCell(i);
            if (cell != null && !"".equals(cell.getStringCellValue())){
                result = false;  //改进、返回不为空的那个cell的索引
            }
        }
        return result;
    }
}
