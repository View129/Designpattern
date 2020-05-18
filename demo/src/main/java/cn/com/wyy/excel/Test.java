package cn.com.wyy.excel;

import cn.com.wyy.excel.dic.DefaultDicImpl;
import cn.com.wyy.excel.exception.ExcelForClassAnnotationNotFoundException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        String filePath = "/home/wyy/queryFiles/temp";
        String filename = "a.xls";
        List<ExportBo> list = new ArrayList<ExportBo>();
        list.add(new ExportBo());
        list.add(new ExportBo());
        list.add(new ExportBo());

        Excel excel=null;
        excel = ExcelUtil.initExcel(new DefaultDicImpl(), ExportBo.class);
        excel.setHeaderTitle("表头");//表头
        boolean isHaveOrder=true;
        excel.setHeaderTitleSize(AnnotionUtil.getAnnotionCount(isHaveOrder, ExportBo.class));//表头标题所占列数

        List<String> headerInfo = ExcelUtil.setExcelHeader("填报单位", "2009-5-5", "suer");
        excel.setHeaderInfo(headerInfo);//表头附加信息
        excel.write(list, ExportBo.class, filePath, filename, true, isHaveOrder);
        //file = new File(filePath + File.separator + filename);
        //return FileUtil.download(file, response);
    }
}
