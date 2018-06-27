package com.zw.pattern.adapter;

/**
 * Created by ZhaoWei on 2018/6/27/0027.
 */
public class LoginForThirdServices extends LoginService {

    public ResultMsg LoginForQQ(String openId){
        //1、openId是全局唯一，我们可以把它当做是一个用户名(加长)
        //2、密码默认为QQ_EMPTY
        //3、注册（在原有系统里面创建一个用户）
        //4、调用原来的登录方法
        return loginForRegist(openId,null);
    }

    public ResultMsg LoginForWeChat(String openId){
        return null;
    }

    public ResultMsg LoginForTelphone(String telphone,String code){
        return null;
    }

    private ResultMsg loginForRegist(String username,String password) {
        super.register(username, password);
        return super.login(username, password);
    }

}
