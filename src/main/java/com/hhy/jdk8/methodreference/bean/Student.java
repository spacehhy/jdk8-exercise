package com.hhy.jdk8.methodreference.bean;

public class Student {

    private String name;

    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /*类名::静态方法 此方法从设计角度考虑是错误的*/
    public static int compareStudentByScore(Student student1,Student student2){
        return student1.getScore() - student2.getScore();
    }

    public static int compareStudentByName(Student student1,Student student2){
        return student1.getName().compareToIgnoreCase(student2.getName());
    }

    /*类名::实例方法*/
    public int compareByScore(Student student){
        return this.getScore() - student.getScore();
    }

    public int compareByName(Student student){
        return this.getName().compareToIgnoreCase(student.getName());
    }
}
