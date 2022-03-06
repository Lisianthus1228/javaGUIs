import javax.swing.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;

public class project3_flappyBird extends JFrame {
    JMenuBar menubar;
    JMenu file, difficulty;
    JMenuItem reset, exit, easy, medium, hard, insane;
    
    ScheduledExecutorService scheduler;
    Runnable updateGameRunnable, updateWallsRunnable, throwWallsRunnable;
    ScheduledFuture<?> updateGameHandle, updateWallsHandle, throwWallsHandle;
    
    Font gameFont = new Font("Consolas", Font.BOLD, 18);
    Font menuFont = new Font("Courier New", Font.BOLD, 28);
    String[][] gameArea = new String[16][16];
    JLabel[][] displayArea = new JLabel[16][16];
    JPanel boardPanel, statsPanel;
    JLabel userScore;
    JButton pauseButton;
    
    boolean paused = true;
    int score = 0;
    int birdX = 4;
    int birdY = 7;
    int gameSpeed = 400; // (ms)
    int topGap = 0;
    int wallTimer = 2000; // (ms)
    int wallTimerDelay = 2000; // (ms)
    
    public project3_flappyBird() {
        Container pane = this.getContentPane();
        setLayout(new BorderLayout());
        // Menu Bar
        menubar = new JMenuBar();
        setJMenuBar(menubar);
        file = new JMenu("File");
        menubar.add(file);
        reset = new JMenuItem("Reset");
        file.add(reset);
        exit = new JMenuItem("Exit");
        file.add(exit);
        ResetEvent re = new ResetEvent();
        reset.addActionListener(re);
        ExitEvent ee = new ExitEvent();
        exit.addActionListener(ee);
        
        difficulty = new JMenu("Difficulty");
        menubar.add(difficulty);
        easy = new JMenuItem("Easy");
        difficulty.add(easy);
        medium = new JMenuItem("Medium");
        difficulty.add(medium);
        hard = new JMenuItem("Hard");
        difficulty.add(hard);
        insane = new JMenuItem("INSANE");
        difficulty.add(insane);
        DifficultyEvent de = new DifficultyEvent();
        easy.addActionListener(de);
        medium.addActionListener(de);
        hard.addActionListener(de);
        insane.addActionListener(de);
        
        // GAME AREA
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(16,16));
        boardPanel.setBackground(new Color(245,245,245));
        boardPanel.setFocusable(true); // Allow user to focus on game display, game will pause if unfocused and inputs will not register.
        boardPanel.requestFocusInWindow(); // Sets focus to boardPanel automatically when GUI window is active.
        controlEvent e = new controlEvent();
        boardPanel.addKeyListener(e);
        for(int i=0; i < displayArea.length; i++) { // Intialize display area as having all empty strings.
            for(int j=0; j < displayArea.length; j++) {
                displayArea[i][j] = new JLabel(" ");
                displayArea[i][j].setFont(gameFont);
            }
        }
        resetGameArea();
        for(int i=0; i < gameArea.length; i++) {
            for(int j=0; j < displayArea.length; j++) {
                boardPanel.add(displayArea[i][j]);
            }
        }
        pane.add(boardPanel, BorderLayout.CENTER);
        
        // UI AREA
        statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2,1,1,10));
        statsPanel.setPreferredSize(new Dimension(304, -1));
        userScore = new JLabel("SCORE: 0");
        userScore.setFont(menuFont);
        statsPanel.add(userScore);
        pauseButton = new JButton("Unpause game");
        statsPanel.add(pauseButton);
        PauseEvent pe = new PauseEvent();
        pauseButton.addActionListener(pe);
        pane.add(statsPanel, BorderLayout.LINE_END);
        
        // Handle game update speed
        scheduler = Executors.newScheduledThreadPool(1);
        updateGameRunnable = new Runnable() {
           public void run() {
               if(!paused) {
                   updateGame();
               }
           }
        };
        updateGameHandle = scheduler.scheduleWithFixedDelay(updateGameRunnable, gameSpeed, gameSpeed, TimeUnit.MILLISECONDS); //Scheduler runs updateGame(); every 500ms (default)
        
        updateWallsRunnable = new Runnable() {
            public void run() {
                if(!paused) {
                    wallTimer--;
                    if(wallTimer == 0) {
                        createNewWall(2);
                        wallTimer = wallTimerDelay;
                        score++;
                        userScore.setText("SCORE: "+score);
                    } else if(wallTimer == 1500) {
                        createNewWall(1);
                    }
                }
            }
        };
        updateWallsHandle = scheduler.scheduleWithFixedDelay(updateWallsRunnable, 1, 1, TimeUnit.MILLISECONDS);
        
        throwWallsRunnable = new Runnable() {
            public void run() {
                if(!paused) {
                    throwWall();
                }
            }
        };
        throwWallsHandle = scheduler.scheduleWithFixedDelay(throwWallsRunnable, 85, 85, TimeUnit.MILLISECONDS);
    }
    
     public class controlEvent implements KeyListener {
        public void keyPressed(KeyEvent e) { // Control events
            if(!paused) {
                gameArea[birdY][birdX] = " ";
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_SPACE:
                        birdY -= 1;
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        birdY += 1;
                        break;
                }
                collisionCheck();
                gameArea[birdY][birdX] = "b";
                updateDisplayArea();
            }
        }
        
        public void keyReleased(KeyEvent e) {};
        public void keyTyped(KeyEvent e) {}; // Necessary overrides, no function.
    }
    
    public class DifficultyEvent implements ActionListener { // CHANGE DIFFICULTY 
        public void actionPerformed(ActionEvent de) {
            Object obj = de.getSource();
            if(obj instanceof JMenuItem) {
                JMenuItem difficultySetting = (JMenuItem) obj; // Find which menuItem is calling this event.
                String difficultyString = difficultySetting.getText();
                switch(difficultyString) {
                    case "Easy":
                        gameSpeed = 400;
                        break;
                    case "Medium":
                        gameSpeed = 280;
                        break;
                    case "Hard":
                        gameSpeed = 230;
                        break;
                    case "INSANE":
                        gameSpeed = 150;
                        break;
                }
                changeReadInterval(gameSpeed);
            }
        }
    }
    
    public class PauseEvent implements ActionListener { // PAUSE GAME
        public void actionPerformed(ActionEvent pe) {
            pauseGame();
            boardPanel.requestFocusInWindow();
        }
    }
    public void pauseGame() { // Made as a procedure so that multiple Event classes may call it.
        if(paused) {
            paused = false;
            pauseButton.setText("Pause game");
        } else {
            paused = true;
            pauseButton.setText("Unpause game");
        }
    }
    public void endGame() {
        paused = false;
        pauseGame();
        pauseButton.setText("GAME OVER");
        pauseButton.setEnabled(false);
        boardPanel.setBackground(new Color(145,145,145));
    }
    
    public class ResetEvent implements ActionListener { // RESET GAME
        public void actionPerformed(ActionEvent re) {
            paused = false;
            pauseGame();
            pauseButton.setEnabled(true);
            boardPanel.setBackground(new Color(245,245,245));
            score = 0;
            resetGameArea();
        }
    }
    
    public class ExitEvent implements ActionListener {
        public void actionPerformed(ActionEvent ee) {
            System.exit(0);
        }
    }
    
    public void updateGame() {
        // Drag bird downwards
        gameArea[birdY][birdX] = " "; // Kill bird.
        birdY += 1;
        collisionCheck();
        gameArea[birdY][birdX] = "b"; // Resurrect bird in new spot.
        updateDisplayArea();
    }
    
    public void createNewWall(int stage) {
        if(stage == 1) {
            // Create gap in wall to go through
            topGap = (int) (Math.random()*11) + 1;
            for(int j=1; j < displayArea.length-1; j++) {
                if(j!=topGap && j!=topGap+1 && j!=topGap+2) {
                    gameArea[j][15] = "!";
                }
            }
        }
        
        if(stage == 2) {
            for(int j=1; j < displayArea.length-1; j++) {
                if(j!=topGap && j!=topGap+1 && j!=topGap+2) {
                    gameArea[j][15] = "#";
                }
            }
        }  
    }
    public void throwWall() {
        for(int i=0; i < gameArea.length; i++) {
            if(gameArea[1][i].equals("#")) { // If it finds a wall, move entire column to the left.
                for(int j=1; j < 15; j++) {
                    if(i-1 >= 0 && (j < topGap || j > topGap+2)) {
                        gameArea[j][i-1] = "#";
                        gameArea[j][i] = " ";
                    } else {
                        gameArea[j][i] = " ";
                    }
                }
            }
        }
        collisionCheck();
    }
    
    public void collisionCheck() {
        if(gameArea[birdY][birdX].equals("■") || gameArea[birdY][birdX].equals("#")) {
            endGame();
        }
    }
    
    public void resetGameArea() {
        for(int i=0; i < gameArea.length; i++) {
            for(int j=0; j < gameArea.length; j++) {
                if(i==0 || i==15) {
                    gameArea[i][j] = "■";
                } else {
                    gameArea[i][j] = " ";
                }
            }
        }
        birdY = 7;
        gameArea[birdY][birdX] = "b";
        updateDisplayArea();
    }
    
    public void updateDisplayArea() {
        for(int i=0; i < gameArea.length; i++) {
            for(int j=0; j < gameArea.length; j++) {
                displayArea[i][j].setText(gameArea[i][j]);
            }
        }
    }
    
    public void changeReadInterval(long time) {
        if(time > 0) {       
            if (updateGameHandle != null) {
                updateGameHandle.cancel(true);
            }
    
            updateGameHandle = scheduler.scheduleWithFixedDelay(updateGameRunnable, gameSpeed, gameSpeed, TimeUnit.MILLISECONDS);
        }
    }
    
    public static void main(String[] args) {
        project3_flappyBird newGui = new project3_flappyBird();
        newGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGui.setSize(640, 396);
        newGui.setResizable(false);
        newGui.setVisible(true);
        newGui.setTitle("Project 3: Flappy Bird");
    }
}
