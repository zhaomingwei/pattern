package com.zw.pattern.proxy.jdk;

import com.zw.pattern.proxy.staticState.Proxy;

public class JDKProxyTest {

    public static void main(String[] args) {
        Person person = (Person) new MeiPo().bind(new XiaoMin());
        System.out.println(person.getClass());
        person.findGF();
    }

}
