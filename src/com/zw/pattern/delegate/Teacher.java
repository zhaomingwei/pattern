package com.zw.pattern.delegate;

import java.util.HashMap;
import java.util.Map;

//老师
public class Teacher implements Student{

    private Map<String, Student> studentMap = new HashMap<String, Student>();

    public Teacher(){
        studentMap.put("檫黑板", new StudentA());
        studentMap.put("扫地", new StudentB());
    }

    @Override
    public void doing(String command) {
        studentMap.get(command).doing(command);
    }
}
