package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle {
    private Rectangle rect;
    private double originalWidth;
    private double speed;

    public Paddle(double x, double y, double width, double height, double speed) {
        this.originalWidth = width;
        this.speed = speed;

        rect = new Rectangle(x, y, width, height);
        rect.setFill(Color.WHITE);
        rect.setArcWidth(20);
        rect.setArcHeight(20);
        rect.setStroke(Color.DODGERBLUE);
        rect.setStrokeWidth(3);
        rect.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0;");
    }

    public void moveLeft() {
        double newX = rect.getX() - speed;
        rect.setX(Math.max(newX, 0));
    }

    public void moveRight() {
        double newX = rect.getX() + speed;
        rect.setX(Math.min(newX, 800 - rect.getWidth()));
    }

    public void enlarge() {
        rect.setWidth(originalWidth * 1.5);
    }

    public void resetSize() {
        rect.setWidth(originalWidth);
    }

    public void reset(double x, double y) {
        rect.setX(x);
        rect.setY(y);
    }

    public Rectangle getRect() {
        return rect;
    }
}