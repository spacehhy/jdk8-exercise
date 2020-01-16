package com.hhy.jdk8.stream;

import java.util.Arrays;
import java.util.List;

/**
 * StreamTest
 */
public class Test7 {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "hello world");

//        list.stream().mapToInt(item -> item.length()).filter(length -> length == 5).findFirst().ifPresent(System.out::println);

        list.stream().mapToInt(item -> {
            int length = item.length();
            System.out.println(item);
            return length;
        }).filter(length -> length == 5).findFirst().ifPresent(System.out::println);

        /*
        * result
        * hello
        * 5
        * 流存在短路运算(重点)
        * 第一个元素应用了所有(短路)操作
        * 当执行findFirst操作时,执行符合条件后下一元素就不再重新执行操作了
        */
    }
}
