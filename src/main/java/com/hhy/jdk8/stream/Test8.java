package com.hhy.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * StreamTest flatMap
 */
public class Test8 {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello welcome", "world hello",
                "hello world hello", "hello welcome");

        /*
        * 期望结果:
        * hello
        * world
        * hello world
        */

//        List<String[]> result = list.stream().map(item -> item.split(" ")).distinct().
//                collect(Collectors.toList());
//        result.forEach(item -> Arrays.asList(item).forEach(System.out::println));

        System.out.println("-----------------");

        //Stream<String[]>  -> Stream<String> 需要数组转化为一个字符串流 Arrays的stream方法
        //输入一个T类型数组,返回一个T类型的流 arr -> Arrays.stream(arr)
        //                                 输入            输出
        //<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
        List<String> result1 = list.stream().map(item -> item.split(" ")).flatMap(Arrays::stream).distinct().
                collect(Collectors.toList());
        result1.forEach(System.out::println);

        System.out.println("-----------------");

        List<String> list1 = Arrays.asList("Hi", "Hello", "你好");
        List<String> list2 = Arrays.asList("zhangsan", "lisi", "wangwu", "zhaoliu");

        //                                             list1每项                   list2每项
        //                                                                           参数T    ------函数主题R------
        //                                                参数T    --------------函数主题R-------------------------
        List<String> resultList = list1.stream().flatMap(item1 -> list2.stream().map(item2 -> item1 + " " + item2)).
                collect(Collectors.toList());
        resultList.forEach(System.out::println);

    }
}
