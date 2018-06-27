package com.zw.pattern.observer;

/**
 * 订阅者,订阅者不止一个,用一个抽象类，所有具体订阅者实现它
 * Created by ZhaoWei on 2018/6/27/0027.
 */
public abstract class Observer {

    //订阅的主题
    protected Subject subject;

    //抽象方法供具体订阅者实现
    public abstract void update();

}
