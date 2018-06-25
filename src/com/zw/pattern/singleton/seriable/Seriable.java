package com.zw.pattern.singleton.seriable;

import java.io.Serializable;

/**
 * 反序列化时会破坏单例
 *
 * 序列化：
 * 就是把内存中的状态通过转换为字节码的形式(IO流)
 * 写入到其他地方(磁盘、网络等)
 * 内存中状态永久保存
 *
 * 反序列化：
 * 把持久化的字节码内容通过IO流转为java对象
 * 转化过程中会重新new对象
 */
public class Seriable implements Serializable {

    private Seriable(){}

    private static Seriable INSTANCE = new Seriable();

    public static Seriable getInstance(){
        return INSTANCE;
    }

    //反序列化时通过此方法可保证单例原则
    private Object readResolve(){
        return INSTANCE;
    }

}
