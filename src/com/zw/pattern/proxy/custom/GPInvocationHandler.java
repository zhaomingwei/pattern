package com.zw.pattern.proxy.custom;

import java.lang.reflect.Method;

/**
 * Created by ZhaoWei on 2018/6/26/0026.
 */
public interface GPInvocationHandler {

    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;

}
