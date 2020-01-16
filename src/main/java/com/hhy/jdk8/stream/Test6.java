package com.hhy.jdk8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * StreamTest Stream、parallelStream
 */
public class Test6 {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(5000000);

        for (int i = 0; i < 5000000; ++i) {
            list.add(UUID.randomUUID().toString());
        }

        System.out.println("开始排序");

        long startTime = System.nanoTime();

//        list.stream().sorted().collect(Collectors.toList());     //3671 3715
        list.parallelStream().sorted().collect(Collectors.toList());       //1165 1210

        long endTime = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        System.out.println("排序耗时：" + millis);
    }
}
