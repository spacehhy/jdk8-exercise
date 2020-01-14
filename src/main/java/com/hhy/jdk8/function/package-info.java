package com.hhy.jdk8.function;

/**
 * 函数式编程: write less do more 写的更少,做的更多
 *
 * 高阶函数:
 * 如果一个函数接收一个函数作为参数,或者返回一个函数作为返回值,那么该函数就叫做高阶函数。
 *
 * Function:
 * Represents a function that accepts one argument and produces a result.
 * 表示一个函数接受一个参数产生一个结果
 * 抽象方法:
 * R apply(T t);
 * Applies this function to the given argument.
 * 将此函数应用于给定参数。
 *
 * compose:(组合)
 * 先执行compose方法内行为的apply,再将执行结果执行调用行为的apply方法
 *
 * andThen:(接着,并且然后)
 * 先执行调用者行为的apply方法,再将执行结果执行andThen方法内行为的apply
 *
 * identity:(同一性)
 * Returns a function that always returns its input argument
 * 始终返回其输入参数的函数
 *
 * BiFunction:(Bidirectional Function)
 * Represents a function that accepts two arguments and produces a result.
 * 表示一个函数接收两个参数产生一个结果
 * This is the two-arity specialization of {@link java.util.function.Function}
 * 这是Function接口两个参数的特化形式
 *
 * R apply(T t, U u);
 *
 * andThen:
 * 先执行调用者行为的apply方法,(BiFunction接收两个参数执行apply方法返回一个参数)
 * 再将执行结果执行andThen方法内行为的apply(执行结果的一个参数作为Function的参数执行apply方法)
 * 因此compose不能存在的原因:
 * 如果存在该方法,参数一定为BiFunction先执行BiFunction的两个参数,返回一个结果,此时仍需将一个结果执行到BiFunction上,此时是无法实现的
 *
 *
 * Predicate:(谓语)
 * Represents a predicate (boolean-valued function) of one argument
 * 表示一个判断一个参数布尔值的函数
 * boolean test(T t);
 *
 * and(与) 调用and的predicate对象结果为false,and后面的predicate不进行运算
 *
 * or(或) 调用or的predicate对象结果为true,or后面的predicate不进行运算
 *
 * negate(非) 当前predicate对象逻辑结果取反
 *
 * isEqual[静态方法]
 * Returns a predicate that tests if two arguments are equal according to {@link java.util.Objects#equals(Object, Object)}.
 * 返回一个predicate,用来测试两个参数是否相等(equal)根据Objects类的equals方法
 *
 *
 * Consumer: 消费者,给出一个动作,执行该动作,不返回参数
 * Supplier: 提供者,
 *
 *
 *
 *
 */