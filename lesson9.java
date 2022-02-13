import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class lesson9 extends JFrame {
    JButton button;
    JLabel label;
    int counter=0, x=0;
    String s;
    
    public lesson9() {
        setLayout(new FlowLayout());
        
        button = new JButton("Click for sound");
        add(button);
        label = new JLabel("");
        add(label);
        
        event e = new event();
        button.addActionListener(e);
    }
    
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Toolkit.getDefaultToolkit().beep();
            counter++;
            if(x==0) {
                s = "time";
            } else if(x==1) {
                s = "times";
            }
            label.setText("You have clicked: " + counter +" "+ s);
            x = 1;
        }
    }
    
    public static void main(String[] args) {
        lesson9 newGui = new lesson9();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(240,120);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 9: Beeper program");
    }
}