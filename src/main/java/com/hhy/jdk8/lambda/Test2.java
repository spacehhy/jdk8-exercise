package com.hhy.jdk8.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 *  TraversalTest
 *  集合遍历中应用Lambda表达式
 */
public class Test2 {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        System.out.println("----------");

        for (Integer i : list) {
            System.out.println(i);
        }

        System.out.println("----------");

        //传统匿名内部类方式
        list.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer i) {
                System.out.println(i);
            }
        });

        System.out.println("----------");

        //jdk1.8 Lambda表达式方式
        //list.forEach((Integer i) -> System.out.println(i));
        list.forEach(i -> System.out.println(i));

        System.out.println("----------");

        //jdk1.8 method reference 方法引用方式
        list.forEach(System.out::println);
    }
}
