package com.zw.pattern.singleton.test;

import com.zw.pattern.singleton.register.BeanFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by ZhaoWei on 2018/6/25/0025.
 */
public class BeanFactoryTest {

    public static void main(String[] args) {
        int count = 200;
        CountDownLatch latch = new CountDownLatch(count);
        long start = System.currentTimeMillis();
        for(int i=0;i<count;i++){
            new Thread(){
                public void run(){
                    try {
                        // 阻塞
                        // count = 0 就会释放所有的共享锁
                        // 万箭齐发
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Object obj = BeanFactory.getBean("com.zw.pattern.singleton.Pojo");
                    System.out.println(System.currentTimeMillis() + ":" + obj);
                }
            }.start();
            //每次启动一个线程，count --
            latch.countDown();
        }
        long end = System.currentTimeMillis();
        System.out.println("总耗时：" + (end - start));
    }


}
