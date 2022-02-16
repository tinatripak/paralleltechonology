package Billiards_ex1_4;

import javax.swing.*;

public class BounceMain {
    public static void main(String[] args) {
        BounceFrame frame = new BounceFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        System.out.println("Thread name = " +
                Thread.currentThread().getName());
        //frame.joinVisualization(); // 4
    }
}

