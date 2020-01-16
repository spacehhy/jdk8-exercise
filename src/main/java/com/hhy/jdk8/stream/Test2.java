package com.hhy.jdk8.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * StreamTest - collect
 */
public class Test2 {

    public static void main(String[] args) {
        Stream<String> stream = Stream.of("hello", "world", "hello world");
        //stream.forEach(System.out::println);
        //String[] stringArray = stream.toArray(length -> new String[length]);
        String[] stringArray = stream.toArray(String[]::new);
        Arrays.asList(stringArray).forEach(System.out::println);

        System.out.println("-------------------");

        Stream<String> stream1 = Stream.of("hello", "world", "hello world");
        /*
        List<String> list = stream1.collect(Collectors.toList());
        List<String> list = stream1.collect(() -> new ArrayList(), (theList,item) -> theList.add(item),
                (leftList,rightList) -> leftList.addAll(rightList));
        */
        //返回linkedList
        LinkedList<Object> list = stream1.collect(LinkedList::new, LinkedList::add, LinkedList::addAll);
        list.forEach(System.out::println);

        System.out.println("-------------------");

        Stream<String> stream2 = Stream.of("hello", "world", "hello world");
        ArrayList<String> arrayList = stream2.collect(Collectors.toCollection(ArrayList::new));
        arrayList.forEach(System.out::println);

        System.out.println("-------------------");

        Stream<String> stream3 = Stream.of("hello", "world", "hello world");
        Set<String> set = stream3.collect(Collectors.toCollection(TreeSet::new));
        set.forEach(System.out::println);
        System.out.println(set.getClass());

        System.out.println("-------------------");

        Stream<String> stream4 = Stream.of("hello", "world", "hello world");
        String str = stream4.collect(Collectors.joining());
        System.out.println(str);
    }
}
