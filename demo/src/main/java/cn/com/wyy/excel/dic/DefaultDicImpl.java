package cn.com.wyy.excel.dic;

import java.util.HashMap;
import java.util.Map;

public class DefaultDicImpl implements Dic{
    public Map<Object, Object> getDic(String var1) {
        Map<Object,Object> map = new HashMap<Object,Object>();
        map.put("1","字典1");
        map.put("2","字典2");
        return map;
    }
}
