import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class lesson2 extends JFrame {
    private JLabel label;
    private JTextField textField;
    private JButton button;
    
    public lesson2() {
        setLayout(new FlowLayout());
        
        label = new JLabel("THIS IS A LABEL.");
        add(label);
        textField = new JTextField("Enter text here"); // Can use an integer to denote width of field.
        add(textField);
        button = new JButton("Click me.");
        add(button);
    }
    
    public static void main(String[] args) {
        lesson2 newGui = new lesson2();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.pack(); // Used over setSize when using FlowLayout()
        newGui.setVisible(true);
        newGui.setTitle("Lesson 2: Adding objects");
    }
}