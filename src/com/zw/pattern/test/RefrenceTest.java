package com.zw.pattern.test;

public class RefrenceTest {

    public static void main(String[] args) throws InterruptedException {
        Integer i = 100;
        Integer j = 100;
        System.out.println(i==j);
        System.out.println(i.equals(j));

        Integer m = 128;
        Integer n = 128;
        System.out.println(m==n);
        System.out.println(m.equals(n));
    }

}
