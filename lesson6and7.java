import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class lesson6and7 extends JFrame {
    int randomNum, guess;
    private JButton button;
    private JTextField textField;
    private JLabel promptLabel;
    private JLabel resultLabel;
    private JLabel randomLabel;
    
    public lesson6and7() {
        setLayout(new FlowLayout());
        
        promptLabel = new JLabel("Enter a random number (1-10):");
        add(promptLabel);
        textField = new JTextField(8);
        add(textField);
        button = new JButton("Submit guess");
        add(button);
        resultLabel = new JLabel("");
        add(resultLabel);
        randomLabel = new JLabel("");
        add(randomLabel);
        
        event e = new event();
        button.addActionListener(e);
    }
    
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            randomNum = (int) ((Math.random() * 10) + 1);
            try {
                guess = (int) (Double.parseDouble(textField.getText()));
                if(guess == randomNum) {
                    resultLabel.setText("You win!");
                } else {
                    resultLabel.setText("You lose!");
                }
                randomLabel.setText("The random number generated was: " + randomNum);
            } catch (Exception ex) {
                randomLabel.setText("Invalid input, numbers only.");
            }
        }
    }
    
    public static void main(String[] args) {
        lesson6and7 newGui = new lesson6and7();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(480,160);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 6/7: Random Number Game");
    }
}