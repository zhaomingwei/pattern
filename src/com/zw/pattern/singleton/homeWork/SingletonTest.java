package com.zw.pattern.singleton.homeWork;

import java.lang.reflect.Constructor;

/**
 * Created by ZhaoWei on 2018/6/25/0025.
 */
public class SingletonTest {

    public static void main(String[] args) {
        Class<?> clazz = Singleton.class;

        try {
            //通过反射得到私有构造方法
            Constructor c = clazz.getDeclaredConstructor();
            //强制设置访问
            c.setAccessible(true);
            //暴力初始化
            Object o1 = c.newInstance();

            Object o2 = c.newInstance();

            System.out.println(o1 == o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
