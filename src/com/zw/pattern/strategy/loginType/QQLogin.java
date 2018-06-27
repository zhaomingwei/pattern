package com.zw.pattern.strategy.loginType;

import com.zw.pattern.strategy.LoginState;
import com.zw.pattern.strategy.User;

public class QQLogin implements LoginChannel{
    @Override
    public LoginState login(String userName, String sex, int age) {
        System.out.println("QQ登陆中");
        System.out.println("QQ登陆成功");
        return new LoginState("200", "登陆成功", userName);
    }
}
