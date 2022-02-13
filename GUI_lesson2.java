import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_lesson2 extends JPanel {
    public GUI_lesson2() {
        setBackground(Color.BLACK);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Painting code
        g.setColor(Color.WHITE);
        g.drawLine(50, 50, 100, 100); // x1, y1, x2, y2
        g.drawString("This is a string", 150, 20);
        g.fillOval(30, 200, 70, 250);
    }
}
