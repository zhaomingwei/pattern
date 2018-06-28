package com.zw.pattern.decorator;

public class MilkFoamDecorator extends Decorator {
    /**
     * 通过组合的方式把Coffee对象传递进来
     *
     * @param coffee
     */
    public MilkFoamDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    int getPrice() {
        return mCoffee.getPrice() + 12;
    }

    @Override
    String getName() {
        return "添加奶泡";
    }
}
