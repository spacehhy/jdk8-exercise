package com.hhy.jdk8.stream2;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * 8.解决Consumer与IntConsumer是如何类型转换的问题
 */
public class ConsumerTest {

    public void test(Consumer<Integer> consumer) {
        consumer.accept(100);
    }

    public static void main(String[] args) {
        ConsumerTest consumerTest = new ConsumerTest();

        //定义lambda表达式,让表达式既可以赋值给Consumer对相关,又可以赋值给IntConsumer对象
        Consumer<Integer> consumer = i -> System.out.println(i);
        IntConsumer intConsumer = i -> System.out.println(i);

        System.out.println(consumer instanceof Consumer);
        System.out.println(intConsumer instanceof IntConsumer);

        //将两个引用传递给test定义的方法上
        consumerTest.test(consumer);//面向对象方式
        //consumerTest.test(intConsumer);//类型不兼容
        //consumerTest.test((Consumer) intConsumer);//编译不报错,运行报错,lambda表达式不能被转化成Consumer对象

        //传递行为
        consumerTest.test(consumer::accept);//函数式方式  consumer::accept === i -> System.out.println(i)
        consumerTest.test(intConsumer::accept);//函数式方式 intConsumer::accept === i -> System.out.println(i);
    }
}
