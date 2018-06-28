package com.zw.pattern.decorator;

public class MilkDecorator extends Decorator {
    /**
     * 通过组合的方式把Coffee对象传递进来
     *
     * @param coffee
     */
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    int getPrice() {
        return mCoffee.getPrice() + 10;
    }

    @Override
    String getName() {
        return "添加牛奶";
    }
}
