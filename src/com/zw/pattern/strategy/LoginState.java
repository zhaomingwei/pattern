package com.zw.pattern.strategy;

public class LoginState {

    private String code;

    private String msg;

    private Object data;

    public LoginState(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString(){
        return "登陆状态：[" + code + "]," + "msg：[" + msg  +"]，[" + data +"]" + "已上线";
    }

}
