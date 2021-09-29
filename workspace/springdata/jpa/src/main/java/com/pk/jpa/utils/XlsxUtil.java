package com.pk.jpa.utils;

import com.pk.jpa.model.Constants;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * excel工具类
 */
public class XlsxUtil {

    /**
     * 生成xlsx文件
     *
     * @param sheetName   sheetName
     * @param columnWidth 单元格宽度(几个字符)
     * @param contentList 每一行中每个单元格的内容
     * @param outFile     输出的文件
     */
    public static void writeToXlsx(String sheetName, List<Integer> columnWidth,
                                   List<List<String>> contentList, File outFile) throws IOException {
        File path = outFile.getParentFile();
        if (!path.exists()) {
            path.mkdirs();
        }
        if (outFile.exists()) {
            outFile.delete();
        }
        FileOutputStream out = new FileOutputStream(outFile);
        // 创建excel,指定内存中保存的行数，防止溢出
        SXSSFWorkbook workbook = new SXSSFWorkbook(Constants.ONE_THOUSAND);
        // 创建sheet
        SXSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置columnWidth
        for (int i = 0; i < columnWidth.size(); i++) {
            sheet.setColumnWidth(i, columnWidth.get(i) * Constants.NOTE_MAX_LENGTH);
        }
        for (int i = 0; i < contentList.size(); i++) {
            // 创建一行
            SXSSFRow row = sheet.createRow(i);
            List<String> text = contentList.get(i);
            for (int j = 0; j < text.size(); j++) {
                String s = text.get(j);
                // 创建单元格
                row.createCell(j).setCellValue(s);
            }
        }
        workbook.write(out);
        out.close();
    }

    /**
     * 生成xlsx文件，合并单元格
     *
     * @param cellRangeList 0-0-1-2 前两个表示行，后两个表示列。表示将第一行的第二列到第三列合并
     */
    public static void writeToXlsx(List<List<String>> contentList, List<String> cellRangeList,
                                   File outFile, List<Integer> columuFont) throws IOException {
        File path = outFile.getParentFile();
        if (!path.exists()) {
            path.mkdirs();
        }
        if (outFile.exists()) {
            outFile.delete();
        }
        FileOutputStream out = new FileOutputStream(outFile);
        // 创建excel,指定内存中保存的行数，防止溢出
        SXSSFWorkbook workbook = new SXSSFWorkbook(Constants.ONE_THOUSAND);
        // 创建sheet
        SXSSFSheet sheet = workbook.createSheet();
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("@"));
        // 创建标题样式
        Font font = workbook.createFont();
        font.setFontName("黑体");
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFont(font);
        // 子标题样式
        Font subTitleFont = workbook.createFont();
        subTitleFont.setFontName("宋体");
        subTitleFont.setFontHeightInPoints((short) 13);
        CellStyle subTitleStyle = workbook.createCellStyle();
        subTitleStyle.setAlignment(HorizontalAlignment.CENTER);
        subTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        subTitleStyle.setFont(subTitleFont);

        for (int i = 0; i < contentList.size(); i++) {
            // 创建一行
            SXSSFRow row = sheet.createRow(i);
            List<String> text = contentList.get(i);
            for (int j = 0; j < text.size(); j++) {
                String s = text.get(j);
                // 创建单元格
                SXSSFCell cell = row.createCell(j, CellType.STRING);

                cell.setCellValue(s);
                // 标题加粗居中
                if (i == 0) {
                    cell.setCellStyle(titleStyle);
                } else if (i == 1) {
                    cell.setCellStyle(subTitleStyle);
                } else {
                    cell.setCellStyle(style);
                }
            }
        }
        for (String s : cellRangeList) {
            String[] rangeStr = s.split(Constants.DASH);
            int[] range = new int[4];
            for (int i = 0; i < rangeStr.length; i++) {
                range[i] = Integer.parseInt(rangeStr[i]);
            }
            System.out.println(Arrays.toString(range));
            CellRangeAddress cellAddresses = new CellRangeAddress(range[0], range[1], range[2], range[3]);
            sheet.addMergedRegion(cellAddresses);
        }
        // 设置列宽
        for (int i = 0; i < columuFont.size(); i++) {
            sheet.setColumnWidth(i, columuFont.get(i));
        }
        workbook.write(out);
        out.close();
    }

}
