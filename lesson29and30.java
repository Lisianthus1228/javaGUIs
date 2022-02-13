import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class lesson29and30 extends JFrame {
    JLabel promptLabel, timerLabel;
    int counter;
    JTextField tf;
    JButton button;
    Timer timer;
    
    public lesson29and30() {
        setLayout(new GridLayout(2, 2, 5, 5));
        
        promptLabel = new JLabel("Enter seconds:", SwingConstants.CENTER);
        add(promptLabel);
        tf = new JTextField(5);
        add(tf);
        button = new JButton("Start timing");
        add(button);
        timerLabel = new JLabel("Waiting...", SwingConstants.CENTER);
        add(timerLabel);
        
        event e = new event();
        button.addActionListener(e);
    }
    
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int count = (int)(Double.parseDouble(tf.getText()));
            timerLabel.setText("Time left: " + count);
            
            TimeClass tc = new TimeClass(count);
            timer = new Timer(1000, tc); // tc acts as a listener for when the timer increments.
            timer.start();
        }
    }
    public class TimeClass implements ActionListener {
        int counter;
        public TimeClass(int counter) {
            this.counter = counter;
        }
        
        public void actionPerformed(ActionEvent tc) {
            counter--;
            if(counter >= 1) {
                timerLabel.setText("Time left: " + counter);
            } else {
                timer.stop();
                timerLabel.setText("Done!");
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
    
    public static void main(String[] args) {
        lesson29and30 newGui = new lesson29and30();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(400,180);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 21/22: Slider color changer");
    }
}