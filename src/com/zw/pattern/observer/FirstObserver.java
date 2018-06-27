package com.zw.pattern.observer;

/**
 * Created by ZhaoWei on 2018/6/27/0027.
 */
public class FirstObserver extends Observer {

    public FirstObserver(Subject subject) {
        this.subject = subject;
        this.subject.addObserver(this);
    }

    @Override
    public void update() {
        System.out.println("我是第一个观察者，已经收到主题更新通知！");
    }
}
