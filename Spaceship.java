import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.*;

public class Spaceship {
    private double x;
    private double y;
    private double rotation; //Rotation in radians
    private double xVelocity;
    private double yVelocity;

    private final JPanel gamePanel; // Reference to the game panel
    private final int[] xPoints = new int[] {-5, 9, -5, 0};
    private final int[] yPoints = new int[] {7, 0, -7, 0};
    private final int nPoints = 4;//Initial points to create spaceship polygon
    private final double maxSpeed = 5.0;
    private final double decelRate = 0.02;

    //Constructor
    public Spaceship(double x, double y, JPanel panel) {
        this.x = x;
        this.y = y;
        this.rotation = 0;
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.gamePanel = panel;
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

        //Deceleration on spaceship to prevent feeling like you're sliding on ice
        if (xVelocity >= 0.2 || yVelocity >= 0.2) {
            double currentSpeed = Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
            if (currentSpeed > 0) {
                double decelerationX = -xVelocity / currentSpeed * decelRate;
                double decelerationY = -yVelocity / currentSpeed * decelRate;

                xVelocity += decelerationX;
                yVelocity += decelerationY;

                //Stop decelerating if velocity is close enough to 0
                if (Math.abs(xVelocity) < 0.01) {
                    xVelocity = 0;
                }
                if (Math.abs(yVelocity) < 0.01) {
                    yVelocity = 0;
                }
            }
        }
    }

    public void rotate(double angleChange) {
        rotation += angleChange;
    }

    //Adds to the acceleration in accordance to rotation
    public void accelerate(double acceleration) {
        xVelocity += acceleration * Math.cos(rotation);
        yVelocity += acceleration * Math.sin(rotation);
        
        //Speed limit prevents the spaceship from passing through to parallel dimensions
        double currentSpeed = Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
        if (currentSpeed > maxSpeed) {
            double ratio = maxSpeed / currentSpeed;
            xVelocity *= ratio;
            yVelocity *= ratio;
        }
    }

    //Redraw the spaceship
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.translate((int)x, (int)y);
        g2d.rotate(rotation);
        g2d.fillPolygon(xPoints, yPoints, nPoints);
        g2d.rotate(-rotation);
        g2d.translate(-(int)x, -(int)y);
    }

    //Getters and setters
    public void setX(float point) {
        x = point;
    }

    public double getX() {
        return x;
    }

    public void setY(float point) {
        y = point;
    }

    public double getY() {
        return y;
    }

    public double getRotation() {
        return rotation;
    }
}