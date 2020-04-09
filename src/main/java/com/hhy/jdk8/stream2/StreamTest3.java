package com.hhy.jdk8.stream2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 7.流源码分析
 */
public class StreamTest3 {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "hello world");

        //System.out.println(list.getClass());
        //1.源码分析 从"stream()"开始
        //list.stream().forEach(System.out::println);

        Stream<String> stream = list.stream();

        System.out.println("1111");

        Stream<String> stream1 = stream.map(item -> item + "_abc");

        System.out.println("2222");

        stream1.forEach(System.out::println);

        System.out.println("------");

        //
        list.stream().forEach(System.out::println);

        System.out.println("------");

        //
        list.forEach(System.out::println);
    }
}
