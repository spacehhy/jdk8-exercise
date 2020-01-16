package com.hhy.jdk8.methodreference;

import java.util.Arrays;
import java.util.List;

/**
 * methodReference 方法引用demo
 */
public class MyConsumer {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "c");
        //Lambda表达式方式:
        list.forEach(item -> MyConsumer.print(item));
        //方法引用: 类名::静态方法
        list.forEach(MyConsumer::print);

        System.out.println();
        System.out.println("--------------------");

        new MyConsumer().start();
    }

    public void start() {
        List<String> list = Arrays.asList("a", "b", "c");
        //Lambda表达式方式:
        list.forEach(item -> new MyConsumer().println(item));
        //方法引用: 对象::成员方法
        list.forEach(new MyConsumer()::println);
    }

    public static void print(String str){
        System.out.print(str);
    }

    public void println(String str){
        System.out.println(str);
    }
}
