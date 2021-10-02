package org.augustus;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: linyongjin
 * @create: 2021-06-24 11:26:25
 */
public class AppTest {

    @Test
    public void poi() {
        String[] firstHead = {"考核年度","被考核人员","单位","岗位","专职","第一季度","第一季度","第二季度","第二季度","第三季度","第三季度","第四季度","第四季度","备注"};
        String[] secondHead = {"考核年度","被考核人员","单位","岗位","专职","考核得分","本周期岗位排名","考核得分","本周期岗位排名","考核得分","本周期岗位排名","考核得分","本周期岗位排名","备注"};
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet");
        XSSFRow firstRow = sheet.createRow(0);
        for (int i = 0; i < firstHead.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(firstHead[i]);
        }
        XSSFRow secondRow = sheet.createRow(1);
        for (int i = 0; i < secondHead.length; i++) {
            XSSFCell cell = secondRow.createCell(i);
            cell.setCellValue(secondHead[i]);
        }
        List<Integer[]> list3 = new ArrayList<>(); //要合并的行和列
        Integer[] array1 = {0, 1, 0, 0};
        Integer[] array2 = {0, 1, 1, 1};
        Integer[] array3 = {0, 1, 2, 2};
        Integer[] array4 = {0, 1, 3, 3};
        Integer[] array5 = {0, 1, 4, 4};
        Integer[] array6 = {0, 0, 5, 6};
        Integer[] array7 = {0, 0, 7, 8};
        Integer[] array8 = {0, 0, 9, 10};
        Integer[] array9 = {0, 0, 11, 12};
        Integer[] array10 = {0, 1, 13, 13};
        list3.add(array1);
        list3.add(array2);
        list3.add(array3);
        list3.add(array4);
        list3.add(array5);
        list3.add(array6);
        list3.add(array7);
        list3.add(array8);
        list3.add(array9);
        list3.add(array10);
        for (Integer[] integers : list3) {
            sheet.addMergedRegion(new CellRangeAddress(integers[0], integers[1],integers[2],integers[3]));
        }
    }

    @Test
    public void easyExcel() throws IOException {
        File file = new File("/Users/linyongjin/IdeaProjects/network-application/easy.xlsx");
        WriteCellStyle headerStyle = new WriteCellStyle();
        WriteFont headerFont = new WriteFont();
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setBold(false);
        headerStyle.setWriteFont(headerFont);
        headerStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.WHITE.index);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBottomBorderColor(IndexedColors.BLACK1.index);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setTopBorderColor(IndexedColors.BLACK1.index);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setRightBorderColor(IndexedColors.BLACK1.index);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setLeftBorderColor(IndexedColors.BLACK1.index);
        WriteCellStyle contentStyle = new WriteCellStyle();
        contentStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        WriteFont contentFont = new WriteFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBold(false);
        contentStyle.setWriteFont(contentFont);
        contentStyle.setBorderBottom(BorderStyle.THIN);
        contentStyle.setBottomBorderColor(IndexedColors.BLACK1.index);
        contentStyle.setBorderTop(BorderStyle.THIN);
        contentStyle.setTopBorderColor(IndexedColors.BLACK1.index);
        contentStyle.setBorderRight(BorderStyle.THIN);
        contentStyle.setRightBorderColor(IndexedColors.BLACK1.index);
        contentStyle.setBorderLeft(BorderStyle.THIN);
        contentStyle.setLeftBorderColor(IndexedColors.BLACK1.index);
        HorizontalCellStyleStrategy styleStrategy = new HorizontalCellStyleStrategy(headerStyle, contentStyle);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(outputStream).head(header()).registerWriteHandler(styleStrategy).autoCloseStream(false).build();
        WriteSheet sheet = EasyExcel.writerSheet("sheet1").build();
        for (int i = 0; i < 4; i++) {
            excelWriter.write(data(), sheet);
        }
        excelWriter.finish();
        byte[] bytes = outputStream.toByteArray();
    }

    public List<List<String>> header() {
        List<List<String>> header = new ArrayList<>();
        List<String> header0 = new ArrayList<>();
        header0.add("person");
        header0.add("name");
        header0.add("name1");
        List<String> header1 = new ArrayList<>();
        header1.add("person");
        header1.add("name");
        header1.add("name2");
        List<String> header2 = new ArrayList<>();
        header2.add("person");
        header2.add("name");
        header2.add("name3");
        List<String> header3 = new ArrayList<>();
        header3.add("person");
        header3.add("name");
        header3.add("name4");
        List<String> header4 = new ArrayList<>();
        header4.add("person");
        header4.add("age");
        header4.add("age1");
        List<String> header5 = new ArrayList<>();
        header5.add("person");
        header5.add("age");
        header5.add("age2");
        List<String> header6 = new ArrayList<>();
        header6.add("person");
        header6.add("age");
        header6.add("age3");
        List<String> header7 = new ArrayList<>();
        header7.add("person");
        header7.add("age");
        header7.add("age4");
        header.add(header0);
        header.add(header1);
        header.add(header2);
        header.add(header3);
        header.add(header4);
        header.add(header5);
        header.add(header6);
        header.add(header7);
        return header;
    }

    public List<List<Object>> data() {
        List<List<Object>> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<Object> rowData = new ArrayList<>();
            rowData.add(RandomUtils.nextInt(100, 999));
            rowData.add(RandomUtils.nextInt(100, 999));
            rowData.add(RandomUtils.nextInt(100, 999));
            rowData.add(RandomUtils.nextInt(100, 999));
            rowData.add(RandomUtils.nextInt(100, 999));
            rowData.add(RandomUtils.nextInt(100, 999));
            rowData.add(RandomUtils.nextInt(100, 999));
            rowData.add(RandomUtils.nextInt(100, 999));
            data.add(rowData);
        }
        return data;
    }
}
