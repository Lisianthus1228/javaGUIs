import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class lesson1 extends JFrame {
    public static void main(String[] args) {
        lesson1 newGui = new lesson1();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(300,300);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 1: Introduction");
    }
}