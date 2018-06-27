package com.zw.pattern.strategy;

import com.zw.pattern.strategy.loginType.LoginChannel;
import com.zw.pattern.strategy.loginType.LoginType;

public class StrategyTest {

    public static void main(String[] args) {
        User user = new User();
        user.setUserName("美女");
        user.setSex("女");
        user.setAge(20);
        System.out.println(user.login(LoginType.QQ_Login));
    }

}
