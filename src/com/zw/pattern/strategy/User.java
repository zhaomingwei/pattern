package com.zw.pattern.strategy;

import com.zw.pattern.strategy.loginType.LoginType;

public class User {

    private String userName;

    private String sex;

    private int age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LoginState login(LoginType loginType){
        return loginType.get().login(userName, sex, age);
    }

}
