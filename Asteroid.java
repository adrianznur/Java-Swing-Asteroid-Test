import java.awt.*;

public class Asteroid {

    private double x;
    private double y;
    private final double rotation; //Rotation in radians
    private final double xVelocity;
    private final double yVelocity;
    private int radius;
    private final int size;
    private final GamePanel gamePanel;
    private final Color asteroidColor;

    //Constructor
    public Asteroid(double x, double y, int size, boolean challengeMode, GamePanel gamePanel) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.gamePanel = gamePanel;
        this.radius = size;
        this.rotation = Math.random() * 2 * Math.PI;

        //Increases speed by a bit if hard mode is enabled
        double speed = Math.random() * 2 + 1;
        if (challengeMode) {
            speed += 0.5;
        }

        this.xVelocity = speed * Math.cos(Math.random() * 2 * Math.PI);
        this.yVelocity = speed * Math.sin(Math.random() * 2 * Math.PI);

        //Generate a random gray color
        //Gray values between 100 and 255 for hex color codes, helps differentiate the
        //asteroids from each other if they're all bunched up together
        int grayValue = (int) (Math.random() * 156 + 100);
        asteroidColor = new Color(grayValue, grayValue, grayValue);
    }

    //Movement code
    public void move() {
        x += xVelocity;
        y += yVelocity;

        //Screen wrapping logic
        if (x < -radius) {
            x = (gamePanel.getWidth() + radius);
        }
        if (x > gamePanel.getWidth() +radius) {
            x = -radius;
        }
        if (y < -radius) {
            y = (gamePanel.getHeight()+ radius);
        }
        if (y > gamePanel.getHeight() + radius) {
            y = -radius;
        }
    }

    //Draw Asteroid
    public void draw(Graphics2D g2d) {
        g2d.setColor(asteroidColor);
        this.radius = size * 20;
        g2d.fillOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
    }

    //Getters and Setters
    public void setX(double point) {
        x = point;
    }

    public double getX() {
        return x;
    }

    public void setY(double point) {
        y = point;
    }

    public double getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getRadius() {
        return radius;
    }
}