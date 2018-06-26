package com.zw.pattern.proxy.staticState;

public class StaticProxyTest {

    public static void main(String[] args) {
        User user = new User();
        user.setCardCode("3242319890652");
        user.setHomeAddr("中国");
        user.setSex("男");
        user.setUserName("阿西吧");
        Proxy proxy = new Proxy(user);
        proxy.buyTicket();
    }

}
