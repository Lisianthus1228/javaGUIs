import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; // New package related to input/output.

public class lesson35 extends JFrame {
    JLabel label;
    JTextField tf;
    JButton button;
    JLabel errorLabel;
    
    public lesson35() {
        setLayout(new FlowLayout());
        
        label = new JLabel("Enter text to write to file:");
        add(label);
        tf = new JTextField(25);
        add(tf);
        button = new JButton("Write to file");
        add(button);
        errorLabel = new JLabel("");
        add(errorLabel);
        
        event e = new event();
        button.addActionListener(e);
    }
    
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String word = tf.getText();
                FileWriter stream = new FileWriter("C:\\A\\writtenFile.txt"); // CHANGE THIS DEPENDING ON DEVICE.
                BufferedWriter out = new BufferedWriter(stream);
                out.write(word);
                out.close();
                errorLabel.setText("");
            } catch(Exception ex) {
                errorLabel.setText("Error occured. Please try again.");
            }
        }
    }
    
    public static void main(String[] args) {
        lesson35 newGui = new lesson35();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(360, 160);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 35: Basic I/O");
    }
}