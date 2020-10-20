package com.aaron;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        String str = new String("hello");
        System.out.println(str);

        for (int i = 0; i < 10000; i++){
            String s = new String();
            System.out.println(s);
        }

        Thread.sleep(99999999999L);
    }
}
