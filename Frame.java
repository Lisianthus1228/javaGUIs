import java.awt.*;
import javax.swing.*;

public class Frame {
    public static void main(String[] args) {
        new Frame();
    }
    
    public Frame() {
        JFrame gui = new JFrame(); // Create new JFrame/GUI to add our panel to. Done in a separate class.
        gui.setTitle("GUI Lesson 1: Painting");
        gui.setSize(400, 400);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GUI_lesson1 panel = new GUI_lesson1(); // Get panel made earlier and add it here.
        
        Container pane = gui.getContentPane(); // Add panel to content pane.
        pane.setLayout(new GridLayout(1,1));
        
        pane.add(panel);
        gui.setVisible(true);
    }
}