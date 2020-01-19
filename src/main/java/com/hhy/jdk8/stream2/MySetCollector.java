package com.hhy.jdk8.stream2;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;
import static java.util.stream.Collector.Characteristics.UNORDERED;

/**
 * 3.CustomCollector 自定义收集器
 */
public class MySetCollector<T> implements Collector<T, Set<T>, Set<T>> {

    /* 用于提供一个空的容器供后续的方法调用 */
    @Override
    public Supplier<Set<T>> supplier() {
        System.out.println("supplier invoked!");
        return HashSet<T>::new;
    }

    /* 累加器 BiConsumer<T, U> T为中间结果容器,U为流中遍历的下一个元素 */
    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        System.out.println("accumulator invoked!");
        return Set<T>::add;
    }

    /* 并行流多个线程执行的部分结果合并起来 */
    @Override
    public BinaryOperator<Set<T>> combiner() {
        System.out.println("combiner invoked!");
        return (set1, set2) -> {
            set1.addAll(set2);
            return set1;
        };
    }

    /* 完成器,最后一步执行,把所有结果合并到一起,返回最终结果类型 */
    @Override
    public Function<Set<T>, Set<T>> finisher() {
        System.out.println("finisher invoked!");
//        return t -> t;
//        return Function.identity();
        throw new UnsupportedOperationException();
    }

    /* 特性不可变集合 */
    @Override
    public Set<Characteristics> characteristics() {
        System.out.println("characteristics invoked!");
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, UNORDERED));
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "welcome", "hello");
        Set<String> set = list.stream().collect(new MySetCollector<>());

        System.out.println(set);
        /*
         * characteristics 被调用两次
         * ReferencePipeline -> ReduceOps.makeRef -> getOpFlags {UNORDERED}
         * return collector.characteristics().contains .... {IDENTITY_FINISH}
         * p.s.确定中间结果类型与返回结果类型一致,需要添加IDENTITY_FINISH,需要注意的是确保A和R强制类型转换是成功的
         */
    }
}
