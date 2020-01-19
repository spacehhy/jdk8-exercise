package com.hhy.jdk8.stream2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 2.Comparator:[比较器]
 * 比较器的串联: myComparator.thenComparing(...).thenComparing(...)....
 */
public class MyComparatorTest {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("nihao", "hello", "world", "welcome");

//        Collections.sort(list);
//        Collections.sort(list, (item1,item2) -> item1.length() - item2.length());
//        Collections.sort(list, (item1,item2) -> item2.length() - item1.length());

//        Collections.sort(list, Comparator.comparingInt(String::length));
//        Collections.sort(list, Comparator.comparingInt(String::length).reversed());

        /* Lambda表示的自动推断,当无法推断出参数类型时,需要显式的指出参数的具体类型
         * 这里需要添加参数类型 String;否则认为类型为Object
         * 因为comparingInt需要的参数ToIntFunction<? super T> ; 参数类型为T或T的父类,所以使用子类的特有方法时需显式指出
         * T implements A,B,C;比较的是T类型本身,但是结果可以对应更宽泛的类型
         * Collections.sort(list, Comparator.comparingInt((Object item) -> 1));//可以
         * Collections.sort(list, Comparator.comparingInt((Boolean item) -> 1));//Boolean 不是String的父类,顾类型错误
         * 加入reversed 影响比较器的类型推断,离上下文更远的类型推断是推断出来的
         */
//        Collections.sort(list, Comparator.comparingInt((String item) -> item.length()));
//        Collections.sort(list, Comparator.comparingInt((String item) -> item.length()).reversed());

//        list.sort(Comparator.comparingInt(String::length).reversed());
        //此处Lambda参数类型不可省略,编译器无法推断出参数类型
//        list.sort(Comparator.comparingInt((String item) -> item.length()).reversed());
        /* thenComparing 此触发器触发条件为,前一个比较器比较的结果相同时,才触发thenComparing
         * CASE_INSENSITIVE_ORDER 不区分大小写
         */
//        Collections.sort(list, Comparator.comparingInt(String::length).thenComparing(String.CASE_INSENSITIVE_ORDER));

//        Collections.sort(list, Comparator.comparingInt(String::length).
//                thenComparing((item1,item2) -> item1.toLowerCase().compareTo(item2.toLowerCase())));

//        Collections.sort(list, Comparator.comparingInt(String::length).
//                thenComparing(Comparator.comparing(String::toLowerCase)));

//        Collections.sort(list, Comparator.comparingInt(String::length).
//                thenComparing(Comparator.comparing(String::toLowerCase,Comparator.reverseOrder())));

//        Collections.sort(list, Comparator.comparingInt(String::length).reversed().
//                thenComparing(Comparator.comparing(String::toLowerCase,Comparator.reverseOrder())));

        //第二个thenComparing 为起到作用根据thenComparing的特点前一个比较器比较的值为0时才会被调用,
        //因此如果想再反转直接调用reverseOrder
        Collections.sort(list, Comparator.comparingInt(String::length).reversed().
                thenComparing(Comparator.comparing(String::toLowerCase,Comparator.reverseOrder())).
                thenComparing(Comparator.reverseOrder()));

        //list.forEach(System.out::println);
        System.out.println(list);
    }
}
