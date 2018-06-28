package com.zw.pattern.decorator;

public class DecoratorTest {

    public static void main(String[] args) {
        Coffee mCoffee = new SimpleCoffee();
//        mCoffee = new MilkDecorator(mCoffee);
        mCoffee = new MilkFoamDecorator(mCoffee);
        System.out.println("名称：" + mCoffee.getName() + "，价格：" + mCoffee.getPrice() +"元");
    }

}
