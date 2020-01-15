package com.hhy.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * StreamTest
 */
public class Test3 {

    public static void main(String[] args) {
        //map
        List<String> list = Arrays.asList("hello", "world", "hello world", "test");
        list.stream().map(String::new).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("----------");

        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        list1.stream().map(item -> item * item).forEach(System.out::println);

        System.out.println("----------");

        //flatMap
        Stream<List<Integer>> listStream = Stream.of(Arrays.asList(1),
                Arrays.asList(2, 3), Arrays.asList(4, 5, 6));

        listStream.flatMap(theList -> theList.stream().map(item -> item * item)).forEach(System.out::println);
    }
}
