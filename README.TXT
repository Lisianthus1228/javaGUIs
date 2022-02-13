import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class lesson21and22 extends JFrame {
    
    
    public lesson21and22() {
        setLayout(new FlowLayout());
        
        
    }
    
    public static void main(String[] args) {
        lesson21and22 newGui = new lesson21and22();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.pack();
        newGui.setVisible(true);
        newGui.setTitle("Lesson 21/22: Slider color changer");
    }
}