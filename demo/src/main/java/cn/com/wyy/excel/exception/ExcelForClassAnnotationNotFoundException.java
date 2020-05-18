package cn.com.wyy.excel.exception;

import cn.com.wyy.excel.annotations.ExcelForClass;

public class ExcelForClassAnnotationNotFoundException extends Exception {
    private static final long serialVersionUID = 2667390749480821478L;

    public ExcelForClassAnnotationNotFoundException() {
    }

    public ExcelForClassAnnotationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelForClassAnnotationNotFoundException(String message) {
        super(message);
    }

    public ExcelForClassAnnotationNotFoundException(Throwable cause) {
        super(cause);
    }

    public ExcelForClassAnnotationNotFoundException(Class<?> clz) {
        super(clz.getName() + "未标注" + ExcelForClass.class.getName() + "注解");
    }
}
