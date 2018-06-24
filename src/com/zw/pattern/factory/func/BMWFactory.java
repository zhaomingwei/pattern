package com.zw.pattern.factory.func;

import com.zw.pattern.factory.BMWCar;
import com.zw.pattern.factory.Car;

/**
 * Created by ZhaoWei on 2018/6/24/0024.
 */
public class BMWFactory implements Factory {

    @Override
    public Car getCar(){
        return new BMWCar();
    }

}
