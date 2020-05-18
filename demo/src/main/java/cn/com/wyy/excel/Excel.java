//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.com.wyy.excel;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.com.wyy.excel.ExcelProcessBean;
import cn.com.wyy.excel.Mapper;
import cn.com.wyy.excel.Reflections;
import cn.com.wyy.excel.annotations.ExcelForClass;
import cn.com.wyy.excel.annotations.ExcelForFeild;
import cn.com.wyy.excel.convert.Convert;
import cn.com.wyy.excel.dic.Dic;
import cn.com.wyy.excel.exception.ExcelForClassAnnotationNotFoundException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class Excel {
    //private static final Logger log = LoggerFactory.getLogger(Excel.class);
    private Map<Class<?>, Mapper> cache = new ConcurrentHashMap();
    private Dic dic;
    private String headerTitle;
    private int headerTitleSize;
    private List<String> headerInfo;
    private static final String LINE = "-";

    public Excel(Dic dic) {
        this.dic = dic;
    }

    public void processAnnotations(Class<?>... types) throws ExcelForClassAnnotationNotFoundException, InstantiationException, IllegalAccessException {
        Class[] var2 = types;
        int var3 = types.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Class<?> clz = var2[var4];
            ExcelForClass excelForClass = (ExcelForClass)clz.getAnnotation(ExcelForClass.class);
            if (null == excelForClass) {
                throw new ExcelForClassAnnotationNotFoundException(clz);
            }

            Mapper mapper = (Mapper)this.cache.get(clz);
            if (mapper == null) {
                mapper = new Mapper();
                String sheetName = excelForClass.sheetName();
                mapper.setSheetName(sheetName);
                Field[] fields = clz.getDeclaredFields();
                Field[] var10 = fields;
                int var11 = fields.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    Field field = var10[var12];
                    ExcelProcessBean bean = new ExcelProcessBean();
                    ExcelForFeild excelForFeild = (ExcelForFeild)field.getAnnotation(ExcelForFeild.class);
                    if (null != excelForFeild) {
                        bean.setFieldName(field.getName());
                        bean.setSort(excelForFeild.sort());
                        bean.setDicType(excelForFeild.dicType());
                        bean.setConvert((Convert)excelForFeild.convert().newInstance());
                        bean.setName(excelForFeild.value());
                        bean.setCollection(excelForFeild.isCollection());
                        mapper.addBean(bean);
                    }
                }
            }

            this.cache.put(clz, mapper);
        }

    }

    public <T> void write(List<T> datas, Class<T> clz, String targetPath, String fileName, boolean head, boolean order) throws IOException, IllegalArgumentException, IllegalAccessException, ExcelForClassAnnotationNotFoundException {
        Mapper mapper = (Mapper)this.cache.get(clz);
        if (null == mapper) {
        }

        Workbook wb = new SXSSFWorkbook();
        Sheet sheet = wb.createSheet(mapper.getSheetName());
        int headNo = 0;
        if (StringUtils.isNotBlank(this.headerTitle)) {
            this.processHeadTitle(sheet, wb);
            ++headNo;
        }

        if (this.getHeaderInfo() != null && this.getHeaderInfo().size() > 0) {
            this.processHeadInfo(sheet, wb, headNo);
            ++headNo;
        }

        if (head) {
            this.processHead(mapper, sheet, order, headNo, wb);
            ++headNo;
        }

        this.process(datas, mapper, sheet, headNo, order);
        FileOutputStream stream = null;

        try {
            stream = this.getOutPutStream(targetPath, fileName);
            wb.write(stream);
        } catch (Exception var16) {
            //log.error("流操作出现异常");
        } finally {
            if (stream != null) {
                stream.close();
            }

        }

    }

    public <T> void write(List<T> datas, Class<T> clz, String targetPath, String fileName, boolean head, boolean order, String sheetName) throws IOException, IllegalArgumentException, IllegalAccessException, ExcelForClassAnnotationNotFoundException {
        Mapper mapper = (Mapper)this.cache.get(clz);
        if (null == mapper) {
        }

        Workbook wb = new SXSSFWorkbook();
        if (StringUtils.isBlank(sheetName)) {
            sheetName = mapper.getSheetName();
        }

        Sheet sheet = wb.createSheet(sheetName);
        int headNo = 0;
        if (StringUtils.isNotBlank(this.headerTitle)) {
            ++headNo;
            this.processHeadTitle(sheet, wb);
        }

        if (this.getHeaderInfo() != null && this.getHeaderInfo().size() > 0) {
            this.processHeadInfo(sheet, wb, headNo);
            ++headNo;
        }

        if (head) {
            this.processHead(mapper, sheet, order, headNo, wb);
            ++headNo;
        }

        this.processNew(datas, mapper, sheet, headNo, order);
        FileOutputStream stream = null;

        try {
            stream = this.getOutPutStream(targetPath, fileName);
            wb.write(stream);
        } catch (Exception var17) {
           // log.error("流操作出现异常");
        } finally {
            if (stream != null) {
                stream.close();
            }

        }

    }

    private FileOutputStream getOutPutStream(String targetPath, String fileName) throws IOException {
        File file = new File(targetPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        File targetFile = new File(targetPath + File.separator + fileName);
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        } else {
            targetFile.delete();
            targetFile.createNewFile();
        }

        FileOutputStream stream = FileUtils.openOutputStream(targetFile);
        return stream;
    }

    private void processHead(Mapper mapper, Sheet sheet, boolean order, int headNo, Workbook wb) throws ExcelForClassAnnotationNotFoundException, IllegalArgumentException, IllegalAccessException {
        List<ExcelProcessBean> beans = mapper.getBeans();
        int orderNo = order ? 1 : 0;
        Row row = sheet.createRow(headNo);
        CellStyle style = this.getBookColumnStyle(wb);
        if (order) {
            Cell cell = row.createCell(0);
            cell.setCellValue("序号");
            cell.setCellStyle(style);
        }

        Iterator var13 = beans.iterator();

        while(var13.hasNext()) {
            ExcelProcessBean excelProcessBean = (ExcelProcessBean)var13.next();
            Cell cell = row.createCell(excelProcessBean.getSort() + orderNo);
            cell.setCellValue(excelProcessBean.getName());
            cell.setCellStyle(style);
        }

    }

    private <T> void process(List<T> datas, Mapper mapper, Sheet sheet, int headNo, boolean order) throws ExcelForClassAnnotationNotFoundException, IllegalArgumentException, IllegalAccessException {
        int orderNo = order ? 1 : 0;
        List<ExcelProcessBean> excelProcessBeans = mapper.getBeans();

        for(int i = 0; i < datas.size(); ++i) {
            T data = datas.get(i);
            Row row = sheet.createRow(i + headNo);
            if (order) {
                Cell cell = row.createCell(0);
                cell.setCellValue((double)(i + orderNo));
            }

            Cell cell;
            String value;
            for(Iterator var20 = excelProcessBeans.iterator(); var20.hasNext(); cell.setCellValue(value)) {
                ExcelProcessBean bean = (ExcelProcessBean)var20.next();
                cell = row.createCell(bean.getSort() + orderNo);
                value = bean.getConvert().toString(Reflections.getFieldValue(data, bean.getFieldName()));
                if (StringUtils.isNotEmpty(bean.getDicType())) {
                    if (bean.isCollection()) {
                        String[] values = value.split("\\,");
                        value = "";
                        String[] var16 = values;
                        int var17 = values.length;

                        for(int var18 = 0; var18 < var17; ++var18) {
                            String str = var16[var18];
                            value = value + this.dic.getDic(bean.getDicType()).get(str) + ",";
                        }
                    } else if (StringUtils.isBlank(value)) {
                        value = "";
                    } else {
                        value = (String)this.dic.getDic(bean.getDicType()).get(value);
                    }
                }
            }
        }

    }

    private <T> void processNew(List<T> datas, Mapper mapper, Sheet sheet, int headNo, boolean order) throws ExcelForClassAnnotationNotFoundException, IllegalArgumentException, IllegalAccessException {
        Map<String, String> valueCache = new HashMap();
        Map<String, Map<Object, Object>> dicCache = new HashMap();
        int orderNo = order ? 1 : 0;
        List<ExcelProcessBean> excelProcessBeans = mapper.getBeans();

        for(int i = 0; i < datas.size(); ++i) {
            this.processData(datas, sheet, headNo, order, valueCache, dicCache, orderNo, excelProcessBeans, i);
        }

    }

    private <T> void processData(List<T> datas, Sheet sheet, int headNo, boolean order, Map<String, String> cache, Map<String, Map<Object, Object>> dicCache, int orderNo, List<ExcelProcessBean> excelProcessBeans, int i) {
        T data = datas.get(i);
        Row row = sheet.createRow(i + headNo);
        if (order) {
            Cell cell = row.createCell(0);
            cell.setCellValue((double)(i + orderNo));
        }

        int size = excelProcessBeans.size();

        for(int j = 0; j < size; ++j) {
            this.processCell(cache, orderNo, excelProcessBeans, dicCache, data, row, j);
        }

    }

    private <T> void processCell(Map<String, String> cache, int orderNo, List<ExcelProcessBean> excelProcessBeans, Map<String, Map<Object, Object>> dicCache, T data, Row row, int j) {
        ExcelProcessBean bean = (ExcelProcessBean)excelProcessBeans.get(j);
        Cell cell = row.createCell(bean.getSort() + orderNo);
        String value = this.getValue(data, bean);
        String type = bean.getDicType();
        if (StringUtils.isNotEmpty(type)) {
            if (bean.isCollection()) {
                String[] values = value.split("\\,");
                value = "";
                String[] var13 = values;
                int var14 = values.length;

                for(int var15 = 0; var15 < var14; ++var15) {
                    String str = var13[var15];
                    value = value + this.dic(type, cache, str, dicCache) + ",";
                }
            } else {
                value = this.dic(type, cache, value, dicCache);
            }
        }

        cell.setCellValue(value);
    }

    private <T> String getValue(T data, ExcelProcessBean bean) {
        String value = bean.getConvert().toString(Reflections.getFieldValue(data, bean.getFieldName()));
        return value;
    }

    private String dic(String type, Map<String, String> cache, String value, Map<String, Map<Object, Object>> dicCache) {
        String valueBak = this.getLocalDic(type, cache, value);
        if (null == valueBak) {
            valueBak = this.getDicValue(type, cache, value, dicCache);
        }

        return valueBak;
    }

    private String getLocalDic(String type, Map<String, String> cache, String value) {
        String valueBak = (String)cache.get(type + "-" + value);
        return valueBak;
    }

    private String getDicValue(String type, Map<String, String> cache, String value, Map<String, Map<Object, Object>> dicCache) {
        Map<Object, Object> map = this.getDic(type, dicCache);
        String valueBak = this.getDicValue2(value, map);
        this.saveLocalDic(type, cache, value, valueBak);
        return valueBak;
    }

    private String getDicValue2(String value, Map<Object, Object> map) {
        String valueBak = "";
        if (StringUtils.isNotBlank(value)) {
            valueBak = (String)map.get(value);
        }

        return valueBak;
    }

    private void saveLocalDic(String type, Map<String, String> cache, String value, String valueBak) {
        cache.put(type + "-" + value, valueBak);
    }

    private Map<Object, Object> getDic(String type, Map<String, Map<Object, Object>> dicCache) {
        Map<Object, Object> map = (Map)dicCache.get(type);
        if (null == map) {
            map = this.dic.getDic(type);
            dicCache.put(type, map);
        }

        return map;
    }

    private void processHeadTitle(Sheet sheet, Workbook wb) {
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(this.getHeaderTitle());
        CellStyle style = this.getHeadTitleStyle(wb);
        cell.setCellStyle(style);
        CellRangeAddress cra = new CellRangeAddress(0, 0, 0, this.getHeaderTitleSize() - 1);
        sheet.addMergedRegion(cra);
    }

    private void processHeadInfo(Sheet sheet, Workbook wb, int headNo) {
        Row row = sheet.createRow(headNo);
        sheet.setDefaultColumnWidth(16);
        List<String> list = this.getHeaderInfo();

        for(int i = 0; i < list.size(); ++i) {
            String infoValue = (String)list.get(i);
            Cell cell = row.createCell(i);
            cell.setCellValue(infoValue);
            CellStyle style = this.getHeadInfoStyle(wb);
            cell.setCellStyle(style);
        }

    }

    private CellStyle getHeadTitleStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font f = wb.createFont();
        f.setFontHeightInPoints((short)22);
        f.setBoldweight((short)700);
        f.setFontName("宋体");
        style.setFont(f);
        style.setAlignment((short)2);
        return style;
    }

    private CellStyle getHeadInfoStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font f = wb.createFont();
        f.setFontHeightInPoints((short)12);
        f.setBoldweight((short)700);
        f.setFontName("宋体");
        style.setFont(f);
        style.setAlignment((short)2);
        return style;
    }

    private CellStyle getBookColumnStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font f = wb.createFont();
        f.setFontHeightInPoints((short)12);
        f.setBoldweight((short)700);
        f.setFontName("Arial");
        style.setFont(f);
        style.setAlignment((short)2);
        return style;
    }

    public String getHeaderTitle() {
        return this.headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public List<String> getHeaderInfo() {
        return this.headerInfo;
    }

    public void setHeaderInfo(List<String> headerInfo) {
        this.headerInfo = headerInfo;
    }

    public int getHeaderTitleSize() {
        return this.headerTitleSize;
    }

    public void setHeaderTitleSize(int headerTitleSize) {
        this.headerTitleSize = headerTitleSize;
    }
}
