import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class lesson13to16 extends JFrame {
    JButton add, subtract, multiply, divide, root;
    JTextField num1, num2;
    JLabel result, enter1, enter2;
    
    public lesson13to16() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        enter1 = new JLabel("1st: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(enter1, c);
        
        num1 = new JTextField(10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        add(num1, c);
        
        enter2 = new JLabel("2nd: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        add(enter2, c);
        
        num2 = new JTextField(10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 3;
        add(num2, c);
        
        add = new JButton("+");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        add(add, c);
        
        subtract = new JButton("-");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        add(subtract, c);
        
        multiply = new JButton("*");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;
        add(multiply, c);
        
        divide = new JButton("/");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 2;
        add(divide, c);
        
        root = new JButton("root");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 4;
        c.gridy = 2;
        add(root, c);
        
        result = new JLabel("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 6;
        add(result, c);
        
        event a = new event();
        add.addActionListener(a);
        subtract.addActionListener(a);
        multiply.addActionListener(a);
        divide.addActionListener(a);
        root.addActionListener(a);
    }
    
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            double number1, number2;
            result.setForeground(Color.RED);
            
            try {
                number1 = Double.parseDouble(num1.getText());
                number2 = Double.parseDouble(num2.getText());
            } catch(NumberFormatException e) {
                result.setText("Illegal data in field. Check both inputs.");
                return;
            }
            
            String op = a.getActionCommand();
            switch(op) {
                case "+":
                    double sum = number1+number2;
                    result.setText(number1 + " + " + number2 + " = " + sum);
                    break;
                case "-":
                    double sub = number1-number2;
                    result.setText(number1 + " - " + number2 + " = " + sub);
                    break;
                case "*":
                    double mult = number1*number2;
                    result.setText(number1 + " * " + number2 + " = " + mult);
                    break;
                case "/":
                    if(number2 == 0) {
                        result.setText("Division by zero attempted. Use non-zero value for 2nd field.");
                        return;
                    } else {
                        double div = number1/number2;
                        result.setText(number1 + " / " + number2 + " = " + div);
                    }
                    break;
                case "root":
                    double rootVal = Math.pow(number1, 1/number2);
                    result.setText(number1 + " to the root of " + number2 + " = " + rootVal);
                    break;
            }
        }
    }
    
    public static void main(String[] args) {
        lesson13to16 newGui = new lesson13to16();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(400, 160);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 13-16: Simple calculator");
    }
}