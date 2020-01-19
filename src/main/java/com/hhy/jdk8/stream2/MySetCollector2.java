package com.hhy.jdk8.stream2;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 4.CustomCollector 自定义收集器2
 * 输入: Set<String>
 * 输出: Map<String,String>
 *
 * 示例输入: ["hello","world","hello world"]
 * 示例输出: {hello:hello, world:world,hello world:hello world}
 *
 */
public class MySetCollector2<T> implements Collector<T, Set<T>, Map<T, T>> {

    @Override
    public Supplier<Set<T>> supplier() {
        System.out.println("supplier invoked!");
//        return HashSet<T>::new;

        /**
         *     A a1 = supplier.get();
         *     accumulator.accept(a1, t1);
         *     accumulator.accept(a1, t2);
         *     R r1 = finisher.apply(a1);  // result without splitting
         *
         *     A a2 = supplier.get();
         *     accumulator.accept(a2, t1);
         *     A a3 = supplier.get();
         *     accumulator.accept(a3, t2);
         *     R r2 = finisher.apply(combiner.apply(a2, a3));  // result with splitting
         *
         */
        return () -> {
            //串行流 只创建一个结果容器,并行流 CONCURRENT只创建一个结果容器;非CONCURRENT每个线程创建一个结果容器
            System.out.println("-----------");
            return new HashSet<T>();
        };
    }

    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        System.out.println("accumulator invoked!");

        return (set, item) -> {
            System.out.println("accumulator: " + set  + ", " + Thread.currentThread().getName());
            set.add(item);
        };
    }

    @Override
    public BinaryOperator<Set<T>> combiner() {
        System.out.println("combiner invoked!");

        return (set1,set2) -> {
            System.out.println("set1: " + set1);
            System.out.println("set2: " + set2);
            set1.addAll(set2);
            return set1;
        };
    }

    @Override
    public Function<Set<T>, Map<T, T>> finisher() {
        System.out.println("finisher invoked!");

        return set -> {
            Map<T, T> map = new TreeMap<>();
            set.stream().forEach(item -> map.put(item, item));
            return map;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        System.out.println("characteristics invoked!");
        //特性集合加入 IDENTITY_FINISH 不会调用finisher直接执行强制转换
        //造成ClassCastException: java.util.HashSet cannot be cast to java.util.Map
        //return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED,Characteristics.IDENTITY_FINISH));
        //CONCURRENT: java.util.ConcurrentModificationException [一个线程修改集合,一个线程遍历集合造成并发修改异常]
        //不输出combiner方法里lambda表达式中代码,证明操作的是同一容器
        //return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED,Characteristics.CONCURRENT));
        //CONCURRENT 多个线程同时操作结果容器 如果不加CONCURRENT表示多个线程操作多个结果容器
        //如果特性集合中包含CONCURRENT,那么就不要再累加器accumulator,不要做额外操作,即添加又遍历
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED));
    }

    public static void main(String[] args) {

        //当前系统线程数
        System.out.println(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < 1; ++i) {
            List<String> list = Arrays.asList("hello", "world", "welcome", "hello", "a", "b", "c", "d", "e", "f", "g");
            Set<String> set = new HashSet<>();
            set.addAll(list);

            System.out.println("set: " + set);

            //Map<String, String> map = set.stream().collect(new MySetCollector2<>());
            //Map<String, String> map = set.stream().sequential().collect(new MySetCollector2<>());
            //Map<String, String> map = set.stream().parallel().collect(new MySetCollector2<>());
            Map<String, String> map = set.parallelStream().collect(new MySetCollector2<>());

            System.out.println(map);
        }
    }
}
