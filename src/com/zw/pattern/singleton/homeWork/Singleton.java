package com.zw.pattern.singleton.homeWork;

/**
 * Created by ZhaoWei on 2018/6/25/0025.
 */
public class Singleton {

    private static boolean b = false;

    private Singleton(){
        if(!b){
            b = true;
        }else {
            throw new RuntimeException("世界上不可能有第二个你！");
        }
    }

    public static Singleton getInstance(){
        return MyLover.singleLover;
    }

    private static class MyLover{
        private static final Singleton singleLover = new Singleton();
    }

}
