package com.zw.pattern.singleton.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RegisterMap {

    public RegisterMap() {}

    private static Map register = new ConcurrentHashMap();

    public static RegisterMap getInstance(String name){
        if(null==name){
            name = RegisterMap.class.getName();
        }
        if(null==register.get(name)){
            register.put(name, new RegisterMap());
        }
        return (RegisterMap) register.get(name);
    }

}
