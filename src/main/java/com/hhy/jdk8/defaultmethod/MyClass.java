package com.hhy.jdk8.defaultmethod;

/**
 *  MyClass implements MyInterface1 ==> 输出: MyInterface1
 *  MyClass implements MyInterface1, MyInterface2 ==> 输出: 报错 此时需要显式的重写 myMethod方法
 *  MyClass extends MyInterface1Impl implements MyInterface2 ==> 输出: MyInterface1Impl(extends 亲爹)
 */
public class MyClass extends MyInterface1Impl implements MyInterface2{

    /*
    //同时实现 MyInterface1, MyInterface2 需要显式的重写父类共有方法
    @Override
    public void myMethod() {
        System.out.println("myClass");
        //MyInterface2.super.myMethod();
    }
    */

    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        myClass.myMethod();
    }
}
