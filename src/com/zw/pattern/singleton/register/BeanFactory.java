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

    private BeanFactory(){}

    //ConcurrentHashMap线程安全
    private static Map ioc = new ConcurrentHashMap();

    public static Object getBean(String className){
        synchronized(ioc){
            if(ioc.containsKey(className)){
                return ioc.get(className);
            }
            Object obj = null;
            try {
                obj = Class.forName(className).newInstance();
                // 如果实例对象在不存在，我们注册到单例注册表中。
                ioc.put(className, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return obj;
        }
    }

}
