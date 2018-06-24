package com.zw.pattern.factory.func;

import com.zw.pattern.factory.Car;

/**
 * 工厂模型
 * Created by ZhaoWei on 2018/6/24/0024.
 */
public interface Factory {

    //统一产品出口
    Car getCar();

}
