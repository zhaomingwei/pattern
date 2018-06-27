package com.zw.pattern.delegate;

public class StudentA implements Student {
    @Override
    public void doing(String command) {
        System.out.println("我是学生A,正在做" + command + "工作");
    }
}
