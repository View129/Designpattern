package cn.com.wyy.excel;

import cn.com.wyy.excel.convert.Convert;

public class ExcelProcessBean {
    private String name;
    private String fieldName;
    private String dicType;
    private int sort;
    private Convert convert;
    private boolean isCollection;

    public ExcelProcessBean() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDicType() {
        return this.dicType;
    }

    public void setDicType(String dicType) {
        this.dicType = dicType;
    }

    public int getSort() {
        return this.sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Convert getConvert() {
        return this.convert;
    }

    public void setConvert(Convert convert) {
        this.convert = convert;
    }

    public boolean isCollection() {
        return this.isCollection;
    }

    public void setCollection(boolean isCollection) {
        this.isCollection = isCollection;
    }
}
