import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class project1_tictactoe extends JFrame {
    JMenuBar menubar;
    JMenu file;
    JMenuItem reset, exit;
    
    int currentTurn = 1; // 1 = player one, 2 = player two
    int[][] gameBoard = {
        {60, 61, 62},
        {63, 64, 65},
        {66, 67, 68}
    };
    JPanel boardPanel;
    JLabel playerOne, playerTwo, gameLabel;
    JTextField playerOneTF, playerTwoTF;
    JButton playerOneButton, playerTwoButton;
    JButton[] gridButtons;
    
    public project1_tictactoe() {
        Container pane = this.getContentPane();
        setLayout(new BorderLayout());
        
        // Toolbar
        menubar = new JMenuBar();
        setJMenuBar(menubar);
        file = new JMenu("File");
        menubar.add(file);
        reset = new JMenuItem("Reset");
        file.add(reset);
        exit = new JMenuItem("Exit");
        file.add(exit);
        ExitEvent ee = new ExitEvent();
        exit.addActionListener(ee);
        ResetEvent re = new ResetEvent();
        reset.addActionListener(re);
        
        // CENTER MENU
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3,3,1,1));
        gridButtons = new JButton[9];
        for(int i=0; i < gridButtons.length; i++) {
            gridButtons[i] = new JButton((i+1)+"");
            gridButtons[i].setEnabled(false);
            boardPanel.add(gridButtons[i]);
        }
        pane.add(boardPanel, BorderLayout.CENTER);
        
        // TOP MENU
        JPanel topPanel = new JPanel();
        playerOne = new JLabel("Player One (X) Turn:");
        topPanel.add(playerOne);
        playerOneTF = new JTextField(8);
        topPanel.add(playerOneTF);
        playerOneButton = new JButton("Play");
        topPanel.add(playerOneButton);
        
        pane.add(topPanel, BorderLayout.PAGE_START);
        
        // BOTTOM MENU
        JPanel bottomPanel = new JPanel();
        playerTwo = new JLabel("Player Two (O) Turn:");
        bottomPanel.add(playerTwo);
        playerTwoTF = new JTextField(8);
        playerTwoTF.setEnabled(false);
        bottomPanel.add(playerTwoTF);
        playerTwoButton = new JButton("Play");
        playerTwoButton.setEnabled(false); // Player one starts first.
        bottomPanel.add(playerTwoButton);
        
        pane.add(bottomPanel, BorderLayout.PAGE_END);
        
        // SIDE MENU
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(180, -1)); // Adjust horizontal width of side-menu
        gameLabel = new JLabel("");
        sidePanel.add(gameLabel, BorderLayout.CENTER);
        
        event playerTurn = new event();
        playerOneButton.addActionListener(playerTurn);
        playerTwoButton.addActionListener(playerTurn);
        
        pane.add(sidePanel, BorderLayout.LINE_END);
    }
    
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            gameLabel.setText(""); // Reset game label if it contained an error message previously.
            String userSymbol = "";
            String gridTileString = "";
            int gridTile = 0;
            
            if(currentTurn == 1) {
                userSymbol = "X";
                gridTileString = playerOneTF.getText();
            } else {
                userSymbol = "O";
                gridTileString = playerTwoTF.getText();
            }
            
            // DISPLAY PLACED TILE
            try {
                gridTile = Integer.parseInt(gridTileString) - 1; // Turn textfield input into usable integer; minus 1 to account for array starting at zero.
                // Check to see if tile is already taken.
                if(gameBoard[gridTile/3][gridTile%3] == 1 || gameBoard[gridTile/3][gridTile%3] == 2) {
                    gameLabel.setText("Error: Tile already taken");
                    return;
                }
                gridButtons[gridTile].setText(userSymbol);
                gridButtons[gridTile].setEnabled(true);
            } catch(NumberFormatException err) {
                gameLabel.setText("Error: Invalid input for tile num"); // Tell user about error.
                return; // Exit method early as data entered will not work, allow user to change it then call the event again.
            } catch(ArrayIndexOutOfBoundsException err) {
                gameLabel.setText("Error: Number out of range (1-9)");
                return;
            }
            
            // GAMEBOARD LOGIC
            gameBoard[gridTile/3][gridTile%3] = currentTurn; // Example: gridTile of 7 gets placed in [2][1]
            // Check for a winner
                boolean winner = false;
                // CASE 1: Any of the row's
                for(int i=0; i < gameBoard.length; i++) {
                    if(gameBoard[i][0] == (gameBoard[i][1]) && gameBoard[i][0] == (gameBoard[i][2])) {
                        winner = true;
                    } 
                }
                // CASE 2: Any of the column's
                for(int i=0; i < gameBoard.length; i++) {
                    if(gameBoard[0][i] == (gameBoard[1][i]) && gameBoard[0][i] == (gameBoard[2][i])) {
                        winner = true;
                    }
                }
                // CASE 3: Both diagonal's
                if(gameBoard[0][0] == (gameBoard[1][1]) && gameBoard[0][0] == (gameBoard[2][2])) {
                    winner = true;
                } else if(gameBoard[0][2] == (gameBoard[1][1]) && gameBoard[0][2] == (gameBoard[2][0])) {
                    winner = true;
                }
                
                if(currentTurn == 1 && winner) {
                    endGame("x");
                    return;
                } else if(currentTurn == 2 && winner) {
                    endGame("o");
                    return;
                }
            // Check for tie.
            int counter = 0;
            for(int i=0; i < gameBoard.length; i++) {
                for(int j=0; j < gameBoard.length; j++) {
                    if(gameBoard[i][j] != 1 && gameBoard[i][j] != 2) {
                        counter++;
                    }
                }
            }
            if(counter == 0) { // NO MORE POSSIBLE MOVES
                endGame("tie");
                return;
            }
                
            // Swap turns if no winner/draw was found
            if(!winner) {
                if(currentTurn == 1) {
                    currentTurn = 2;
                    playerOneButton.setEnabled(false);
                    playerOneTF.setEnabled(false);
                    playerTwoButton.setEnabled(true);
                    playerTwoTF.setEnabled(true);
                } else {
                    currentTurn = 1;
                    playerOneButton.setEnabled(true);
                    playerOneTF.setEnabled(true);
                    playerTwoButton.setEnabled(false);
                    playerTwoTF.setEnabled(false);
                }
            }
        }
    }
    
    public void endGame(String winner) {
        // Prevent more turns being played.
        playerOneButton.setEnabled(false);
        playerOneTF.setEnabled(false);
        playerTwoButton.setEnabled(false);
        playerTwoTF.setEnabled(false);
        switch(winner) {
            case "x":
                gameLabel.setText("Player one wins!");
                break;
            case "o":
                gameLabel.setText("Player two wins!");
                break;
            case "tie":
                gameLabel.setText("Draw! Reset to play again");
                break;
        }
    }
    
    public class ResetEvent implements ActionListener { // RESET GAME
        public void actionPerformed(ActionEvent re) {
            int currentTurn = 1; // Reset to player one.
            playerOneButton.setEnabled(true);
            playerOneTF.setEnabled(true);
            playerTwoButton.setEnabled(false);
            playerTwoTF.setEnabled(false);
            playerOneTF.setText("");
            playerTwoTF.setText("");
            gameLabel.setText("Begin the game!");
            for(int i=0; i < gridButtons.length; i++) {
               gridButtons[i].setEnabled(false);
               gridButtons[i].setText((i+1)+"");
            }
            int counter = 0;
            for(int i=0; i < 3; i++) {
                for(int j=0; j < 3; j++) {
                    gameBoard[i][j] = 60 + counter;
                    counter++;
                }
            }
        }
    }
    
    public class ExitEvent implements ActionListener {
        public void actionPerformed(ActionEvent ee) {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        project1_tictactoe newGui = new project1_tictactoe();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(720, 420);
        newGui.setResizable(false);
        newGui.setVisible(true);
        newGui.setTitle("Project 1: Tic-Tac-Toe");
    }
    
    public String toString(int k){
        String s;
        s=""+k;
        return s;
    }
}