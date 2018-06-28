package com.zw.pattern.decorator;

/**
 * 其中一种基本咖啡
 */
public class SimpleCoffee extends Coffee{

    /**
     * 咖啡原料
     * @return
     */
    @Override
    int getPrice() {
        return 2;
    }

    /**
     * 咖啡名称
     * @return
     */
    @Override
    String getName() {
        return "咖啡原料";
    }
}
