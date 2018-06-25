package com.zw.pattern.singleton.lazy;

/**
 * 懒汉式单例
 * 优点：需要使用到时在初始化，节约内存
 * 缺点：多线程同时请求时可能会出现两个对象，不是单例情况
 *
 * 需要优化
 */
public class LazyOne {

    private LazyOne(){}

    private static LazyOne lazyOne = null;

    public static LazyOne getInstance(){
        if(null == lazyOne){
            //第一次调用：第一个线程进入了if但是还没new
            //紧接着第二个线程也进入到了if，然后第一个线程new了一个对象
            //第二个线程也new了一个对象，此时就会出现两个对象
            //不能保证是一个对象
            lazyOne = new LazyOne();
        }
        return lazyOne;
    }

}
