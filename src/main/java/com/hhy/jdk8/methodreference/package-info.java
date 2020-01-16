package com.hhy.jdk8.methodreference;

/**
 * 方法引用: method reference
 * 方法引用是实际上Lambda表达式的一个语法糖
 * 当Lambda表达式所能实现的功能,恰巧有一个方法就能表示,实现出来,就可以通过方法引用的方式来去替换
 * 掉Lambda表达式,使代码更加精简。但是存在局限性并不能完全替换所有的Lambda表达式，方法引用是Lambda表达式的特殊情况
 *
 * 我们可以将方法引用看作是一个『函数指针』,function pointer
 *
 * 方法引用:  classname::staticMethod
 * 方法调用:  classname.staticMethod
 * 本质上上述两种调用无任何关系,
 *
 * 方法引用共分为4类:
 *
 * 1. 类名::静态方法名,
 * 2. 引用名(方法名)::实例方法名,
 * 3. 类名::实例方法名,
 * [一定是(sort)方法的Lambda表达式的第一个参数,来去调用的实例方法,
 * 如果接收多个参数,那么除了第一个参数外的后续所有参数都作为实例方法的参数传递进去]
 * 4. 构造方法引用: 类名::new
 *
 */