package com.aaron;

import java.io.*;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader{
    public static void main(String[] args) throws Exception{
        Class<?> clazz = new MyClassLoader().findClass("Hello");
        Object obj = clazz.getDeclaredConstructor().newInstance();
        Method hello = clazz.getDeclaredMethod("hello");
        hello.invoke(obj);
        System.out.println("当前类加载器：" + clazz.getClassLoader());
    }

    @Override
    protected Class<?> findClass(String name) {
        try {
            InputStream is = this.getClass().getResourceAsStream("Hello.xlass");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int b;
            while((b = is.read()) != -1){
                baos.write(b);
            }
            baos.close();
            is.close();
            byte[] bytes = baos.toByteArray();
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte)(255 - bytes[i]);
            }
            return defineClass(name,bytes,0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
