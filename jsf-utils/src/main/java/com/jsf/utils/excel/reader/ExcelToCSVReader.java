package com.jsf.utils.excel.reader;

import com.jsf.utils.exception.SysException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * SAX模式读取Excel (xls to CSV)
 * <p>适合万级，十万级以上的数据量；需要2007版本以上</p>
 *
 * @author rick
 * @version 1.0
 */
public class ExcelToCSVReader {

    private List<String[]> datas;
    public List<String> headers;
    public int cols;
    public boolean includeHeader;

    public ExcelToCSVReader(boolean includeHeader) {
        this.datas = new ArrayList<>();
        this.includeHeader = includeHeader;
        if (includeHeader) {
            headers = new ArrayList<>();
        }
    }

    /**
     * 处理方法
     *
     * @param is 输入流
     * @return
     */
    public List<String[]> process(InputStream is) {
        InputStream stream = null;
        OPCPackage opcPackage = null;
        try {
            opcPackage = OPCPackage.open(is);
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opcPackage);
            XSSFReader xssfReader = new XSSFReader(opcPackage);
            StylesTable styles = xssfReader.getStylesTable(); //getSharedStringsTable
            XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();

            // 取第一个表
            stream = iter.next();
            //String sheetName = iter.getSheetName();
            processSheet(styles, strings, new SheetToCSV(), stream);
        } catch (Exception e) {
            throw new RuntimeException("读取出错", e);
        } finally {
            try {
                stream.close();
                opcPackage.close();
            } catch (IOException e) {
                throw new SysException(e.getMessage(), e);
            }
        }
        return datas;
    }

    private void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings,
                              XSSFSheetXMLHandler.SheetContentsHandler sheetHandler, InputStream sheetInputStream) {
        DataFormatter formatter = new DataFormatter();
        InputSource sheetSource = new InputSource(sheetInputStream);
        try {
            XMLReader sheetParser = SAXHelper.newXMLReader();
            ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings, sheetHandler, formatter, false);
            sheetParser.setContentHandler(handler);
            sheetParser.parse(sheetSource);
        } catch (Exception e) {
            throw new RuntimeException("转换CSV出错", e);
        }
    }

    private class SheetToCSV implements XSSFSheetXMLHandler.SheetContentsHandler {
        private boolean firstCellOfRow = true;
        private int currentRow = 0;
        private int currentCol = 0;
        private String[] data;

        // 行开始
        @Override
        public void startRow(int rowNum) {
            currentRow = rowNum;
            currentCol = 0;
            if (!firstCellOfRow) {
                // 初始化数组
                data = new String[cols];
            }
        }

        // 行结束
        @Override
        public void endRow(int rowNum) {
            if (firstCellOfRow) {
                cols = currentCol + 1; // 初始化列数
            } else {
                datas.add(data); //追加数据
            }
            firstCellOfRow = false;
        }

        // 行 - 字段处理
        @Override
        public void cell(String cellReference, String formattedValue, XSSFComment comment) {
            int thisCol = (new CellReference(cellReference)).getCol(); // 防止空值（可去除空行）
            currentCol = thisCol;
            if (!firstCellOfRow) { // 非首行
                if (currentCol < cols) { // 遍历列
                    data[currentCol] = formattedValue;
                    // 注意时间格式问题
                }
            } else {
                if (includeHeader) {
                    headers.add(formattedValue);
                }
            }
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
        }
    }

}
