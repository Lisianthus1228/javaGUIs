import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class lesson31to34 extends JFrame {
    Timer timer;
    int timerCounter;
    int clickCounter;
    JLabel directions, enterTime, clickLabel, timeLeft;
    JButton startButton, clickButton;
    JTextField tf;
    
    JMenuBar menubar;
    JMenu file;
    JMenuItem reset, exit;
    
    public lesson31to34() {
        Container pane = this.getContentPane();
        pane.setLayout(new GridLayout(3, 1, 2, 2));
        
        menubar = new JMenuBar();
        setJMenuBar(menubar);
        file = new JMenu("File");
        menubar.add(file);
        reset = new JMenuItem("Reset");
        file.add(reset);
        exit = new JMenuItem("Quit");
        file.add(exit);
        
        
        ResetClass rc = new ResetClass();
        reset.addActionListener(rc);
        ExitClass ec = new ExitClass();
        exit.addActionListener(ec);
        
        
        // TOP PANEL
        JPanel top = new JPanel();
        top.setLayout(new GridLayout(1,1));
        directions = new JLabel("Enter time, press start, start CLICKing", SwingConstants.CENTER);
        top.add(directions);
        pane.add(top);
        
        // MIDDLE PANEL
        JPanel middle = new JPanel();
        middle.setLayout(new GridLayout(1,3));
        enterTime = new JLabel("Enter time (sec):", SwingConstants.CENTER);
        middle.add(enterTime);
        
        tf = new JTextField();
        middle.add(tf);
        
        startButton = new JButton("Start");
        middle.add(startButton);
        pane.add(middle);
        
        // BOTTOM PANEL
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1,3));
        clickButton = new JButton("CLICK");
        clickButton.setEnabled(false); // Cannot be clicked unless timer is started/active.
        bottom.add(clickButton);
        
        clickLabel = new JLabel("Clicks: 0", SwingConstants.CENTER);
        bottom.add(clickLabel);
        
        timeLeft = new JLabel("Time left: N/A");
        bottom.add(timeLeft);
        pane.add(bottom);
        
        StartButtonClass sbc = new StartButtonClass();
        startButton.addActionListener(sbc);
        ClickButtonClass cbc = new ClickButtonClass();
        clickButton.addActionListener(cbc);
    }
    
    // EVENTS
    public class StartButtonClass implements ActionListener {
        public void actionPerformed(ActionEvent sbc) {
            try {
                int timeCount = (int) (Double.parseDouble(tf.getText()));
                if(timeCount <= 0) {
                    tf.setText("Positive numbers only.");
                } else {
                    timeLeft.setText("Time left: " + timeCount);
                    TimeClass tc = new TimeClass(timeCount);
                    timer = new Timer(1000, tc);
                    timer.start();
                    startButton.setEnabled(false); // Disable start button until current timer is done.
                    clickButton.setEnabled(true);
                }
            } catch(NumberFormatException ex) {
                tf.setText("Numbers only.");
            }
        }
    }
    public class ClickButtonClass implements ActionListener {
        public void actionPerformed(ActionEvent cbc) {
            clickCounter++;
            clickLabel.setText("Clicks: " + clickCounter);
        }
    }
    public class TimeClass implements ActionListener {
        int timerCounter;
        public TimeClass(int timerCounter) {
            this.timerCounter = timerCounter;
        }
        public void actionPerformed(ActionEvent tc) {
            timerCounter--;
            if(timerCounter >= 1) {
                timeLeft.setText("Time left: " + timerCounter);
            } else {
                timer.stop();
                timeLeft.setText("Done!");
                clickButton.setEnabled(false);
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
    public class ResetClass implements ActionListener {
        public void actionPerformed(ActionEvent rc) {
            clickButton.setEnabled(false);
            startButton.setEnabled(true);
            clickCounter = 0;
            clickLabel.setText("Clicks: 0");
            tf.setText("");
            timeLeft.setText("Time left: N/A");
        }
    }
    public class ExitClass implements ActionListener {
        public void actionPerformed(ActionEvent ec) {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        lesson31to34 newGui = new lesson31to34();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(440,180);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 31-34: Click counter/timer");
    }
}