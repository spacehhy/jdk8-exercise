package com.hhy.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * StreamTest
 */
public class Test1 {

    public static void main(String[] args) {
        /* 1.流的创建方式 */
        Stream stream1 = Stream.of("hello", "world", "hello world");

        String[] myArray = new String[]{"hello", "world", "hello world"};
        Stream stream2 = Stream.of(myArray);
        Stream stream3 = Arrays.stream(myArray);

        List<String> list = Arrays.asList(myArray);
        Stream stream4 = list.stream();
        /* 1.流的创建方式 */

        System.out.println("-----分割线-----");

        /* 2.流的简化 */
        IntStream.of(new int[]{5, 6, 7}).forEach(System.out::println);
        System.out.println("-----");

        IntStream.range(3, 8).forEach(System.out::println);//半闭半开
        System.out.println("-----");

        IntStream.rangeClosed(3, 8).forEach(System.out::println);
        /* 2.流的简化 */

        System.out.println("-----分割线-----");

        /* 3.流的进一步应用 */
        /*
        * 1.8之前 传统方式
        * int sum = 0;
        *
        * for(Integer i : list){
        *      sum += 2 * i;
        * }
        *
        * System.out.println(sum)
        */

        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println(list1.stream().map(i -> 2 * i).reduce(0,Integer::sum));
        /* 3.流的进一步应用 */
    }
}
