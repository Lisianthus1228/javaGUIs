import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class lesson17 extends JFrame {
    JPanel panel;
    
    public lesson17() {
        panel = new JPanel();
        panel.setBackground(randomColor());
        add(panel);
        
        event e = new event();
        panel.addMouseListener(e); // Triggers event on click with panel.
    }
    
    public Color randomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return(new Color(r,g,b));
    }
    
    public class event implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            panel.setBackground(randomColor());
        }
        
        public void mousePressed(MouseEvent e) {
        }
        public void mouseReleased(MouseEvent e) {
        }
        public void mouseEntered(MouseEvent e) {
        }
        public void mouseExited(MouseEvent e) {
        }
    }
    
    public static void main(String[] args) {
        lesson17 newGui = new lesson17();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(300,300);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 17: Random color panel");
    }
}