package cn.com.wyy.excel.convert.impl;

import cn.com.wyy.excel.convert.Convert;

public class Default implements Convert {
    public Default() {
    }

    public String toString(Object obj) {
        return obj == null ? null : obj.toString();
    }
}
