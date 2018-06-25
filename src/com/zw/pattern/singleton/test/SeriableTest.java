package com.zw.pattern.singleton.test;

import com.zw.pattern.singleton.seriable.Seriable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by ZhaoWei on 2018/6/25/0025.
 */
public class SeriableTest {

    public static void main(String[] args) {

        Seriable seriable1 = null;
        Seriable seriable2 = Seriable.getInstance();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("F:\\ss.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(seriable2);
            oos.flush();
            oos.close();

            FileInputStream fis = new FileInputStream("F:\\ss.txt");
            ObjectInputStream oos2 = new ObjectInputStream(fis);
            seriable1 = (Seriable) oos2.readObject();
            oos2.close();

            System.out.println(seriable1);
            System.out.println(seriable2);
            System.out.println(seriable1 == seriable2);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
