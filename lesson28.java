import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class lesson28 extends JFrame {
    JTextArea textarea;
    JButton button;
    JLabel label;
    
    public lesson28() {
        setLayout(new FlowLayout());
        
        textarea = new JTextArea(5, 30);
        add(textarea);
        button = new JButton("Click here to transfer text to label");
        add(button);
        label = new JLabel("");
        add(label);
        
        event e = new event();
        button.addActionListener(e);
    }
    
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String text = textarea.getText();
            if(text.equals("")) {
                label.setText("Please input text into area!");
            } else {
                label.setText(text);
            }
        }
    }
    
    public static void main(String[] args) {
        lesson28 newGui = new lesson28();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(400,200);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 28: JTextArea");
    }
}