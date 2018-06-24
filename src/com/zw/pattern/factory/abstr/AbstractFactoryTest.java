package com.zw.pattern.factory.abstr;

/**
 * Created by ZhaoWei on 2018/6/24/0024.
 */
public class AbstractFactoryTest {

    public static void main(String[] args) {
        CarFactory factory = new CarFactory();
        System.out.println(factory.getBenzCar());
    }

}
