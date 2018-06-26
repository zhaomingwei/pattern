package com.zw.pattern.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibLiePin implements MethodInterceptor {

    /**
     * 生产CGLIB代理对象
     * @param clazz
     * @return clazz类的CGLIB代理对象
     */
    public Object getInstance(Class<?> clazz){
        //CGLIB enhancer 增强类对象
        Enhancer enhancer = new Enhancer();
        //设置增强类型
        enhancer.setSuperclass(clazz);
        //定义代理落脚对象为当前对象，要求当前对象实现MethodInterceptor方法
        enhancer.setCallback(this);
        //生产并返回代理对象
        return  enhancer.create();
    }

    /**
     * 代理逻辑方法
     * @param o 代理对象
     * @param method 方法
     * @param objects 方法参数
     * @param methodProxy 方法代理
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        //业务的增强
        System.out.println("我是猎头：我要给你找工作，现在已经拿到你的需求");
        System.out.println("开始物色");
        Object obj = methodProxy.invokeSuper(o,objects);
        System.out.println("如果合适的话，就准备入职");
        return obj;

    }
}
