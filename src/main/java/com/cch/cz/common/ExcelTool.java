package com.cch.cz.common;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/16.
 */
public class ExcelTool {

    public static void main(String[] args) {
        try {
            wirte();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    public static void read() throws IOException, InvalidFormatException {
        Execl result1 = new Execl();
        StringBuffer result = new StringBuffer();
        String fileToBeRead = "C:\\Users\\Administrator\\Pictures\\BuChongXinXi_2018-03-12_211652.xlsx";

        // 创建对Excel工作簿文件的引用
        Workbook workbook = WorkbookFactory.create(new FileInputStream(fileToBeRead));

        // 创建对工作表的引用。
        Sheet sheet = workbook.getSheetAt(0);
        workbook.getActiveSheetIndex();
        workbook.getAllNames();
        workbook.getNumberOfSheets();
        // 便利所有单元格，读取单元格
        int row_num = sheet.getLastRowNum();
        for (int i = 0; i <=row_num; i++) {
            Row r = sheet.getRow(i);
            int cell_num = r.getLastCellNum();
            for (int j = 0; j < cell_num; j++) {
                //System.out.println(r.getCell((short)j).getCellType());
                if(CellType.NUMERIC == r.getCell(j).getCellTypeEnum()){
                    result.append(r.getCell(j).getNumericCellValue());
                }else{
                    result.append(r.getCell(j).getStringCellValue());
                }
                result.append("\t");
            }
            result.append("\n");
        }
        System.out.println(result);
    }


    public static void wirte() throws IOException, InvalidFormatException {

        File outputFile = new File("C:\\Users\\Administrator\\Pictures\\1.xlsx");
        FileOutputStream fOut = new FileOutputStream(outputFile);
        Workbook w = new XSSFWorkbook();
        Sheet sheet= w.createSheet("sheetone");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("姓名");

        w.write(fOut);

    }

}
