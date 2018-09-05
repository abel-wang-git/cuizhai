package com.cch.cz.common;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/16.
 */
public class ExcelTool {



    public static String getCellValue(Cell cell){
        switch (cell.getCellTypeEnum()){
            case NUMERIC:
                if(HSSFDateUtil.isCellDateFormatted(cell)){
                    return UtilFun.DateToString(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()),UtilFun.YYYYMMDD).trim();
                }else {
                    cell.setCellType(CellType.STRING);
                    return cell.getStringCellValue().trim();
                }
            default:
                cell.setCellType(CellType.STRING);
                return cell.getStringCellValue().trim();
        }
    }


    public static void wirte() throws IOException, InvalidFormatException {

        File outputFile = new File("C:\\Users\\zyyy\\AppData\\Roaming\\baidu\\BaiduNetdisk\\1.xlsx");
        FileOutputStream fOut = new FileOutputStream(outputFile);
        Workbook w = new XSSFWorkbook();
        Sheet sheet = w.createSheet("sheetone");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("姓名");

        w.write(fOut);

    }

    public static void main(String[] args) {
        try {
            wirte();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }


}
