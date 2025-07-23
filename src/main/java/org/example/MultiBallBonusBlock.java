package org.example;

import javafx.scene.paint.Color;

public class MultiBallBonusBlock extends Block {
    public MultiBallBonusBlock(double x, double y, double width, double height) {
        super(x, y, width, height, Color.PURPLE);
        rect.setStroke(Color.WHITE);
    }

    @Override
    public void onHit(GameContext context) {
        context.addExtraBalls(2); // Добавляем 2 новых шара (вместе с текущим будет 3)
    }
}