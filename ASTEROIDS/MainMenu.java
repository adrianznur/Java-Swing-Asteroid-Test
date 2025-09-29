import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainMenu extends JFrame {

    private final JPanel menuPanel;
    private final JLabel titleLabel;
    private final JButton startButton;
    private final JLabel instructionsLabel;
    private final JRadioButton normalButton;
    private final JRadioButton challengeButton;
    private final ButtonGroup buttonGroup;
    private final JButton quitButton;
    private final JLabel highScoreLabel;
    private int highScore = 0;

    //Constructor for Main Menu
    public MainMenu() {
        setTitle("Asteroids Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(Color.BLACK);

        //Title
        titleLabel = new JLabel("Asteroids");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Impact", Font.BOLD, 60));
        titleLabel.setBounds(200, 50, 400, 100);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        menuPanel.add(titleLabel);

        //Difficulty Options
        normalButton = new JRadioButton("Normal Mode");
        normalButton.setForeground(Color.WHITE);
        normalButton.setBackground(Color.BLACK);
        normalButton.setBounds(330, 150, 200, 30);
        normalButton.setSelected(true); //Normal selected by default
        menuPanel.add(normalButton);

        challengeButton = new JRadioButton("Challenge Mode");
        challengeButton.setForeground(Color.WHITE);
        challengeButton.setBackground(Color.BLACK);
        challengeButton.setBounds(330, 175, 200, 30);
        menuPanel.add(challengeButton);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(normalButton);
        buttonGroup.add(challengeButton);

        //Start Game Button
        startButton = new JButton("Start Game");
        startButton.setBounds(300, 220, 200, 50);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        menuPanel.add(startButton);

        //High Score Display
        highScoreLabel = new JLabel("High Score: 0");
        highScoreLabel.setForeground(Color.YELLOW);
        highScoreLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        highScoreLabel.setBounds(300, 280, 200, 30);
        menuPanel.add(highScoreLabel);

        //Quit Button
        quitButton = new JButton("Quit Game");
        quitButton.setBounds(300, 325, 200, 50);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuPanel.add(quitButton);

        //Instructions with formatting
        instructionsLabel = new JLabel(
            "<html><center>Controls:<br>Left/Right Arrows - Rotate<br>"+
            "Up Arrow - Accelerate<br>Space - Shoot<br><br>Challenge Mode:<br>"+
            "More Asteroids with Increased Speed</center></html>");
        instructionsLabel.setForeground(Color.WHITE);
        instructionsLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        instructionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        instructionsLabel.setBounds(200, 400, 400, 120);
        menuPanel.add(instructionsLabel);

        add(menuPanel);
        setVisible(true);
    }

    //Update high score function each time a game over occurs
    public void updateHighScoreLabel(int highScore) {
        if (highScore > this.highScore) {
            this.highScore = highScore;
        }
        highScoreLabel.setText("High Score: " + this.highScore);
    }

    //Starts the game
    private void startGame() {
        this.setVisible(false);
        JFrame gameFrame = new JFrame("Asteroids");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(800, 600);
        gameFrame.setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel(challengeButton.isSelected(), this);
        gameFrame.add(gamePanel);
        gameFrame.setVisible(true);
    }
}