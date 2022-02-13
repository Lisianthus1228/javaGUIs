import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class lesson4 extends JFrame {
    private JLabel label;
    private JButton button;
    
    public lesson4() {
        setLayout(new FlowLayout());
        
        button = new JButton("CLICK FOR TEXT.");
        add(button);
        label = new JLabel("");
        add(label);
        
        event e = new event();
        button.addActionListener(e);
    }
    
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            label.setText("Text has appeared.");
        }
    }
    
    public static void main(String[] args) {
        lesson4 newGui = new lesson4();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.pack();
        newGui.setVisible(true);
        newGui.setTitle("Lesson 4: Event Handling");
    }
}