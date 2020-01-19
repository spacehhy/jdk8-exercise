package com.hhy.jdk8.stream2;

import com.hhy.jdk8.stream2.bean.Student;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * 1.collect -> Collector
 */
public class StreamTest1 {

    public static void main(String[] args) {
        Student student1 = new Student("zhangsan", 80);
        Student student2 = new Student("lisi", 90);
        Student student3 = new Student("wangwu", 100);
        Student student4 = new Student("zhaoliu", 90);
        Student student5 = new Student("zhaoliu", 90);

        List<Student> students = Arrays.asList(student1, student2, student3, student4, student5);

        //无意义操作 目的了解collect方法
        List<Student> students1 = students.stream().collect(toList());
        students1.forEach(System.out::println);
        System.out.println("------------");

        System.out.println(students.stream().collect(counting()));
        System.out.println(students.stream().count());
        System.out.println("------------");

        /* collect详解 */
        students.stream().collect(minBy(Comparator.comparingInt(Student::getScore))).ifPresent(System.out::println);
        students.stream().collect(maxBy(Comparator.comparingInt(Student::getScore))).ifPresent(System.out::println);
        System.out.println(students.stream().collect(averagingInt(Student::getScore)));
        System.out.println(students.stream().collect(summingInt(Student::getScore)));

        IntSummaryStatistics summaryStatistics = students.stream().collect(summarizingInt(Student::getScore));
        System.out.println(summaryStatistics);
        System.out.println("------------");

        System.out.println(students.stream().map(Student::getName).collect(joining()));
        System.out.println(students.stream().map(Student::getName).collect(joining(",")));
        System.out.println(students.stream().map(Student::getName).collect(joining(",", "<begin>", "<end>")));
        System.out.println("------------");

        //groupingBy的返回类型为map; Student::getScore 的返回值为key的类型;
        //因此返回值类型应为 map ->key:Integer,value:map ->key:String,value:List<Student>
        Map<Integer, Map<String, List<Student>>> map = students.stream().
                collect(groupingBy(Student::getScore, groupingBy(Student::getName)));
        System.out.println(map);
        System.out.println("------------");

        Map<Boolean, List<Student>> map1 = students.stream().
                collect(partitioningBy(student -> student.getScore() > 80));
        System.out.println(map1);
        System.out.println("------------");

        Map<Boolean, Map<Boolean, List<Student>>> map2 = students.stream().
                collect(partitioningBy(student -> student.getScore() > 80, partitioningBy(student -> student.getScore() > 90)));
        System.out.println(map2);
        System.out.println("------------");

        //逻辑同上 counting的返回类型为Long
        //partitioningBy 返回类型为 map -> key:Boolean,value->Long
        Map<Boolean, Long> map3 = students.stream().
                collect(partitioningBy(student -> student.getScore() > 80, counting()));
        System.out.println(map3);
        System.out.println("------------");

        //collectingAndThen: (收集然后) 转换函数返回的类型 包裹一个收集器，对其结果应用转换函数
        //int i = stream.collect(collectingAndThen(toList(),List :: size);
        // minBy返回Optional 这里是转换为Student
        Map<String, Student> map4 = students.stream().
                collect(groupingBy(Student::getName, collectingAndThen(minBy(Comparator.comparingInt(Student::getScore)),
                        Optional::get)));
        System.out.println(map4);
        System.out.println("------------");
    }
}
