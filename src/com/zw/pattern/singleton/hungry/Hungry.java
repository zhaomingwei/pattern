package com.zw.pattern.singleton.hungry;

/**
 * 饿汉式单利
 * 类加载时就初始化
 * 优点：执行效率高(没有加锁)
 * 缺点：类初始化时就加载，不管最后有没有使用到都会加载，这样一定程度上可能会造成资源浪费
 *
 * 线程安全：在线程访问时它已经初始化完成，不存在线程不安全
 */
public class Hungry {

    private Hungry(){}

    private static final Hungry hungry = new Hungry();

    public static Hungry getInstance(){
        return hungry;
    }

}
