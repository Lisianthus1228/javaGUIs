import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class lesson8 extends JFrame {
    JMenu file; // A tab to use within the menubar.
    JMenuBar menubar; // The bar itself that will hold the tabs.
    JMenuItem exit; // Option that will show under the 'File' tab.
    
    public lesson8() {
        setLayout(new FlowLayout());
        
        menubar = new JMenuBar();
        setJMenuBar(menubar);
        file = new JMenu("File");
        menubar.add(file); // Adding the 'file' tab to our menu bar.
        exit = new JMenuItem("Exit");
        file.add(exit);
        
        event e = new event();
        exit.addActionListener(e);
    }
    
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0); // Exits GUI/Program
        }
    }
    
    public static void main(String[] args) {
        lesson8 newGui = new lesson8();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(480,360);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 8: Menus");
    }
}