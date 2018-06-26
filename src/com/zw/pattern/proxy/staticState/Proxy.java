package com.zw.pattern.proxy.staticState;

/**
 * 具体代理接口的实现
 */
public class Proxy implements IProxy{

    /**
     * 被代理对象的引用
     */
    private User user;

    /*
     * 构造方法获取对象
     */
    public Proxy(User user){
        this.user = user;
    }

    @Override
    public void buyTicket() {
        // 具体实现逻辑
        if (user != null) {
            System.out.println("***********我要买票***********");
            System.out.println("信息如下：");
            System.out.println("姓名：" + user.getUserName());
            System.out.println("性别：" + user.getSex());
            System.out.println("身份证号：" + user.getCardCode());
            System.out.println("住址：" + user.getHomeAddr());
            System.out.println("***********信息已核对***********");
            System.out.println("出票成功：动车D2222次09车20A");
        }
    }
}
