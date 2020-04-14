package com.hhy.jdk8.stream2;

/**
 * 9.Lambda表达式创建的对象与匿名内部类创建的对象的不同
 * Lambda表达式看似是匿名内部类的替代品,实际上有本质上的不同区别
 * 匿名内部类实在内部类里开辟出一块新的作用域,与外层类不是同一个作用域
 * Lambda表达式并没有开辟新的作用域,与外层作用域是一个作用域
 * Lambda表达式并不是匿名内部类的语法糖,内容表现形式,本质上就不是一回事
 * 只不过可以完成同样的一些工作,从实现和编码角度Lambda表达式比匿名内部类更简洁一些,
 */
public class LambdaTest {

    //LambdaTest@1f6f913d
    //Lambda表达式的this表示当前类的对象
    Runnable r1 = () -> System.out.println(this);

    //LambdaTest$1@250f5d9
    //匿名内部类的this表示匿名内部类所对应的对象
    //匿名类编译时生成的类名为 外层public类 + $ + 顺序 的class文件 例如LambdaTest$1.class
    //匿名内部类如何表示, 外层public类 + $ + 顺序
    Runnable r2 = new Runnable() {
        @Override
        public void run() {
            System.out.println(this);
        }
    };

    public static void main(String[] args) {
        LambdaTest lambdaTest = new LambdaTest();

        Thread t1 = new Thread(lambdaTest.r1);
        t1.start();

        System.out.println("--------");

        Thread t2 = new Thread(lambdaTest.r2);
        t2.start();
    }
}