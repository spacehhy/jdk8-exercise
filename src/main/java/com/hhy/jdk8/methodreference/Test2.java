package com.hhy.jdk8.methodreference;

import com.hhy.jdk8.methodreference.bean.Student;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * MethodReferenceTest
 */
public class Test2 {

    public String getString(Supplier<String> supplier) {
        return supplier.get() + "test";
    }

    public String getString2(String str, Function<String, String> function) {
        return function.apply(str);
    }

    public static void main(String[] args) {
        Student student1 = new Student("zhangsan", 10);
        Student student2 = new Student("lisi", 90);
        Student student3 = new Student("wangwu", 50);
        Student student4 = new Student("zhaoliu", 40);

        List<Student> students = Arrays.asList(student1, student2, student3, student4);

        //Lambda表达式
        students.sort((studentParam1, studentParam2) ->
                Student.compareStudentByScore(studentParam1, studentParam2));
        students.forEach(student -> System.out.println(student.getScore()));

        System.out.println("-------");

        //1.方法引用 类名::静态方法
        students.sort(Student::compareByScore);
        students.stream().map(Student::getScore).forEach(System.out::println);//方法引用 类名::一般方法

        students.sort((studentParam1, studentParam2) ->
                Student.compareStudentByName(studentParam1, studentParam2));
        students.forEach(student -> System.out.println(student.getName()));

        System.out.println("-------");

        students.sort(Student::compareByName);
        students.forEach(student -> System.out.println(student.getName()));

        System.out.println("-------分割线-------");

        StudentComparator studentComparator = new StudentComparator();

        //Lambda表达式
        students.sort((studentParam1, studentParam2) ->
                studentComparator.compareStudentByScore(studentParam1, studentParam2));
        students.forEach(student -> System.out.println(student.getScore()));

        System.out.println("-------");

        //2.方法引用 对象名::一般方法
        students.sort(studentComparator::compareStudentByScore);
        students.forEach(student -> System.out.println(student.getScore()));

        students.sort((studentParam1,studentParam2) ->
                studentComparator.compareStudentByName(studentParam1, studentParam2));
        students.forEach(student -> System.out.println(student.getName()));

        System.out.println("-------");

        students.sort(studentComparator::compareStudentByName);
        students.forEach(student -> System.out.println(student.getName()));

        System.out.println("-------分割线-------");

        //3.类名::实例方法
        students.sort(Student::compareByScore);
        students.forEach(student -> System.out.println(student.getName()));

        System.out.println("-------");

        students.sort(Student::compareByName);
        students.forEach(student -> System.out.println(student.getName()));

        System.out.println("-------分割线-------");

        List<String> cities = Arrays.asList("qingdao", "chongqing", "tianjin", "beijing");

        Collections.sort(cities, (city1,city2) -> city1.compareToIgnoreCase(city2));
        cities.forEach(city -> System.out.println(city));

        System.out.println("-------");

        Collections.sort(cities, String::compareToIgnoreCase);
        cities.forEach(System.out::println);

        System.out.println("-------分割线-------");

        Test2 test = new Test2();
        //4.类名::new
        System.out.println(test.getString(String::new));
        System.out.println(test.getString2("hello",String::new));
    }

}
