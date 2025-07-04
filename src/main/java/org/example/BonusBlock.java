package org.example;

import javafx.scene.paint.Color;

public class BonusBlock extends Block {
    public BonusBlock(double x, double y, double width, double height) {
        super(x, y, width, height, Color.GOLD);
        rect.setStroke(Color.ORANGERED);
    }

    @Override
    public void onHit(GameContext context) {
        context.activatePaddleBonus();
    }
}