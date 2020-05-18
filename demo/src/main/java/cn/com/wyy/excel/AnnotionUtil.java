package cn.com.wyy.excel;

import cn.com.wyy.excel.annotations.ExcelForFeild;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

public class AnnotionUtil {
    public AnnotionUtil() {
    }

    public static int getAnnotionCount(boolean order, Class<?> clz) {
        AtomicInteger count = new AtomicInteger(0);
        if (order) {
            count.getAndIncrement();
        }

        Field[] fields = clz.getDeclaredFields();
        Field[] var4 = fields;
        int var5 = fields.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            ExcelForFeild annotation = (ExcelForFeild)field.getAnnotation(ExcelForFeild.class);
            if (null != annotation) {
                count.getAndIncrement();
            }
        }

        return count.intValue();
    }
}