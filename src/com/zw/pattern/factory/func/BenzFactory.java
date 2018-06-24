package com.zw.pattern.factory.func;

import com.zw.pattern.factory.BenzCar;
import com.zw.pattern.factory.Car;

/**
 * Created by ZhaoWei on 2018/6/24/0024.
 */
public class BenzFactory implements Factory {
    @Override
    public Car getCar() {
        return new BenzCar();
    }
}
