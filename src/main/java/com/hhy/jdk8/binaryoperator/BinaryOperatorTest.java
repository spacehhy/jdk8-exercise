package com.hhy.jdk8.binaryoperator;

import java.util.Comparator;
import java.util.function.BinaryOperator;

/**
 * BinaryOperatorTest
 */
public class BinaryOperatorTest {

    public static void main(String[] args) {
        BinaryOperatorTest binaryOperatorTest = new BinaryOperatorTest();

        System.out.println(binaryOperatorTest.compute(1, 2, (a,b) -> a+b));
        System.out.println(binaryOperatorTest.compute(1, 2, (a,b) -> a-b));

        System.out.println("----------");

        System.out.println(binaryOperatorTest.getSort("hello123", "world", (a,b) -> a.length() - b.length()));
        System.out.println(binaryOperatorTest.getSort("hello123", "world", (a,b) -> a.charAt(0) - b.charAt(0)));

        //System.out.println(binaryOperatorTest.getSort("hello123", "world", Comparator.comparingInt(String::length)));
        //System.out.println(binaryOperatorTest.getSort("hello123", "world", Comparator.comparingInt(a -> a.charAt(0))));
    }

    public int compute(int a, int b, BinaryOperator<Integer> binaryOperator) {
        return binaryOperator.apply(a, b);
    }

    public String getSort(String a, String b, Comparator<String> comparator) {
        return BinaryOperator.maxBy(comparator).apply(a, b);
    }
}
