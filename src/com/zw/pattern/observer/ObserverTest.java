package com.zw.pattern.observer;

/**
 * Created by ZhaoWei on 2018/6/27/0027.
 */
public class ObserverTest {

    public static void main(String[] args) {
        Subject subject = new Subject();

        new FirstObserver(subject);
        new SecondObserver(subject);
        subject.setState(12);

    }

}
