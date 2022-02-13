import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class lesson10 extends JFrame {
    JButton button1, button2, button3;
    JLabel label1, label2, label3;
    
    public lesson10() {
        setLayout(new GridLayout(2, 3)); // New layout, GridLayout based on (rows, columns)
        // Each cell of the grid can contain one element that automatically resizes to fill it in some cases (e.g. buttons).
        
        button1 = new JButton("Button 1");
        add(button1);
        label1 = new JLabel("Label 1");
        add(label1);
        
        button2 = new JButton("Button 2");
        add(button2);
        label2 = new JLabel("Label 2");
        add(label2);
        
        button3 = new JButton("Button 3");
        add(button3);
        label3 = new JLabel("Label 3");
        add(label3);
    }
    
    public static void main(String[] args) {
        lesson10 newGui = new lesson10();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.pack();
        newGui.setVisible(true);
        newGui.setTitle("Lesson 10: Gridlayout");
    }
}