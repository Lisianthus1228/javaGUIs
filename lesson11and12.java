import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class lesson11and12 extends JFrame {
    JLabel label1, label2, label3, label4, errorLabel;
    JTextField tf1, tf2, tf3, tf4;
    JButton button;
    
    public lesson11and12() {
        setLayout(new GridLayout(5, 2, 5, 5)); // Rows, Columns, horizontal-gap, vertical-gap
        
        label1 = new JLabel("Enter number of movements:");
        add(label1);
        tf1 = new JTextField();
        add(tf1);
        
        label2 = new JLabel("Enter delay between movements:");
        add(label2);
        tf2 = new JTextField();
        add(tf2);
        
        label3 = new JLabel("Enter screen width in pixels:");
        add(label3);
        tf3 = new JTextField();
        add(tf3);
        
        label4 = new JLabel("Enter screen height in pixels:");
        add(label4);
        tf4 = new JTextField();
        add(tf4);
        
        button = new JButton("Start");
        add(button);
        errorLabel = new JLabel("");
        add(errorLabel);
        
        event e = new event();
        button.addActionListener(e);
    }
    
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int num = (int) (Double.parseDouble(tf1.getText()));
                int delay = (int) (Double.parseDouble(tf2.getText()));
                int width = (int) (Double.parseDouble(tf3.getText()));
                int height = (int) (Double.parseDouble(tf4.getText()));
                
                if(num <= 0 || delay <= 0 || width <= 0 || height <= 0) {
                    errorLabel.setText("Invalid value found. Positive values only.");
                } else {
                    errorLabel.setText("");
                    try {
                        Robot rob = new Robot();
                        for(int x=0; x<=num; x++) {
                            rob.mouseMove((int)(Math.random()*width), (int)(Math.random()*height));
                            rob.delay(delay);
                        }
                    } catch(AWTException ex) {
                        System.exit(0);
                    }
                }
            } catch(Exception exx) {
                errorLabel.setText("Numbers only.");
            }
        }
    }
    
    public static void main(String[] args) {
        lesson11and12 newGui = new lesson11and12();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(540,220);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 11/12: Virus mouse mover ");
    }
}