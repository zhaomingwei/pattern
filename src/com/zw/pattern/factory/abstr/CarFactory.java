package com.zw.pattern.factory.abstr;

import com.zw.pattern.factory.Car;
import com.zw.pattern.factory.func.BMWFactory;
import com.zw.pattern.factory.func.BenzFactory;

/**
 * Created by ZhaoWei on 2018/6/24/0024.
 */
public class CarFactory extends AbstractFactory{

    /**
     * 获取宝马汽车
     * @return
     */
    public Car getBMWCar(){
        return new BMWFactory().getCar();
    };

    /**
     * 获取奔驰汽车
     * @return
     */
    public Car getBenzCar(){
        return new BenzFactory().getCar();
    };

}
