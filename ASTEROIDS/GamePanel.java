import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    //Initial variables
    private final Spaceship player;
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private final ArrayList<Asteroid> asteroids = new ArrayList<>();
    private final double rotationSpeed = 0.1; //Easier to handle player movement here
    private final double acceleration = 0.1;
    private boolean spawned = false; //Ensures player is moved to center of the screen on start
    private boolean gameOver = false;
    private Timer gameOverTimer; //Short delay before getting booted to main menu
    private int highScore = 0;
    private final MainMenu mainMenu; //Reference to main menu for game over

    //Controls
    private boolean leftKeyPressed = false;
    private boolean rightKeyPressed = false;
    private boolean upKeyPressed = false;
    private boolean spaceKeyPressed = false;
    private final int maxBullets = 5;
    private final Timer gameTimer; //Update loop
    private int asteroidCount; // Number of asteroids to respawn
    private int score = 0;
    private final JLabel scoreLabel;
    private final boolean challengeMode;

    //Game constructor
    public GamePanel(boolean challengeMode, MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        this.challengeMode = challengeMode;
        //Increases initial asteroid count if hard mode is active
        if (challengeMode) {
            asteroidCount = 5;
        }
        else {
            asteroidCount = 3;
        }

        //Initialize window, game loop, and player
        this.setFocusable(true);
        this.requestFocusInWindow();
        addKeyListener(this);
        this.setBackground(Color.BLACK);
        gameTimer = new Timer(16, this);
        gameTimer.start();
        player = new Spaceship(400, 300, this);

        //Initialize score
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Impact", Font.BOLD, 16));
        scoreLabel.setHorizontalAlignment(JLabel.RIGHT);
        scoreLabel.setBounds(getWidth() - 150, 10, 140, 20);
        add(scoreLabel);

        //Delay asteroid generation until player is in middle of screen to prevent
        //them from spawning on top of the player because that's just cheap
        SwingUtilities.invokeLater(() -> generateAsteroids(player.getX(), player.getY()));
    }

    //Function to generate asteroids
    private void generateAsteroids(double playerX, double playerY) {
        int maxAsteroids;
        //The max amount of asteroids is more if hard mode is enabled
        if (challengeMode) {
            maxAsteroids = 10;
        }
        else {
            maxAsteroids = 7;
        }

        //Goes through a loop of how many asteroids to spawn
        for (int i = 0; i < asteroidCount; i++) {
            double asteroidX, asteroidY;
            boolean tooClose;
            int radius = 30;

            //First tests a random x and y value for the asteroid, then checks
            //if the asteroid would have spawned within 200 units of the player.
            //If it's too close, keep trying random values until no longer too close
            do {
                asteroidX = Math.random() * getWidth();
                asteroidY = Math.random() * getHeight();
                tooClose = checkCollision(playerX, playerY, 200, asteroidX, asteroidY, radius);
            } while (tooClose);

            //If the asteroid is not too close, add it to the list of asteroids and spawn it
            asteroids.add(new Asteroid(asteroidX, asteroidY, 3, challengeMode, this));
        }
        //If the max amount of asteroids haven't been reached, increase the amount for
        //the next round
        if (asteroidCount < maxAsteroids) {
            asteroidCount++;
        }
    }

    //PaintComponent override for game loop
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //If the game isn't over, draw the update player position as well as
        //the bullets and asteroids, and keep the score visible
        if (!gameOver) {
            scoreLabel.setVisible(true);
            player.draw(g2d);
            for (Bullet bullet : bullets) {
                bullet.draw(g2d);
            }

            for (Asteroid asteroid : asteroids) {
                asteroid.draw(g2d);
            }
        }
        //If the game is over, hide the score label and display the game over text/
        //and the final score
        else {
            scoreLabel.setVisible(false);
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Impact", Font.BOLD, 40));
            String gameOverText = "GAME OVER";
            FontMetrics metrics = g2d.getFontMetrics();
            int x = (getWidth() - metrics.stringWidth(gameOverText)) / 2;
            int y = getHeight() / 2;
            g2d.drawString(gameOverText, x, y);

            g2d.setFont(new Font("Impact", Font.BOLD, 24));
            String finalScoreText = "Final Score: " + score;
            FontMetrics scoreMetrics = g2d.getFontMetrics();
            int scoreX = (getWidth() - scoreMetrics.stringWidth(finalScoreText)) / 2;
            int scoreY = getHeight() / 2 + 30;
            g2d.drawString(finalScoreText, scoreX, scoreY);
        }
    }

    //Keyboard input
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        //Gets movement input
        if (key == KeyEvent.VK_LEFT) {
            leftKeyPressed = true;
        }
        else if (key == KeyEvent.VK_RIGHT) {
            rightKeyPressed = true;
        }
        else if (key == KeyEvent.VK_UP) {
            upKeyPressed = true;
        }
        else if (key == KeyEvent.VK_SPACE) {
            //Creates a bullet only if the max amount of bullets on screen isn't reached
            //and prevents creating an infinite string of bullets if the space key is pressed
            if (!spaceKeyPressed && bullets.size() < maxBullets) {
                bullets.add(new Bullet(player.getX(), player.getY(), player.getRotation(), this));
                spaceKeyPressed = true;
            }
        }
}

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        //Releases movement input
        if (key == KeyEvent.VK_LEFT) {
            leftKeyPressed = false;
        }
        else if (key == KeyEvent.VK_RIGHT) {
            rightKeyPressed = false;
        }
        else if (key == KeyEvent.VK_UP) {
            upKeyPressed = false;
        }
        else if (key == KeyEvent.VK_SPACE) {
            spaceKeyPressed = false;
        }
    }

    //Need this override for class to implement keylistener
    @Override
    public void keyTyped(KeyEvent e) {
    }

    //Updates game loop
    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame();
        if (!gameOver) {
            scoreLabel.setText("Score: " + score);
        }
    }

    public void updateGame() {
        //Handle player movement
        if (!gameOver) {
            if (leftKeyPressed) {
                player.rotate(-rotationSpeed);
            }
            if (rightKeyPressed) {
                player.rotate(rotationSpeed);
            }
            if (upKeyPressed) {
                player.accelerate(acceleration);
            }
            player.move();
            //Initially spawns player in middle of screen
            if (!spawned) {
                player.setX(400);
                player.setY(300);
                spawned = true;
            }

            //Asteroid Physics
            for (int i = asteroids.size() - 1; i >= 0; i--) {
                Asteroid asteroid = asteroids.get(i);
                asteroid.move();
            }

            //Bullet Physics
            for (int i = bullets.size() - 1; i >= 0; i--) {
                Bullet bullet = bullets.get(i);
                bullet.move();
                //Bullets are rid of after a short time
                if (!bullet.isAlive()) {
                    bullets.remove(i);
                }
            }

            // Bullet-Asteroid Collision
            for (int i = bullets.size() - 1; i >= 0; i--) {
                Bullet bullet = bullets.get(i);
                for (int j = asteroids.size() - 1; j >= 0; j--) {
                    Asteroid asteroid = asteroids.get(j);
                    if (checkCollision(bullet.getX(), bullet.getY(), bullet.getRadius(), asteroid.getX(), asteroid.getY(), asteroid.getRadius())) {
                        bullets.remove(i);
                        addScore(asteroid.getSize());
                        splitAsteroid(asteroid);
                        break;
                    }
                }
            }

            //Player-Asteroid Collision
            for(int i = asteroids.size() - 1; i >= 0; i--){
                Asteroid asteroid = asteroids.get(i);
                if ((spawned) && (checkCollision(player.getX(), player.getY(), 9, asteroid.getX(), asteroid.getY(), asteroid.getRadius()))) {
                    gameOver = true;
                    updateHighScore();
                    if(gameOverTimer != null){
                        gameOverTimer.stop();
                    }
                    //Boot back to main menu after a short time and update high score
                    gameOverTimer = new Timer(3000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mainMenu.setVisible(true);
                            mainMenu.updateHighScoreLabel(highScore);
                            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(GamePanel.this);
                            frame.dispose();
                        }
                    });
                    gameOverTimer.setRepeats(false);
                    gameOverTimer.start();
                    break;
                }
            }
            //If all asteroids have been destroyed, start next round
            if(asteroids.isEmpty()){
                generateAsteroids(player.getX(), player.getY());
            }
        }
        repaint();
    }

    //Adds to the score based on the destroyed asteroid's size
    private void addScore(int asteroidSize) {
        if (asteroidSize == 3) {
            score += 500;
        }
        else if (asteroidSize == 2) {
            score += 250;
        }
        else if (asteroidSize == 1) {
            score += 100;
        }
    }

    //Splits an asteroid after hit with a bullet
    private void splitAsteroid(Asteroid asteroid) {
        if (asteroid.getSize() > 1) {
            asteroids.add(new Asteroid(asteroid.getX(), asteroid.getY(), asteroid.getSize() - 1, challengeMode, this));
            asteroids.add(new Asteroid(asteroid.getX(), asteroid.getY(), asteroid.getSize() - 1, challengeMode, this));
        }
        asteroids.remove(asteroid);
    }

    //Collision check for all objects
    private boolean checkCollision(double x1, double y1, double r1, double x2, double y2, double r2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < r1 + r2;
    }

    //Update high score function
    private void updateHighScore() {
        if (score > highScore) {
            highScore = score;
        }
    }
}