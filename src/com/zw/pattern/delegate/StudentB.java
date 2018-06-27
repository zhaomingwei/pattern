package com.zw.pattern.delegate;

public class StudentB implements Student {
    @Override
    public void doing(String command) {
        System.out.println("我是学生B,正在做" + command + "工作");
    }
}
