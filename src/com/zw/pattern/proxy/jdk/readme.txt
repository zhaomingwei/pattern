1、真实角色：被代理的角色(委托类)--XiaoMin.java
2、抽象角色：一般使用接口或者抽象类来实现--Person.java
3、代理角色：代理真实角色，代理真实角色后一般会做一些附属的操作--MeiPo.java

代理类里步奏：
1、实现InvocationHandler接口
2、私有的Object的代理对象
3、建立代理对象和真实对象的代理关系，并返回代理对象（bind绑定）
4、重写invoke方法
