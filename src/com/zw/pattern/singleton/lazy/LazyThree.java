package com.zw.pattern.singleton.lazy;

/**
 * 懒汉式单例
 * 特点：外部类被调用时才会加载内部类
 * 内部类一定在方法调用之前初始化，避免了线程安全问题
 *
 * 此种方式兼顾饿汉式的内存浪费也兼顾了synchronized性能问题
 */
public class LazyThree {

    private static boolean initialized = false;

    private LazyThree(){
        synchronized (LazyThree.class){
            if(initialized == false){
                initialized = !initialized;
            }else {
                throw new RuntimeException("单例已被侵犯了");
            }
        }
    }

    //final保证这个方法不会被重写，重载
    public static final LazyThree getInstance(){
        return LazyHolder.LAZY;
    }

    //静态内部类,在调用的时候才会加载
    //static final 是一个静态常量,存储在内存中的常量区,
    //在new之前会看常量里面是否有这个值,如果有,就不再执行new操作了
    private static class LazyHolder{
        private static final LazyThree LAZY = new LazyThree();
    }


}
