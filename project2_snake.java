import javax.swing.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;

public class project2_snake extends JFrame {
    JMenuBar menubar;
    JMenu file, difficulty;
    JMenuItem reset, exit, easy, medium, hard, extreme;
    
    ScheduledExecutorService scheduler;
    Runnable updateGameRunnable;
    ScheduledFuture<?> updateGameHandle;
    
    Font gameFont = new Font("Consolas", Font.BOLD, 24);
    Font menuFont = new Font("Courier New", Font.PLAIN, 24);
    String[][] gameBoard = new String[13][13]; // Handles functional/internal comparison of snake character and movement.
    int[][] decayBoard = new int[13][13]; // Handles decay of snake body parts and creation of new ones to gameBoard.
    JLabel[][] displayBoard = new JLabel[13][13]; // Handles diplaying of gameBoard via JLabel components.
    JPanel boardPanel, statsPanel, statsBottomPanel, inputPanel;
    // PLAYER VARIABLES
    int gameSpeed = 400;
    int snakeX = 6;
    int snakeY = 5;
    int snakeLength = 2;
    int score = 0;
    int timeSurvived = 0;
    String snakeDirection = "RIGHT"; // Start facing/going right.
    // PLAYER GUI
    JLabel playerScore,playerTime;
    JButton pauseButton;
    JButton[] inputMap = new JButton[6];  
    boolean paused = true;
    
    public project2_snake() {
        Container pane = this.getContentPane();
        pane.setLayout(new GridLayout(0,2));
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
        
        difficulty = new JMenu("Difficulty");
        menubar.add(difficulty);
        easy = new JMenuItem("Easy");
        difficulty.add(easy);
        medium = new JMenuItem("Medium");
        difficulty.add(medium);
        hard = new JMenuItem("Hard");
        difficulty.add(hard);
        extreme = new JMenuItem("Extreme");
        difficulty.add(extreme);
        DifficultyEvent de = new DifficultyEvent();
        easy.addActionListener(de);
        medium.addActionListener(de);
        hard.addActionListener(de);
        extreme.addActionListener(de);
        
        // Game board
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(13,13));
        boardPanel.setBackground(new Color(245,245,245));
        boardPanel.setFocusable(true); // Allow user to focus on game display, game will pause if unfocused and inputs will not register.
        boardPanel.requestFocusInWindow(); // Sets focus to boardPanel automatically when GUI window is active.
        for(int i=0; i<displayBoard.length; i++) { // Initialize displayBoard atleast once.
            for(int j=0; j<displayBoard.length; j++) {
                displayBoard[i][j] = new JLabel(" ");
                displayBoard[i][j].setFont(gameFont);
                boardPanel.add(displayBoard[i][j]);
            }
        }
        for(int i=0; i<decayBoard.length; i++) { // Reset/create decayBoard
            for(int j=0; j<decayBoard.length; j++) {
                decayBoard[i][j] = 0;
            }
        }
        pane.add(boardPanel);
        resetArrayBoard(); // Reset array board and set snake initial position.
        
        // Stats, score, etc.
        statsPanel = new JPanel();
        statsPanel.setLayout(new BorderLayout());
        playerScore = new JLabel("SCORE: 0");
        playerScore.setFont(menuFont);
        statsPanel.add(playerScore, BorderLayout.PAGE_START);
        
        statsBottomPanel = new JPanel();
        statsBottomPanel.setLayout(new FlowLayout());
        playerTime = new JLabel("TIME: 0");
        playerTime.setFont(menuFont);
        statsBottomPanel.add(playerTime);
        
        pauseButton = new JButton("Unpause game");
        pauseEvent pe = new pauseEvent();
        pauseButton.addActionListener(pe);
        statsBottomPanel.add(pauseButton);
        statsPanel.add(statsBottomPanel, BorderLayout.PAGE_END);
        updateDisplayBoard(); // Boards are split into functional ArrayBoard ('gameBoard') and display 'displayBoard, playerScore updated aswell.
        
            // Input map
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2,3));
        String[] keyInputs = {"","↑","","←","↓","→"};
        for(int i=0; i < 6; i++) {
            if(i == 0 || i == 2) {
                inputPanel.add(new JLabel("")); // Blank label added to properly align key map.
            } else {
                inputMap[i] = new JButton(keyInputs[i]);
                inputMap[i].setFont(menuFont);
                inputMap[i].setEnabled(false);
                inputPanel.add(inputMap[i]);
            }
        }
        statsPanel.add(inputPanel, BorderLayout.CENTER);
        pane.add(statsPanel);
        
        controlEvent e = new controlEvent();
        boardPanel.addKeyListener(e);
        
        // Handle game update speed
        scheduler = Executors.newScheduledThreadPool(1);
        updateGameRunnable = new Runnable(){
           public void run() {
               if(!paused) {
                   updateGame();
               }
           }
        };
        updateGameHandle = scheduler.scheduleWithFixedDelay(updateGameRunnable, gameSpeed, gameSpeed, TimeUnit.MILLISECONDS); //Scheduler runs updateGame(); every 400ms (default)
        
        ScheduledExecutorService timeSchedule = Executors.newScheduledThreadPool(1);
        Runnable timerRunnable = new Runnable(){
           public void run() {
               if(!paused) {
                   timeSurvived++;
                   playerTime.setText("TIME: "+timeSurvived);
               }
           }
        };
        ScheduledFuture<?> timerHandle = scheduler.scheduleWithFixedDelay(timerRunnable, 1, 1, TimeUnit.SECONDS);
    }
    
    public class controlEvent implements KeyListener {
        public void keyPressed(KeyEvent e) {
            // Detect key inputs (either WASD or arrow keys) and change snake direction.
            String pastDir = snakeDirection; // Store current direction in case of preventing direction change.
            switch(e.getKeyCode()) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    snakeDirection = "UP";
                    inputMap[1].setEnabled(true);
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    snakeDirection = "DOWN";
                    inputMap[4].setEnabled(true);
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    snakeDirection = "LEFT";
                    inputMap[3].setEnabled(true);
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    snakeDirection = "RIGHT";
                    inputMap[5].setEnabled(true);
                    break;
            }
            ouroborosCheck(snakeDirection, pastDir); // Check snake is not trying to go behind itself.
        }
        
        public void keyReleased(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    inputMap[1].setEnabled(false);
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    inputMap[4].setEnabled(false);
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    inputMap[3].setEnabled(false);
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    inputMap[5].setEnabled(false);
                    break;
            }
        }
        public void keyTyped(KeyEvent e) {}; // Necessary overrides, no function.
    }
    
    public void pauseGame() {
        if(paused) {
            pauseButton.setText("Pause game");
            paused = false;
        } else {
            pauseButton.setText("Unpause game");
            paused = true;
        }
        boardPanel.requestFocusInWindow(); // Set focus back to game once button has been clicked, to allow controls.
    }
    
    public void endGame() {
        paused = false; // Force pause game.
        pauseGame();
        
        pauseButton.setEnabled(false);
        pauseButton.setText("GAME OVER");
        boardPanel.setBackground(new Color(125, 125, 125));
    }
    
    public class DifficultyEvent implements ActionListener { // CHANGE DIFFICULTY 
        public void actionPerformed(ActionEvent de) {
            Object obj = de.getSource();
            if(obj instanceof JMenuItem) {
                JMenuItem difficultySetting = (JMenuItem) obj; // Find which menuItem is calling this event.
                String difficultyString = difficultySetting.getText();
                switch(difficultyString) {
                    case "Easy":
                        gameSpeed = 400; // Default game speed
                        break;
                    case "Medium":
                        gameSpeed = 300;
                        break;
                    case "Hard":
                        gameSpeed = 150;
                        break;
                    case "Extreme":
                        gameSpeed = 95;
                        break;
                }
                changeReadInterval(gameSpeed);
            }
        }
    }
    
    public class pauseEvent implements ActionListener { // PAUSE GAME
        public void actionPerformed(ActionEvent pe) {
            pauseGame();
        }
    }
    
    public class ResetEvent implements ActionListener { // RESET GAME
        public void actionPerformed(ActionEvent re) {
            paused = false;
            pauseGame();
            pauseButton.setEnabled(true);
            boardPanel.setBackground(new Color(245, 245, 245));
            
            score = 0;
            timeSurvived = 0;
            snakeLength = 2;
            snakeDirection = "RIGHT";
            for(int i=0; i<decayBoard.length; i++) { // Reset decayBoard
                for(int j=0; j<decayBoard.length; j++) {
                    decayBoard[i][j] = 0;
                }
            }
            resetArrayBoard();
            updateDisplayBoard();
        }
    }
    
    public class ExitEvent implements ActionListener {
        public void actionPerformed(ActionEvent ee) {
            System.exit(0);
        }
    }
    
    public void generateApple() {
        int appleX, appleY;
        do {
            appleX = (int) Math.floor((Math.random() * 11) + 1); // 1-12
            appleY = (int) Math.floor((Math.random() * 11) + 1); // 1-12
        } while(!(gameBoard[appleX][appleY] == " ")); // Repeat loop if tile chosen is not an empty tile.
        gameBoard[appleX][appleY] = "a";
    }
    
    public void ouroborosCheck(String currentDir, String pastDir) {
        if(currentDir.equals("LEFT")) {
            if(gameBoard[snakeX][snakeY-1].equals("s") && decayBoard[snakeX][snakeY-1] == snakeLength) {
                snakeDirection = pastDir;
            }
        }
        switch(currentDir) {
            case "UP":
                if(gameBoard[snakeX-1][snakeY].equals("s") && decayBoard[snakeX-1][snakeY] == snakeLength) {
                    snakeDirection = pastDir;
                }
                break;
            case "DOWN":
                if(gameBoard[snakeX+1][snakeY].equals("s") && decayBoard[snakeX+1][snakeY] == snakeLength) {
                    snakeDirection = pastDir;
                }
                break;
            case "LEFT":
                if(gameBoard[snakeX][snakeY-1].equals("s") && decayBoard[snakeX][snakeY-1] == snakeLength) {
                    snakeDirection = pastDir;
                }
                break;
            case "RIGHT":
                if(gameBoard[snakeX][snakeY+1].equals("s") && decayBoard[snakeX][snakeY+1] == snakeLength) {
                    snakeDirection = pastDir;
                }
                break;
        }
    }
    
    public void resetArrayBoard() {
        for(int i=0; i < gameBoard.length; i++) {
            for(int j=0; j < gameBoard.length; j++) {
                if((12>i&&i>0) && (12>j&&j>0)) {
                    gameBoard[i][j] = " ";
                } else {
                    gameBoard[i][j] = "■";
                }
            }
        }
        snakeX = 6;
        snakeY = 5;
        gameBoard[snakeX][snakeY] = "H";
        for(int i=1; i<=snakeLength; i++) { // Create initial snake body parts and set their decay
            gameBoard[snakeX][snakeY-i] = "s";
            decayBoard[snakeX][snakeY-i] = 3-i;
        }
        gameBoard[6][9] = "a";
    }
    
    public void updateDisplayBoard() {
        for(int i=0; i < displayBoard.length; i++) {
            for(int j=0; j < displayBoard.length; j++) {
                displayBoard[i][j].setText(gameBoard[i][j]);
            }
        }
        playerScore.setText("SCORE: " + score); // Update score aswell.
        playerTime.setText("TIME: " + timeSurvived);
    }
    
    public void changeReadInterval(long time) {
        if(time > 0) {       
            if (updateGameHandle != null) {
                updateGameHandle.cancel(true);
            }
    
            updateGameHandle = scheduler.scheduleWithFixedDelay(updateGameRunnable, gameSpeed, gameSpeed, TimeUnit.MILLISECONDS);
        }
    }
    
    public void updateGame() {
        // Update INTERNAL position
        boolean noDecay = false; // If snake is going to move to an apple, prevent decay this turn, essentially elongating the snake.
        int oldSnakeX = snakeX;
        int oldSnakeY = snakeY; // Store previous location to create new snake body part.
        switch(snakeDirection) {
            case "UP":
                snakeX -= 1;
                break;
            case "DOWN":
                snakeX += 1;
                break;
            case "LEFT":
                snakeY -= 1;
                break;
            case "RIGHT":
                snakeY += 1;
                break;
        }
        
        // Check collision to see if player has hit wall, self or apple.
        if(gameBoard[snakeX][snakeY].equals("s") || gameBoard[snakeX][snakeY].equals("■")) { // Hit body or wall, game over
            endGame();
        } else if(gameBoard[snakeX][snakeY].equals("a")) {
            noDecay = true;
            score++;
            snakeLength++;
            generateApple();
        }
        
        // Decay all parts of snake body unless apple collected.
        if(!noDecay) {
            for(int i=0; i < decayBoard.length; i++) {
                for(int j=0; j < decayBoard.length; j++) {
                    if(decayBoard[i][j] > 0) { // If snake part exists in segment (i.e. decay > 0)
                        decayBoard[i][j] -= 1; // Decay segment.
                        if(decayBoard[i][j] == 0) { // Delete snake body part if fully decayed.
                            gameBoard[i][j] = " ";
                        }
                    }
                }
            }
        }
        // Move snake head & create new body part.
        gameBoard[snakeX][snakeY] = "H";
        gameBoard[oldSnakeX][oldSnakeY] = "s";
        decayBoard[oldSnakeX][oldSnakeY] = snakeLength;
        updateDisplayBoard();
    }
    
    public static void main(String[] args) {
        project2_snake newGui = new project2_snake();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(540, 360);
        newGui.setResizable(false);
        newGui.setVisible(true);
        newGui.setTitle("Project 2: Snake");
    }
}