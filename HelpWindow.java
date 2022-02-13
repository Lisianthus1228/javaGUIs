import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class HelpWindow extends JDialog {
    JLabel label;
    
    public HelpWindow(JFrame frame) { // Will take our main GUI frame as a parameter
        super(frame, "About GUI", true);
        setLayout(new FlowLayout());
        
        label = new JLabel("This is a help menu.");
        add(label);
    }
}