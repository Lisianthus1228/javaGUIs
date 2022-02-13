import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class lesson19 extends JFrame {
    JTable table;
    
    public lesson19() {
        setLayout(new FlowLayout());
        String[] columnNames = {"Name", "Hair Color", "Gender"};
        String[][] userData = {
            {"Steve","Brown","Male"},
            {"Jessica","Blonde","Female"},
            {"Panagiotidis","Deep Brown","Male"},
            {"Giorgos","Deep Brown","Male"},
            {"Theofanis","Deep Brown","Male"},
            {"Taulant","Deep Brown","Male"},
        };
        
        table = new JTable(userData, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500,50)); // Viewport dimensions, the space the table will take up.
        table.setFillsViewportHeight(true); // Always try to fill the height of the viewport.
        
        JScrollPane scrollPane = new JScrollPane(table); // Allows scrolling of table when the rows of data cannot fit in the viewport.
        add(scrollPane);
    }
    
    public static void main(String[] args) {
        lesson19 newGui = new lesson19();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(720,360);
        newGui.setVisible(true);
        newGui.setTitle("Lesson 19: Using JTable");
    }
}