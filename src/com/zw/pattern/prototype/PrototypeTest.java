package com.zw.pattern.prototype;

/**
 * Created by ZhaoWei on 2018/6/25/0025.
 */
public class PrototypeTest {

    public static void main(String[] args) {
        BenZCar benzCar = new BenZCar();
        BenZCar b = (BenZCar) benzCar.deepClone();
        System.out.println(b == benzCar);
    }

}
