package com.hhy.jdk8.stream2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 6.关闭处理器:当close方法被执行时,关闭处理器才会被调用
 */
public class StreamTest2 {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "hello world");

        //1.close方法未被执行,关闭处理器不会被调用, "aaa","bbb"不会输出
        list.stream().onClose(() ->{
            System.out.println("aaa");
        }).onClose(() ->{
            System.out.println("bbb");
        }).forEach(System.out::println);

        //2.try with resource: 输出: hello world hello world aaa bbb 按照关闭处理器添加顺序执行
        NullPointerException nullPointerException = new NullPointerException("my exception");

        try (Stream<String> stream = list.stream()){
            stream.onClose(() -> {
                System.out.println("aaa");
                //(1)某一关闭处理器发生异常,不会影响其他关闭处理器 输出: hello Exception world hello word aaa bbb
                //throw new NullPointerException("first exception");
                //(3)只会抛出一个异常,并不会抛出压制异常
                //throw nullPointerException;
                throw new NullPointerException("first exception");
            }).onClose(() -> {
                System.out.println("bbb");
                //(2)压制异常 输出: Suppressed:java.lang.ArithmeticException: second exception
                //throw new ArithmeticException("second exception");
                //(3)文档:除非剩余的异常与第一个异常是同一个异常(对象),就不回作为被压制异常,因为异常不会压制自己
                //throw nullPointerException;
                throw new NullPointerException("second exception");
            }).forEach(System.out::println);
        }
    }
}
