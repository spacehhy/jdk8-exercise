package com.hhy.jdk8.methodreference;

import java.util.Arrays;
import java.util.List;

/**
 * MethodReferenceTest
 */
public class Test1 {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "hello world");
        //Lambda表达式
        //list.forEach(item -> System.out.println(item));
        //方法引用
        list.forEach(System.out::println);
    }
}
