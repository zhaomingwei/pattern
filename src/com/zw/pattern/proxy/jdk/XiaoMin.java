package com.zw.pattern.proxy.jdk;

/**
 * 实现代理接口方法
 */
public class XiaoMin implements Person {
    @Override
    public void findGF() {
        System.out.println("我要找女朋友！");
    }
}
