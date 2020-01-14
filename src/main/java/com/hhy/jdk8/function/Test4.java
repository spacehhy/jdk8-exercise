package com.hhy.jdk8.function;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

/**
 * PredicateTest
 */
public class Test4 {

    public static void main(String[] args) {
        Predicate<String> predicate = p -> p.length() > 5;

        System.out.println(predicate.test("hello"));
        System.out.println(predicate.test("hello1"));
        System.out.println("---------分割线---------");

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Test4 test = new Test4();

        //传统方式
        test.findAllEvents(list);
        System.out.println("---------");
        //偶数
        test.conditionFilter(list, integer -> integer % 2 == 0);
        System.out.println("---------");
        //奇数
        test.conditionFilter(list, integer -> integer % 2 != 0);
        System.out.println("---------");
        //大于五
        test.conditionFilter(list, integer -> integer > 5);
        System.out.println("---------");
        //小于三
        test.conditionFilter(list, integer -> integer < 3);
        System.out.println("---------");
        //所有全部
        test.conditionFilter(list, integer -> true);
        System.out.println("---------");
        //所有全不
        test.conditionFilter(list, integer -> false);
        System.out.println("---------");

        test.conditionFilter2(list, integer -> integer > 5, integer -> integer % 2 == 0);
        System.out.println("---------");

        System.out.println(test.isEqual(new Date()).test(new Date()));
    }

    public void conditionFilter(List<Integer> list, Predicate<Integer> predicate) {
        for (Integer integer : list) {
            if (predicate.test(integer)) {
                System.out.println(integer);
            }
        }
    }

    public void conditionFilter2(List<Integer> list, Predicate<Integer> predicate,
                                 Predicate<Integer> predicate2) {
        for (Integer integer : list) {
            if (predicate.and(predicate2).negate().test(integer)) {
                System.out.println(integer);
            }
        }
    }

    public Predicate<Date> isEqual(Object object){
        return Predicate.isEqual(object);
    }

    public void findAllEvents(List<Integer> list){
        for (Integer integer : list) {
            if (integer % 2 == 0) {
                System.out.println(integer);
            }
        }
    }
}
