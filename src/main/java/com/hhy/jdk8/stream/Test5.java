package com.hhy.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * StreamTest - terminal operation、distinct
 */
public class Test5 {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "hello world");

//        list.stream().map(item -> item.substring(0, 1).toUpperCase() + item.substring(1)).forEach(System.out::println);

        //无任何输出,无结束操作 terminal operation
        list.stream().map(item -> {
            String result = item.substring(0,1).toUpperCase() + item.substring(1);
            System.out.println("test");
            return result;
        });

        System.out.println("-------------------");

        //注意流使用的顺序,是否会生成一个无限循环的流
//        IntStream.iterate(0, i -> (i + 1) % 2).distinct().limit(6).forEach(System.out::println);
        IntStream.iterate(0, i -> (i + 1) % 2).limit(6).distinct().forEach(System.out::println);

    }
}
