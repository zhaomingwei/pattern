package com.zw.pattern.proxy.jdk1;

/**
 * 具体代理接口的实现
 */
public class CustomerBuyTicketImpl implements IBuyTicket {
    @Override
    public void butTicket() {
        System.out.println("我要买上海南到霍邱的硬卧!");
    }
}
