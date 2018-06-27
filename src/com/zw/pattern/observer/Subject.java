package com.zw.pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 主题，主题一有改变立即通知订阅者
 * 好比订阅一样，一有新东西就会告诉你
 * Created by ZhaoWei on 2018/6/27/0027.
 */
public class Subject {

    //订阅的列表
    private List<Observer> observers = new ArrayList<>();

    //主题的更新状态
    private int state;

    //获取主题更新状态
    public int getState() {
        return state;
    }

    //增加订阅者
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    //取消订阅
    public void removeObserver(Observer observer){
        if(null!=observer){
            observers.remove(observer);
        }
    }

    //更新主题的状态，并通知所有订阅者
    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    //主题状态改变循环通知所有订阅者
    private void notifyAllObservers() {
        for(Observer observer: observers){
            observer.update();
        }
    }


}
