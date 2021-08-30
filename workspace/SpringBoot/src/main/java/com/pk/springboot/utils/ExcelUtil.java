package com.pk.springboot.utils;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class ExcelUtil {


    public static void readExcel(File file) throws Exception {
        if (!file.exists()) {
            throw new Exception("文件不存在：" + file.getName());
        }
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row = sheet.getRow(6);
//        System.out.println(row.getCell(1).getStringCellValue());
//        System.out.println(sheet.getRow(7).getCell(1).getStringCellValue());
        System.out.println(sheet.getRow(2).getCell(7).getStringCellValue());


    }

    public static void main(String[] args) {
        File file = new File("/Users/chenguanlin/Downloads/20210831_variant-query.xlsx");
        try {
            readExcel(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
