package com.zw.pattern.singleton.test;

import com.zw.pattern.singleton.lazy.LazyThree;

public class LazyTest {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for(int i=0;i<100000000;i++){
            Object obj = LazyThree.getInstance();
        }
        long end = System.currentTimeMillis();
        System.out.println("总耗时:"+(end-start));
    }

}
