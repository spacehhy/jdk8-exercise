package com.hhy.jdk8.stream;

import java.util.IntSummaryStatistics;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * StreamTest - generate、iterate
 */
public class Test4 {

    public static void main(String[] args) {
//        Stream<String> stream = Stream.generate(UUID.randomUUID()::toString);
//        stream.findFirst().ifPresent(System.out::println);

//        Stream.iterate(1, item -> item + 2).forEach(System.out::println);//此时创建的stream是无限循环的
        Stream<Integer> iterate = Stream.iterate(1, item -> item + 2).limit(6);
//        System.out.println(iterate.filter(item -> item > 22).mapToInt(item -> item * 2).skip(2).limit(2).sum());
//        iterate.filter(item -> item > 2).mapToInt(item -> item * 2).skip(2).limit(2).min().ifPresent(System.out::println);
//        iterate.filter(item -> item > 200).mapToInt(item -> item * 2).skip(2).limit(2).max().ifPresent(System.out::println);

//        IntSummaryStatistics summaryStatistics = iterate.filter(item -> item > 2).
//                mapToInt(item -> item * 2).skip(2).limit(2).summaryStatistics();
//
//        System.out.println(summaryStatistics.getMin());
//        System.out.println(summaryStatistics.getMax());
//        System.out.println(summaryStatistics.getCount());

        //exception java.lang.IllegalStateException: stream has already been operated upon or closed
        /*System.out.println(iterate);
        System.out.println(iterate.filter(item -> item > 2));
        /此时流已操作完
        System.out.println(iterate.distinct());*/


        System.out.println(iterate);
        Stream<Integer> stream2 = iterate.filter(item -> item > 2);
        System.out.println(stream2);
        Stream<Integer> stream3 = stream2.distinct();
        System.out.println(stream3);
    }
}
