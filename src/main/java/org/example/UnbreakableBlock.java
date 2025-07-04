package org.example;

import javafx.scene.paint.Color;

public class UnbreakableBlock extends Block {
    public UnbreakableBlock(double x, double y, double width, double height) {
        super(x, y, width, height, Color.GRAY);
        rect.setStroke(Color.DARKGRAY);
    }

    @Override
    public boolean isDestructible() {
        return false;
    }
}