package com.zw.pattern.prototype;

import java.io.*;

/**
 * Created by ZhaoWei on 2018/6/25/0025.
 */
public class BenZCar extends Car implements Cloneable,Serializable {

    public Producer producer;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this.deepClone();
    }

    public Object deepClone(){
        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            System.out.println(this);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            BenZCar newBZCar = (BenZCar)ois.readObject();
            System.out.println(newBZCar);
            return newBZCar;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
