package com.zw.pattern.observer;

/**
 * Created by ZhaoWei on 2018/6/27/0027.
 */
public class SecondObserver extends Observer {

    //这里是通过构造函数注入主题，然后把当前对象添加到订阅的主题里
    public SecondObserver(Subject subject) {
        this.subject = subject;
        this.subject.addObserver(this);
    }

    @Override
    public void update() {
        System.out.println("我是第二个观察者，已经收到主题更新通知!");
    }
}
