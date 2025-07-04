package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;

public class Block {
    protected Rectangle rect;

    public Block(double x, double y, double width, double height, Color color) {
        rect = new Rectangle(x, y, width, height);
        rect.setFill(color);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setStroke(Color.WHITE);
        rect.setStrokeWidth(2);
        rect.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 0, 1);");
    }

    public Rectangle getRect() {
        return rect;
    }

    public Bounds getBounds() {
        return rect.getBoundsInParent();
    }

    public void onHit(GameContext context) {
        // Базовый блок не имеет специального поведения
    }

    public boolean isDestructible() {
        return true;
    }
}