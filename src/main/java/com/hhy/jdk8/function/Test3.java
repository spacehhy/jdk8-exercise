package com.hhy.jdk8.function;

import com.hhy.jdk8.function.bean.Person;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * BiFunctionTest
 */
public class Test3 {

    public static void main(String[] args) {
        Person person1 = new Person("zhangsan", 20);
        Person person2 = new Person("lisi", 30);
        Person person3 = new Person("wangwu", 40);

        List<Person> persons = Arrays.asList(person1, person2, person3);

        Test3 test = new Test3();

//        List<Person> personResult = test.getPersonsByUsername("zhangsan", persons);
//        personResult.stream().forEach(person -> System.out.println(person.getUsername()));

//        List<Person> personResult = test.getPersonsByAge(20, persons);
//        personResult.stream().forEach(person -> System.out.println(person.getAge()));

        List<Person> personResult = test.getPersonsByAge2(20, persons, (age, personList) -> {
            return  personList.stream().filter(person -> person.getAge() > age).collect(Collectors.toList());
        });
        personResult.stream().forEach(person -> System.out.println(person.getUsername()));

        System.out.println("--------------------");

        List<Person> personsResult2 = test.getPersonsByAge2(20, persons, (age, personList) -> personList.stream()
                .filter(person -> person.getAge() <= age).collect(Collectors.toList()));
        personsResult2.stream().forEach(person -> System.out.println(person.getUsername()));

    }

    public List<Person> getPersonsByUsername(String username, List<Person> persons) {
        return persons.stream().filter(person -> person.getUsername().equals(username)).collect(Collectors.toList());
    }

    public List<Person> getPersonsByAge(int age, List<Person> persons) {
        BiFunction<Integer, List<Person>, List<Person>> biFunction = (ageOfPerson, personList) -> {
            return personList.stream().filter(person -> person.getAge() > ageOfPerson).collect(Collectors.toList());
        };
        return biFunction.apply(age, persons);
    }

    public List<Person> getPersonsByAge2(int age, List<Person> persons, BiFunction<Integer, List<Person>, List<Person>> biFunction){
        return biFunction.apply(age, persons);
    }
}
