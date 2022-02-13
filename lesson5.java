import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class lesson5 extends JFrame {
    private JLabel label;
    private JButton button;
    private JLabel labelTwo;
    private JButton buttonTwo;
    private int x=0;
    
    public lesson5() {
        setLayout(new FlowLayout());
        
        button = new JButton("CLICK FOR TEXT.");
        add(button);
        label = new JLabel("");
        add(label);
        
        buttonTwo = new JButton("CLICK FOR ALTERNATING TEXT.");
        add(buttonTwo);
        labelTwo = new JLabel("Text version one");
        add(labelTwo);
        
        event e = new event();
        button.addActionListener(e);
        event2 ev = new event2();
        buttonTwo.addActionListener(ev);
    }
    
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            label.setText("Text has appeared.");
        }
    }
    public class event2 implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            if(x == 0) {
                labelTwo.setText("Text version two");
                x++;
            } else {
                labelTwo.setText("Text version one");
                x = 0;
            }
        }
    }
    
    public static void main(String[] args) {
        lesson5 newGui = new lesson5();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.pack();
        newGui.setVisible(true);
        newGui.setTitle("Lesson 5: Multiple Event Handlers");
    }
}