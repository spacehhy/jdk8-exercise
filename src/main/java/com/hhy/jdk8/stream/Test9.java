package com.hhy.jdk8.stream;

import com.hhy.jdk8.stream.bean.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * StreamTest 分组、分区
 */
public class Test9 {
    public static void main(String[] args) {
        Student student1 = new Student("zhangsan", 100, 20);
        Student student2 = new Student("lisi", 90, 20);
        Student student3 = new Student("wangwu", 90, 30);
        Student student4 = new Student("zhangsan", 80, 40);

        List<Student> students = Arrays.asList(student1, student2, student3, student4);

        // select * from student group by name
        /*
        * result:  Map<String, List<Student>>
        * 传统java方式
        * 1.循环列表
        * 2.取出学生的名字
        * 3.检查map中是否存在该名字,不存在直接添加到map中;存在则将map中的List对象取出,然后将该Student对象添加到List中
        * 4.返回map对象
        */

        /* 分组:  group by */
        //classifier 分类器
        Map<String, List<Student>> map1 = students.stream().
                collect(Collectors.groupingBy(Student::getName));
        System.out.println(map1);
        System.out.println("---------------------");

        Map<Integer, List<Student>> map2 = students.stream().
                collect(Collectors.groupingBy(Student::getAge));
        System.out.println(map2);
        System.out.println("---------------------");

        //select name,count(*) from student group by name;
        Map<String, Long> map3 = students.stream().
                collect(Collectors.groupingBy(Student::getName, Collectors.counting()));
        System.out.println(map3);
        System.out.println("---------------------");

        Map<String, Double> map4 = students.stream().
                collect(Collectors.groupingBy(Student::getName, Collectors.averagingDouble(Student::getScore)));
        System.out.println(map4);
        System.out.println("---------------------");

        /* 分区:  partition by (分组特殊情况,只会有两个分区)*/
        Map<Boolean, List<Student>> map5 = students.stream().
                collect(Collectors.partitioningBy(student -> student.getScore() >= 90));
        System.out.println(map5);
        System.out.println("---------------------");
    }
}
