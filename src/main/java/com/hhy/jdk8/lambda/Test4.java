package com.hhy.jdk8.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *  FunctionInterfaceTest
 */
public class Test4 {

    public static void main(String[] args) {
        /*
        FunctionInterface1 interface1 = () -> {};
        System.out.println(interface1.getClass().getInterfaces()[0]);

        FunctionInterface2 interface2 = () -> {};
        System.out.println(interface2.getClass().getInterfaces()[0]);

        new Thread(()-> System.out.println("hello world")).start();
        */

        List<String> list = Arrays.asList("hello","world","hello world");

        //list.forEach(item -> System.out.println(item.toUpperCase()));

        List<String> list2 = new ArrayList<>();//diamond 菱形语法

        //list.forEach(item -> list2.add(item.toUpperCase()));
        //list2.forEach(item -> System.out.println(item));

        //list.stream().map(item -> item.toUpperCase()).forEach(item -> System.out.println(item));

        //list.stream().map(String::toUpperCase).forEach(System.out::println);

        //一定存在一个调用toUpperCase方法的字符串对象,作为执行toUpperCase方法的第一个参数
        //Function<T, R> -----> R apply(T t); 调用toUpperCase方法的对象为第一个参数T t;返回值为R
        //Function<String, String> function = String::toUpperCase;
        //System.out.println(function.getClass().getInterfaces()[0]);
    }
}

@FunctionalInterface
interface FunctionInterface1 {
    void method1();
}

@FunctionalInterface
interface FunctionInterface2 {
    void method2();
}
