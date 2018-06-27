package com.zw.pattern.strategy.loginType;

//相当于一个常量池,登陆渠道改变只需要维护此类
public enum LoginType {

    QQ_Login(new QQLogin()),
    WX_Login(new WXLogin()),
    WY_Login(new WYLogin());

    private LoginChannel loginChannel;

    LoginType(LoginChannel loginChannel){
        this.loginChannel = loginChannel;
    }

    public LoginChannel get(){
        return this.loginChannel;
    }

}
