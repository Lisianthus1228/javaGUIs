import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class lesson3 extends JFrame {
    private ImageIcon image1;
    private JLabel label1;
    private ImageIcon image2;
    private JLabel label2;
    
    public lesson3() {
        setLayout(new FlowLayout());
        
        image1 = new ImageIcon(getClass().getResource("coolimage.jpg"));
        label1 = new JLabel(image1);
        add(label1);
    }
    
    public static void main(String[] args) {
        lesson3 newGui = new lesson3();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.pack();
        newGui.setVisible(true);
        newGui.setTitle("Lesson 3: Adding Images");
    }
}