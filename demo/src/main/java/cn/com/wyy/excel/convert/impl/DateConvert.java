package cn.com.wyy.excel.convert.impl;

import cn.com.wyy.excel.convert.Convert;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvert implements Convert {
    public DateConvert() {
    }

    public String toString(Object obj) {
        if (obj == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format((Date)obj);
        }
    }
}
