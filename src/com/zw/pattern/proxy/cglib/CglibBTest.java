package com.zw.pattern.proxy.cglib;

public class CglibBTest {

    public static void main(String[] args) {
        LiSi lisi = (LiSi) new CglibLiePin().getInstance(LiSi.class);
        lisi.findJob();
    }

}
