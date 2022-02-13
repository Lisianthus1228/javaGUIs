import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class lesson20 extends JFrame {
    JSlider slider;
    JLabel label;
    
    public lesson20() {
        setLayout(new FlowLayout());
        
        slider = new JSlider(JSlider.HORIZONTAL, 0, 20, 0); // Orientation, min value, max value, initial value
        slider.setMajorTickSpacing(5); // Distance between major markers
        slider.setPaintTicks(true); // Show tick/spacing marks on slider?
        add(slider);
        
        label = new JLabel("Current value: 0");
        add(label);
        
        event e = new event();
        slider.addChangeListener(e);
    }
    
    public class event implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            int value = slider.getValue();
            label.setText("Current value: " + value);
        }
    }
    
    public static void main(String[] args) {
        lesson20 newGui = new lesson20();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(400, 85);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 20: JSlider");
    }
}