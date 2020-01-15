package com.hhy.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * StreamTest
 */
public class Test8 {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello welcome", "world hello",
                "hello world hello", "hello welcome");

        List<String[]> result = list.stream().map(item -> item.split(" ")).distinct().
                collect(Collectors.toList());
        result.forEach(item -> Arrays.asList(item).forEach(System.out::println));

        System.out.println("-----------------");

        List<String> result1 = list.stream().map(item -> item.split(" ")).flatMap(Arrays::stream).distinct().
                collect(Collectors.toList());
        result1.forEach(System.out::println);

        System.out.println("-----------------");

        List<String> list1 = Arrays.asList("Hi", "Hello", "你好");
        List<String> list2 = Arrays.asList("zhangsan", "lisi", "wangwu", "zhaoliu");

        List<String> resultList = list1.stream().flatMap(item1 -> list2.stream().map(item2 -> item1 + " " + item2)).
                collect(Collectors.toList());
        resultList.forEach(System.out::println);

    }
}
