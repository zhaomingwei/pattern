package com.zw.pattern.singleton.lazy;

/**
 * 懒汉式单例（加锁）
 * 给getInstance方法加锁
 * 优点：保证了输出肯定是一个对象
 * 缺点：执行效率大大下降
 */
public class LazyTwo {

    private LazyTwo(){}

    private static LazyTwo lazyTwo = null;

    //给方法加锁，保证只有一个线程执行初始化
    public static synchronized LazyTwo getInstance(){
        if(null == lazyTwo){
            lazyTwo = new LazyTwo();
        }
        return lazyTwo;
    }

}
