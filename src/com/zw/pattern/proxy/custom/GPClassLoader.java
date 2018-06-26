package com.zw.pattern.proxy.custom;

import java.io.*;

/**
 * Created by ZhaoWei on 2018/6/26/0026.
 */
public class GPClassLoader extends ClassLoader {

    private File classPathFile;

    public GPClassLoader(){
        String classPath = GPClassLoader.class.getResource("").getPath();
        this.classPathFile = new File(classPath);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = GPClassLoader.class.getPackage().getName() + "." + name;
        if(null!=classPathFile){
            File classFile = new File(classPathFile, name.replaceAll("\\.", "/") + ".class");
            if(classFile.exists()){
                FileInputStream fis = null;
                ByteArrayOutputStream out = null;
                try {
                    fis = new FileInputStream(classFile);
                    out = new ByteArrayOutputStream();
                    byte[] buff = new byte[1024];
                    int len;
                    while ((len = fis.read(buff))!=-1){
                        out.write(buff,0,len);
                    }
                    return defineClass(className, out.toByteArray(),0,out.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(fis!=null){
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(out!=null){
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

}
