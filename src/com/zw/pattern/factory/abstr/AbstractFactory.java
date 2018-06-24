package com.zw.pattern.factory.abstr;

import com.zw.pattern.factory.Car;

/**
 * 抽象工厂是用户的主入口
 * 在Spring中应用得最为广泛的一种设计模式
 * 易于扩展
 * Created by ZhaoWei on 2018/6/24/0024.
 */
public abstract class AbstractFactory {

    //公共的逻辑
    //方便于统一管理

    /**
     * 获取宝马汽车
     * @return
     */
    public abstract Car getBMWCar();

    /**
     * 获取奔驰汽车
     * @return
     */
    public abstract Car getBenzCar();


}
