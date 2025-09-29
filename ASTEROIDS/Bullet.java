import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Bullet {

    private double x;
    private double y;
    private final double rotation; //Rotation in radians
    private final double xVelocity;
    private final double yVelocity;
    private final double SPEED = 8.0;
    private final int RADIUS = 3;
    private Timer lifeTimer;
    private boolean alive = true; //Variable to show how long bullet lasts and if it is dead
    private final JPanel gamePanel;

    //Constructor
    public Bullet(double x, double y, double rotation, JPanel panel) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.gamePanel = panel;
        this.xVelocity = SPEED * Math.cos(rotation);
        this.yVelocity = SPEED * Math.sin(rotation);

        //Timer for bullet lifetime
        lifeTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alive = false;
                lifeTimer.stop();
            }
        });
        lifeTimer.setRepeats(false);
        lifeTimer.start();
    }

    //Movement code
    public void move() {
        x += xVelocity;
        y += yVelocity;

        //Screen wrapping logic
        if (x < 0) {
            x = gamePanel.getWidth();
        }
        if (x > gamePanel.getWidth()) {
            x = 0;
        }
        if (y < 0) {
            y = gamePanel.getHeight();
        }
        if (y > gamePanel.getHeight()) {
            y = 0;
        }
    }

    //Draw bullet
    public void draw(Graphics2D g2d) {
        if (alive) {
            g2d.setColor(Color.RED);
            g2d.fillOval((int) (x - RADIUS), (int) (y - RADIUS), 2 * RADIUS, 2 * RADIUS);
        }
    }

    //Getters and setters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return RADIUS;
    }

    public boolean isAlive() {
        return alive;
    }
}