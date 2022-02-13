import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class lesson21and22 extends JFrame {
    JSlider redSlider, greenSlider, blueSlider;
    JLabel redLabel, greenLabel, blueLabel;
    JPanel colorPanel, sliders, labels;
    
    public lesson21and22() {
        redSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        greenSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        blueSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        
        redLabel = new JLabel("Red: 0");
        greenLabel = new JLabel("Green: 0");
        blueLabel = new JLabel("Blue: 0");
        
        event e = new event();
        redSlider.addChangeListener(e);
        greenSlider.addChangeListener(e);
        blueSlider.addChangeListener(e);
        
        colorPanel = new JPanel();
        colorPanel.setBackground(Color.BLACK);
        
        Container pane = this.getContentPane(); // Retrieves the content pane to add objects to.
        pane.setLayout(new GridLayout(1,3,3,3));
        
        sliders = new JPanel();
        labels = new JPanel();
        // CONTROLS ORDER OF PANELS/OBJECTS
        pane.add(labels);
        pane.add(sliders);
        pane.add(colorPanel);
        
        // LAYOUT
        labels.setLayout(new GridLayout(3,1,2,2));
        labels.add(redLabel);
        labels.add(greenLabel);
        labels.add(blueLabel);
        
        sliders.setLayout(new GridLayout(3,1,2,2));
        sliders.add(redSlider);
        sliders.add(greenSlider);
        sliders.add(blueSlider);
        
    }
    
    public class event implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            int r = redSlider.getValue();
            int g = greenSlider.getValue();
            int b = blueSlider.getValue();
            redLabel.setText("Red: " + r);
            greenLabel.setText("Green: " + g);
            blueLabel.setText("Blue: " + b);
            colorPanel.setBackground(new Color(r,g,b));
        }
    }
    
    public static void main(String[] args) {
        lesson21and22 newGui = new lesson21and22();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(480,200);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 21/22: Slider color changer");
    }
}