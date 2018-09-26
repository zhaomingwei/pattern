package com.zw.pattern.proxy.jdk1;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

public class test {

    public static void main(String[] args) throws IOException {
        //bind方法作用：传入一个乘客，然后返回一个乘客的代理，然后由这个代理去买票
        IBuyTicket iBuyTicket = (IBuyTicket) new BuyTicketProxy().bind(new CustomerBuyTicketImpl());
        iBuyTicket.butTicket();
        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{BuyTicketProxy.class});
        FileOutputStream fos = new FileOutputStream("D://$Proxy0.class");
        fos.write(bytes);
        fos.close();
    }

}
