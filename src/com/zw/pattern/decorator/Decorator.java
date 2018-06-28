package com.zw.pattern.decorator;

/**
 * Decorator类继承Coffee基类
 */
public abstract class Decorator extends Coffee{

    protected Coffee mCoffee;

    /**
     * 通过组合的方式把Coffee对象传递进来
     * @param coffee
     */
    public Decorator(Coffee coffee){
        this.mCoffee = coffee;
    }

}
