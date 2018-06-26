package com.zw.pattern.proxy.custom;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by ZhaoWei on 2018/6/26/0026.
 */
public class GPProxy {

    public final static String ln = "\r\n";

    public static Object newProxyInstance(GPClassLoader classLoader, Class<?>[] interfaces, GPInvocationHandler h){
        try {
            //1、动态生成源代码.java文件
            String src = generateSrc(interfaces);

            //2、Java文件输出到磁盘
            String filePath = GPProxy.class.getResource("").getPath();
            System.out.println(filePath);
            File f = new File(filePath + "$Proxy0.java");
            FileWriter fw = new FileWriter(f);
            fw.write(src);
            fw.flush();
            fw.close();

            //3、把生成的.java文件编译成.class文件
            JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager mange = javaCompiler.getStandardFileManager(null,null,null);
            Iterable iterable = mange.getJavaFileObjects(f);
            JavaCompiler.CompilationTask task = javaCompiler.getTask(null,mange,null,null,null,iterable);
            task.call();
            mange.close();

            //4、编译生成的.class文件加载到jvm中
            Class proxyClass = classLoader.findClass("$Proxy0");
            Constructor c = proxyClass.getConstructor(GPInvocationHandler.class);
            //f.delete();

            //5、返回字节码重组的新的代理对象
            return c.newInstance(h);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成.java文件
     * @param interfaces
     * @return
     */
    private static String generateSrc(Class<?>[] interfaces) {
        StringBuffer sb = new StringBuffer();
        sb.append("package com.zw.pattern.proxy.custom;" + ln);
        sb.append("import java.lang.reflect.Method;" + ln);
        sb.append("import com.zw.pattern.proxy.custom.GPPerson;" + ln);
        sb.append("public class $Proxy0 implements " + interfaces[0].getName() + "{" + ln);
        sb.append("GPInvocationHandler h;" + ln);
        sb.append("public $Proxy0(GPInvocationHandler h){" + ln);
        sb.append("this.h = h;" + ln);
        sb.append("}" + ln);

        for(Method m : interfaces[0].getMethods()){
            sb.append("public " + m.getReturnType().getName() + " " + m.getName() + "(){" + ln);
            sb.append("try{" + ln);
            sb.append("Method m = " + interfaces[0].getName() +".class.getMethod(\"" + m.getName() +"\",new Class[]{});" + ln);
            sb.append("this.h.invoke(this,m,null);" + ln);
            sb.append("}catch(Throwable e){" + ln);
            sb.append("e.printStackTrace();" + ln);
            sb.append("}" + ln);
            sb.append("}" + ln);
        }
        sb.append("}" + ln);
        return sb.toString();
    }

}
