package com.hhy.jdk8.lambda;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
* SwingTest
*/
public class Test1 {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("My JFrame");
        JButton jButton = new JButton("My JButton");

        //1.8之前以匿名内部类方式
        /*jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Pressed!");
            }
        });*/

        //1.8之后采用lambda表达式方式
        jButton.addActionListener(event -> {
            System.out.println("Button Pressed!");
            System.out.println("Hello World");
            System.out.println("executed");
        });

        jFrame.add(jButton);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
