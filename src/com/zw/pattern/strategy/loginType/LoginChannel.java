package com.zw.pattern.strategy.loginType;

import com.zw.pattern.strategy.LoginState;
import com.zw.pattern.strategy.User;

//登陆渠道
public interface LoginChannel {

    LoginState login(String userName, String sex, int age);

}
