package com.hhy.jdk8.stream2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 7.流(Stream)源码分析
 */
public class StreamTest3 {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "hello world");

        //源码分析 从"stream()"开始  stream() forEach()
        //1.分析入口1: stream()
        Stream<String> stream = list.stream();
        //2.forEach() 实现有两个 Head in ReferencePipeline.forEach() 和 ReferencePipeline.forEach()
        //List<String> list = Arrays.asList("hello", "world", "hello world","welcome","person","student");
        //list.stream().parallel().forEach(System.out::println);//非顺序执行 每次结果执行顺序位置
        //list.stream().forEach(System.out::println);//Head in ReferencePipeline.forEach()
        //list.stream().map(item -> item).forEach(System.out::println);//ReferencePipeline.forEach()

        //IteratorSpliterator.forEachRemaining(Consumer<? super T> action)处打断点,发现程序不走该方法
        //System.out.println(list.getClass()); //java.util.Arrays$ArrayList
        //list.stream().forEach(System.out::println);

        // stream() -> StreamSupport.stream(spliterator(), false); ->
        // default Spliterator<E> spliterator() {return Spliterators.spliterator(this, 0);} ->
        // ArrayList in Arrays #spliterator() ->
        // Spliterators#spliterator return new ArraySpliterator<>(Objects.requireNonNull(array),additionalCharacteristics);
        //
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
