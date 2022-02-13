import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

public class lesson24to27 extends JFrame {
    int round = 1;
    int firstRand, rand2, rand3, rand4;
    JLabel firstLabel, secondLabel, thirdLabel, fourthLabel, or, winOrLose;
    JButton higher, lower;
    JMenuBar menubar;
    JMenu file;
    JMenuItem reset, exit;
    
    // USED WITH GAME LOGIC
    int[] pastValues = new int[3]; // PAST ROUND VALUES TO COMPARE WITH
    int currentValue = 0; // CURRENT VALUE THAT WILL BE STORED IN PAST-VALUES AFTER ITS ROUND
    
    public lesson24to27() {
        setLayout(new GridLayout(3,1));
        Font font = new Font("Serif", Font.BOLD, 16);
        firstRand = (int) (Math.random()*20+1);
        pastValues[0] = firstRand;
        
        menubar = new JMenuBar();
        setJMenuBar(menubar);
        file = new JMenu("File");
        menubar.add(file);
        reset = new JMenuItem("Reset");
        file.add(reset);
        exit = new JMenuItem("Exit");
        file.add(exit);
        
        systemClose s = new systemClose();
        exit.addActionListener(s);
        restartGame r = new restartGame();
        reset.addActionListener(r);
        
        Container pane = this.getContentPane();
        // TOP PANEL
        JPanel top = new JPanel();
        top.setLayout(new GridLayout(1, 4));
        
        firstLabel = new JLabel("" + firstRand); // Aligns text to center.
        firstLabel.setFont(font);
        top.add(firstLabel);
        secondLabel = new JLabel("", SwingConstants.CENTER); // Aligns text to center.
        secondLabel.setFont(font);
        top.add(secondLabel);
        thirdLabel = new JLabel("", SwingConstants.CENTER); // Aligns text to center.
        thirdLabel.setFont(font);
        top.add(thirdLabel);
        fourthLabel = new JLabel("", SwingConstants.CENTER); // Aligns text to center.
        fourthLabel.setFont(font);
        top.add(fourthLabel);
        pane.add(top);
        
        // MIDDLE PANEL
        JPanel middle = new JPanel();
        middle.setLayout(new GridLayout(1, 3));
        
        higher = new JButton("HIGHER");
        middle.add(higher);
        or = new JLabel("OR", SwingConstants.CENTER);
        middle.add(or);
        lower = new JButton("LOWER");
        middle.add(lower);
        pane.add(middle);
        
        event e = new event();
        higher.addActionListener(e);
        lower.addActionListener(e);
        
        // BOTTOM PANEL
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1, 1));
        
        winOrLose = new JLabel("", SwingConstants.CENTER);
        winOrLose.setFont(font);
        bottom.add(winOrLose);
        pane.add(bottom);
    }
    
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String option = e.getActionCommand();
            
            switch(round) {
                case 1:
                    rand2 = (int) (Math.random()*20 + 1);
                    secondLabel.setText("" + rand2);
                    currentValue = rand2;
                    break;
                case 2:
                    rand3 = (int) (Math.random()*20 + 1);
                    thirdLabel.setText("" + rand3);
                    pastValues[round-1] = currentValue; // Stores value from round 1 into index 1.
                    currentValue = rand3;
                    break;
                case 3:
                    rand4 = (int) (Math.random()*20 + 1);
                    fourthLabel.setText("" + rand4);
                    pastValues[round-1] = currentValue; // Stores value from round 2 into index 2.
                    currentValue = rand4;
                    break;
            }
            
            if(currentValue > pastValues[round-1] && option.equals("HIGHER")) {
                winOrLose.setText("Right, " + (3-round) + " more!");
            } else if(currentValue < pastValues[round-1] && option.equals("HIGHER")) {
                winOrLose.setText("You lose!");
                lower.setEnabled(false); // Disables both buttons, user must reset in menu to play again.
                higher.setEnabled(false);
                round = -1;
            } else if(currentValue > pastValues[round-1] && option.equals("LOWER")) {
                winOrLose.setText("You lose!");
                lower.setEnabled(false); // Disables both buttons, user must reset in menu to play again.
                higher.setEnabled(false);
                round = -1; // Ensures round wont tick over to 4 and let the player win even though they lost.
            } else if(currentValue < pastValues[round-1] && option.equals("LOWER")) {
                winOrLose.setText("Right, " + (3-round) + " more!");
            }
            
            if((round+1) <= 3) {
                round++;
            } else {
                winOrLose.setText("YOU WON!");
                lower.setEnabled(false);
                higher.setEnabled(false);
            }
        }
    }
    
    public class systemClose implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    public class restartGame implements ActionListener {
        public void actionPerformed(ActionEvent e) { // RESET ALL VARIABLES
            firstRand = (int) (Math.random()*20 + 1);
            pastValues[0] = firstRand;
            round = 1;
            higher.setEnabled(true);
            lower.setEnabled(true);
            firstLabel.setText("" + firstRand);
            secondLabel.setText("");
            thirdLabel.setText("");
            fourthLabel.setText("");
            winOrLose.setText("");
        }
    }
    
    public static void main(String[] args) {
        lesson24to27 newGui = new lesson24to27();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(360,150);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 24-27: Higher/Lower guessing game");
    }
}