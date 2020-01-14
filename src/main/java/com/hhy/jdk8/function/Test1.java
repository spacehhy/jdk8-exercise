package com.hhy.jdk8.function;

import java.util.function.Function;

/**
 * FunctionTest
 */
public class Test1 {

    public static void main(String[] args) {
        Test1 test = new Test1();

//        System.out.println(test.compute(1, value -> {return 2 * value;}));
//        System.out.println(test.compute(2, value -> 5 + value));
//        System.out.println(test.compute(3, value -> value * value));

//        System.out.println(test.convert(5, value -> String.valueOf(value + "hello world")));
//        //传统Java开发命令式编程,需要预先定义好行为;而java1.8引入function后,行为是在方法使用时,通过方法传递由调用方确定
//        System.out.println(test.method1(2));

        Function<Integer,Integer> function = value -> value * 2;

        System.out.println(test.compute(4, function));

    }

    //值:a;行为:function
    public int compute(int a, Function<Integer, Integer> function) {
        return function.apply(a);
    }

    public String convert(int a, Function<Integer, String> function) {
        return function.apply(a);
    }

    public int method1(int a){
        return 2 * a;
    }

    public int method2(int a){
        return 5 + a;
    }

    public int method3(int a){
        return a * a;
    }
}
