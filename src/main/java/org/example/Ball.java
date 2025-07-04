package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.effect.DropShadow;

public class Ball {
    private Circle circle;
    private double dx;
    private double dy;

    public Ball(double x, double y, double radius, Color color) {
        circle = new Circle(radius, color);
        circle.setCenterX(x);
        circle.setCenterY(y);

        DropShadow glow = new DropShadow();
        glow.setColor(color.brighter());
        glow.setRadius(10);
        glow.setSpread(0.5);

        circle.setEffect(glow);
    }

    public void move() {
        circle.setCenterX(circle.getCenterX() + dx);
        circle.setCenterY(circle.getCenterY() + dy);
    }

    public void reverseX() {
        dx *= -1;
    }

    public void reverseY() {
        dy *= -1;
    }

    public void reset(double x, double y) {
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    public double getX() {
        return circle.getCenterX();
    }

    public double getY() {
        return circle.getCenterY();
    }

    public double getRadius() {
        return circle.getRadius();
    }

    public Circle getCircle() {
        return circle;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }
}