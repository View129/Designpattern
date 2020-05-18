package cn.com.wyy.excel.annotations;

import cn.com.wyy.excel.convert.Convert;
import cn.com.wyy.excel.convert.impl.Default;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface ExcelForFeild {
    String value() default "";

    int sort() default 0;

    String dicType() default "";

    Class<? extends Convert> convert() default Default.class;

    boolean isCollection() default false;
}
