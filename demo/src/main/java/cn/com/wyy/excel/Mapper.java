package cn.com.wyy.excel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Mapper {
    private List<ExcelProcessBean> beans = new ArrayList();
    private String sheetName;
    private Comparator<ExcelProcessBean> comparator = new Comparator<ExcelProcessBean>() {
        public int compare(ExcelProcessBean bean1, ExcelProcessBean bean2) {
            return bean1.getSort() - bean2.getSort();
        }
    };

    public Mapper() {
    }

    public List<ExcelProcessBean> getBeans() {
        return this.beans;
    }

    public void setBeans(List<ExcelProcessBean> beans) {
        this.beans = beans;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public void addBean(ExcelProcessBean bean) {
        this.beans.add(bean);
    }

    public void sort() {
        if (this.beans != null) {
            Collections.sort(this.beans, this.comparator);
        }

    }
}