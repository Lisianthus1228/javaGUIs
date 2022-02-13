import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class lesson18 extends JFrame {
    JCheckBox show1, show2;
    JLabel label1, label2;
    
    public lesson18() {
        setLayout(new GridLayout(2,2));
        
        label1 = new JLabel("");
        add(label1);
        label2 = new JLabel("");
        add(label2);
        
        show1 = new JCheckBox("Show label 1");
        add(show1);
        show2 = new JCheckBox("Show label 2");
        add(show2);
        
        event e = new event();
        show1.addItemListener(e);
        show2.addItemListener(e);
    }
    
    public class event implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            if(show1.isSelected()) {
                label1.setText("First checkbox activated.");
            } else {
                label1.setText("");
            }
            
            if(show2.isSelected()) {
                label2.setText("Second checkbox activated.");
            } else {
                label2.setText("");
            }
        }
    }
    
    public static void main(String[] args) {
        lesson18 newGui = new lesson18();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(380, 100);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 18: Using JCheckBox");
    }
}