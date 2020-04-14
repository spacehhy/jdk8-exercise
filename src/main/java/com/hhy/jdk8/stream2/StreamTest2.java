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

        //单独定义异常 这种情况将不会见到压制异常
        //除非剩余的异常[被传递给调用者之外的其他异常]与第一个异常时同一个异常,那么将不会作为压制异常,
        //因为一个异常时可能压制自己的
        NullPointerException nullPointerException = new NullPointerException("my exception");

        //2.使用try with resource 方法调用流的close方法: 输出: hello world hello world aaa bbb 按照关闭处理器添加顺序执行
        try (Stream<String> stream = list.stream()){
            stream.onClose(() -> {//关闭处理器
                System.out.println("aaa");
                //(1)某一关闭处理器发生异常,不会影响其他关闭处理器运行 输出: hello Exception world hello word aaa bbb
                //throw new NullPointerException("first exception");
                //(3)只会抛出一个异常,并不会抛出压制异常 [抛出相同异常]
                //throw nullPointerException;
                //(4)[两个异常对象]
                throw new NullPointerException("first exception");
            }).onClose(() -> {//关闭处理器
                System.out.println("bbb");
                //(2)第二个关闭处理器抛出参数异常 压制异常体现
                //输出: Suppressed:java.lang.ArithmeticException: second exception
                //压制异常,两个关闭处理器本身独立,并不是调用堆栈的关系,并不是前面的方法调动后面的方法
                //因此会把第一个异常返回给调用者,后续异常集中化做一个Suppressed压制
                //throw new ArithmeticException("second exception");
                //(3)文档:除非剩余的异常与第一个异常是同一个异常(对象),就不回作为被压制异常,因为异常不会压制自己 [抛出相同异常]
                //throw nullPointerException;
                //(4)[两个异常对象 压制异常]
                throw new NullPointerException("second exception");
            }).forEach(System.out::println);//输出流内元素
        }
    }
}
