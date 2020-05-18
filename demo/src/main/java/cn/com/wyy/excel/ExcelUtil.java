package cn.com.wyy.excel;

import cn.com.wyy.excel.dic.DefaultDicImpl;
import cn.com.wyy.excel.dic.Dic;
import cn.com.wyy.excel.exception.ExcelForClassAnnotationNotFoundException;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {
    public static Excel excel;

    //private Dic dic = new DefaultDicImpl();

    public ExcelUtil() {
    }

    public static Excel initExcel(Dic dic, Class... calzz) throws InstantiationException, IllegalAccessException, ExcelForClassAnnotationNotFoundException {
        if (calzz.length == 0) {
            throw new RuntimeException("calzz is null!");
        } else if (null == dic) {
            throw new RuntimeException("dic is null!");
        } else {
            excel = new Excel(dic);
            excel.processAnnotations(calzz);
            return excel;
        }
    }

    public static List<String> setExcelHeader(String orgName, String time, String userName) {

        List<String> headerInfo = new ArrayList();
        headerInfo.add("填报单位：");
        headerInfo.add(orgName);
        headerInfo.add("制表时间：");
        headerInfo.add(time);
        headerInfo.add("制表人：");
        headerInfo.add(userName);
        return headerInfo;
    }

    public static void setCellToString(Row row) {
        for(int cellNum = 0; cellNum <= row.getLastCellNum(); ++cellNum) {
            if (row.getCell(cellNum) != null) {
                row.getCell(cellNum).setCellType(1);
            }
        }

    }
}