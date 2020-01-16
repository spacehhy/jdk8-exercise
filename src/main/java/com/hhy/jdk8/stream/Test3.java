package com.hhy.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * StreamTest - map
 */
public class Test3 {

    public static void main(String[] args) {
        //map
        List<String> list = Arrays.asList("hello", "world", "hello world", "test");
        list.stream().map(String::toUpperCase).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("----------");

        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        list1.stream().map(item -> item * item).forEach(System.out::println);

        System.out.println("----------");

        //flatMap 扁平 返回一个包含替换掉流中每一个元素(即把所有包含在容器内的元素取出,重新放入新的容器内)
        Stream<List<Integer>> listStream = Stream.of(Arrays.asList(1),
                Arrays.asList(2, 3), Arrays.asList(4, 5, 6));

        //<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
        //<R> Stream<R> map(Function<? super T, ? extends R> mapper);
        //flatMap接收的是一个将集合转化成的流 而map接收的是一个结果类型
        listStream.flatMap(theList -> theList.stream()).map(item -> item * item).forEach(System.out::println);
    }
}
