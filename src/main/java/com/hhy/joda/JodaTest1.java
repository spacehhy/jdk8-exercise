package com.hhy.joda;

import org.joda.primitives.list.impl.ArrayIntList;

public class JodaTest1 {

    public static void main(String[] args) {
        ArrayIntList intList = new ArrayIntList();
        intList.add(1);
        intList.add(2);

        intList.forEach(System.out::println);
    }
}
