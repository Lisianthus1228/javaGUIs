import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class lesson23 extends JFrame {
    JMenuBar menubar;
    JMenu help;
    JMenuItem about;
    
    public lesson23() {
        setLayout(new FlowLayout());
        
        menubar = new JMenuBar();
        add(menubar);
        help = new JMenu("Help");
        menubar.add(help);
        about = new JMenuItem("About");
        help.add(about);
        setJMenuBar(menubar);
        
        event e = new event();
        about.addActionListener(e);
    }
    
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            HelpWindow gui = new HelpWindow(lesson23.this);
            gui.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            gui.setSize(300,100);
            gui.setLocation(300,300);
            gui.setVisible(true);
        }
    }
    
    public static void main(String[] args) {
        lesson23 newGui = new lesson23();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(480,180);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 23: Opening new windows");
    }
}