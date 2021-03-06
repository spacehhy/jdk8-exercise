package com.hhy.jdk8.lambda;

/**
 * lambda表达式
 * lambda表达式是将函数作为参数进行传递，从而实现了函数式编程（函数式编程在js中广泛应用）
 * Runnable runnable2 = ()->{
 *         System.out.println("runnable...");
 *         System.out.println("run....");
 * };
 *
 * 其中 () 代表参数， {}代表函数主体。表现为 (Type1 parameter1,Type2 parameter2........)->{statement1;statement2;......}
 * 什么情况适用于lambda表达式呢？答案是 ：能够接收Lambda表达式的参数类型，是一个只包含一个方法的接口，称为函数式接口，
 * 为此jdk1.8推出了一种标记注解@FunctionalInterface
 *
 *
 *
 * 传统匿名内部类方式创建对象:
 * a.execute(callback(event){
 *      event.......
 * });
 *
 * Lambda表达式的基本结构:
 * 左侧参数(param) -> 右侧体(body)
 * (param1,param2,param3) -> {
 *
 * }
 *
 * 函数式接口:
 * 1.注解 @FunctionalInterface
 * 一个接口有且只有一个抽象方法,就称之为函数式接口;
 * java8开始引入 default 关键字,接口中可以存在具体实现的方法
 *
 * <p>Note that instances of functional interfaces can be created with
 * lambda expressions, method references, or constructor references.
 * 注意函数式函数式接口的实例可以通过Lambda表达式,方法引用或者构造方法引用创建!
 *
 * 关于函数式接口:
 * 1.如果一个接口只有一个抽象方法,那么该接口就是一个函数式接口。
 * 2.如果我们在某个接口上声明了FunctionalInterface注解,那么编译器就会按照函数式接口的定义来要求该接口。
 * 3.如果某个接口只有一个抽象方法,但我们并没有给该接口声明FunctionalInterface注解,那么编译器依旧会将该接口看作是函数式接口。
 * java8开始接口中除了可以存在默认方法（default）还可以存在静态方法（static）
 *
 * 为何需要Lambda表达式:
 * 在Java中,我们无法将函数作为参数传递给一个方法,也无法声明返回一个函数。
 * 在JavaScript中，函数参数是一个函数，返回值另一个函数的情况是非常常见的；JavaScript是一门非常典型的函数式语言。
 *
 * Lambda表达式作用：
 * Lambda表达式为Java添加了缺失的函数式编程特性，使我们能将函数当做一等公民看待
 * 在将函数作为一等公民的语言中，Lambda表达式的类型是函数。但在Java中，Lambda表达式是对象，他们必须依附于一类特别的对象类型————函数式接口（functional interface）
 *
 * 外部迭代：一定存在一个迭代器，开始指向1，取出1.迭代器指向下一个位置，以此类推，指到最后一个元素，发现无下一个元素，迭代结束。
 * 内部迭代：不依赖于迭代器，通过集合本身与Lambda表达式接口，把元素一个一个取出来。
 * 方法引用：System.out::println；方法引用的方式创建一个函数式接口的实例。
 *
 *
 * Stream.xxx.yyy.zzz
 * Pipeline
 *
 * Java Lambda概要:
 * Java Lambda表达式是一种匿名函数;它是没有声明的方法,即没有访问修饰符、返回值声明和名字。
 * Lambda表达式的作用：
 * *** 传递行为，而不仅仅是值 ***
 * 提升抽象层次
 * API重用性更好
 * 更加灵活
 * Lambda表达式的基本语法：
 * Java中的Lambda表达式基本语法:
 * (argument) -> {body}
 * 比如说:
 * (arg1,arg2...) -> {body}
 * (type1 arg1,type2 arg2) -> {body}
 * Lambda示例说明
 * (int a,int b) -> {return a+b;}
 * () -> System.out.println("Hello World")
 * (String s) -> {System.out.println(s);}
 * () -> 42
 * () -> {return 3.1415};
 * Java Lambda结构
 * 一个Lambda表达式可以有零个或多个参数
 * 参数的类型既可以明确声明,也可以根据上下文来推断。例如：（int a）与（a）效果相同
 * 所有参数需包含在圆括号内,参数之间用逗号相隔。例如:（a,b）或(int a,int b) 或 (String a,int b,float c)
 * 空圆括号代表参数集为空。例如：（）-> 42
 * 当只有一个参数,且其类型可推导时,圆括号()可省略。例如: a -> return a*a
 * Lambda表达式的主体可包含零条或多条语句
 * 如果Lambda表达式的主体只有一条语句,花括号{}可省略。匿名函数的返回类型与该主体表达式一致
 * 如果Lambda表达式的主体包含一条以上语句,则表达式必须包含在花括号{}中(形成代码块)。匿名函数的返类型与代码块的返回类型一致,若没有返回则为空
 *
 * 函数式接口:
 * 函数式接口是只包含一个抽象方法声明的接口
 * java.lang.Runnable接口只声明了一个方法 void run()
 * 每个Lambda表达式都能隐式地赋值给函数式接口
 * FunctionalInterface
 * java.lang.FunctionalInterface
 * 标识所声明的接口为函数式接口
 * 如果不满足函数式接口的要求,则编译器报错
 * 并非必须,但凡满足函数式接口条件的接口,编译器均将其看作是函数式接口,即便没有添加FunctionalInterface注解亦如此
 *
 * 何为传递行为
 *
 */