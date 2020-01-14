package com.hhy.jdk8.method;

import com.hhy.jdk8.method.bean.Student;

public class StudentComparator {

    /*对象名::实例方法*/
    public int compareStudentByScore(Student student1, Student student2) {
        return student1.getScore() - student2.getScore();
    }

    public int compareStudentByName(Student student1, Student student2) {
        return student1.getName().compareToIgnoreCase(student2.getName());
    }
}
