package com.hhy.jdk8.supplier;

import com.hhy.jdk8.supplier.bean.Student;

import java.util.function.Supplier;

/**
 * SupplierTest
 */
public class SupplierTest {

    public static void main(String[] args) {
        //不接收参数返回一个结果 [主要用于工厂]
        Supplier<String> supplier = () -> "hello world";
        System.out.println(supplier.get());

        System.out.println("------------------");

        //使用空参构造 不接受参数 返回一个对象
        Supplier<Student> supplier1 = () -> new Student();
        System.out.println(supplier1.get().getName());

        System.out.println("-------");

        Supplier<Student> supplier2 = Student::new;
        System.out.println(supplier2.get().getAge());

        /*如果构造方法引用不提供相应的构造(如空参构造),编译报错*/
    }
}
