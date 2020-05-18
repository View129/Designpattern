package cn.com.wyy.excel.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface ExcelForClass {
    String sheetName() default "";
}
