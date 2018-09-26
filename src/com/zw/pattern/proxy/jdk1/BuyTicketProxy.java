package com.zw.pattern.proxy.jdk1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BuyTicketProxy implements InvocationHandler {
    //代理的目标对象，这里可以认为所有使用APP购买车票的乘客
    private Object target;
    //生成一个代理对象
    //Proxy.newProxyInstance参数说明：
    //第一个参数：当前目标类加载器。如果没有重写ClassLoader并用自定义的加载器加载类，一般都是同一个类加载器
    //第二个参数：当前目标类所实现的所有接口。如BuyTicketProxy实现的所有接口为InvocationHandler,多个的话按实现顺序排序
    //第三个参数：this，指当前对象，即是BuyTicketProxy
    public Object bind(Object target){
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    /**
     * @param proxy 代理类
     * @param method 代理方法
     * @param args 代理方法的参数
     * @return 代理方法的返回类型
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开始代理买票");
        Object result = method.invoke(target, args);
        System.out.println("买票结束");
        return result;
    }
}
