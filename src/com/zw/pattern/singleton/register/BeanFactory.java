package com.zw.pattern.singleton.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * spring中就是用此种注册式单例方法
 */
public class BeanFactory {

    //ConcurrentHashMap线程安全
    private static Map ioc = new ConcurrentHashMap();

    public static Object getBean(String className){
        if(ioc.containsKey(className)){
            return ioc.get(className);
        }
        Object obj = null;
        try {
            obj = Class.forName(className).getInterfaces();
            ioc.put(className, obj);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
