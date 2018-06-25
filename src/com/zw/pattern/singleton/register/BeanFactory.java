package com.zw.pattern.singleton.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * spring中就是用此种注册式单例方法
 *
 * 先检查ConcurrentHashMap中是否有我们需要的bean
 * 有则直接拿出返回
 * 没有则放入ConcurrentHashMap中在返回，下次再来取则到ConcurrentHashMap中拿出返回即可
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
