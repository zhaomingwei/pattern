package com.zw.pattern.adapter;

/**
 * Created by ZhaoWei on 2018/6/27/0027.
 */
public class LoginService {

    /**
     * 注册方法
     * @param userName
     * @param password
     * @return
     */
    public ResultMsg register(String userName, String password){
        return new ResultMsg("200", userName, password);
    }

    /**
     * 登陆方法
     * @return
     */
    public ResultMsg login(String userName, String password){
        return new ResultMsg("200", userName, password);
    }

}
