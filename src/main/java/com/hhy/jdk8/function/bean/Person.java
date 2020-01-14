package com.hhy.jdk8.function.bean;

public class Person {

    private String username;

    private int age;

    public Person(String name, int age) {
        this.username = name;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
