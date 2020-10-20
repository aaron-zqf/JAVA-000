package com.aaron;

import java.io.*;

public class MyClassLoader extends ClassLoader{

    public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException {
        MyClassLoader classLoader = new MyClassLoader();
        classLoader.loadClass("D://Hello.xlass");
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] bytes = new byte[0];
        try {
            bytes = loadClassData(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name,bytes,0, bytes.length);
    }

    public byte[] loadClassData(String name) throws IOException {
        FileInputStream is = new FileInputStream(new File(name));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b;

        while((b = is.read()) != -1){
            baos.write(b);
        }
        return baos.toByteArray();
    }
}
