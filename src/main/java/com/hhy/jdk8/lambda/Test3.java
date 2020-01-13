package com.hhy.jdk8.lambda;


/**
 *  FunctionInterfaceTest
 */
public class Test3 {

    public void myTest(MyInterface myInterface){
        System.out.println(1);
        myInterface.test();
        System.out.println(2);
    }

    public static void main(String[] args) {
        Test3 test = new Test3();

        /*
        //jdk1.8之前匿名内部类方式
        test.myTest(new MyInterface() {
            @Override
            public void test() {
                System.out.println("myTest");
            }
        });
        */

        //函数式接口左侧不接收参数,小括号()不可以省略
        test.myTest(() -> {
            System.out.println("myTest");
        });

        System.out.println("----------");

        //MyInterface具体实现的对象
        MyInterface myInterface = () -> {
            System.out.println("hello");
        };

        System.out.println(myInterface.getClass());
        System.out.println(myInterface.getClass().getSuperclass());
        System.out.println(myInterface.getClass().getInterfaces()[0]);
    }
}

@FunctionalInterface
interface MyInterface {

    void test();

    /*
    //Multiple non-overriding abstract methods found in interface com.hhy.jdk8.lambda.MyInterface
    //发现没有重写的多个方法(接口的抽象方法个数多余一个)。
    void test2();

    String myString();
    */

    //除非该方法是重写了根类Object类中的public方法,这种情况是不会讲接口的抽象方法增加个数的。
    String toString();
}
