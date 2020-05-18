package cn.com.wyy.excel;

import cn.com.wyy.excel.annotations.ExcelForClass;
import cn.com.wyy.excel.annotations.ExcelForFeild;
import cn.com.wyy.excel.convert.impl.DateConvert;

import java.util.Date;

@ExcelForClass(sheetName = "查询记录")
public class ExportBo {
    private String id;
    @ExcelForFeild(value = "查询时间",sort = 0, convert = DateConvert.class)
    private Date queryTime=new Date();
    @ExcelForFeild(value = "用户代码",sort = 1)
    private String operator="23";
    @ExcelForFeild(value = "用户姓名",sort = 2)
    private String operatorName="wuyiyong";//
    @ExcelForFeild(value = "客户名称",sort=3)
    private String customerName="客户名称";
    @ExcelForFeild(value = "查询原因1",sort = 4, dicType = "qryReason")
    private String qryReason1="1";
    @ExcelForFeild(value = "查询原因2",sort = 5, dicType = "qryReason")
    private String qryReason2="2";
}
