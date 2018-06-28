package com.zw.pattern.decorator;

/**
 * 基类咖啡
 *
 * 场景：一个原料咖啡，然后可以添加不同的配料
 * 加糖、加牛奶、加奶泡，加不同的配料有不同的价格
 * 使用装饰器模式可以很好的实现
 */
public abstract class Coffee {

    /**
     * 返回价格
     * @return
     */
    abstract int getPrice();

    /**
     * 返回名称
     * @return
     */
    abstract String getName();

}
