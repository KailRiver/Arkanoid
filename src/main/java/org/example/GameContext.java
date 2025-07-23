package org.example;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class GameContext {
    private final GameState gameState;
    private final List<Ball> extraBalls = new ArrayList<>();

    public GameContext(GameState gameState) {
        this.gameState = gameState;
    }

    public void addExtraBalls(int count) {
        for (int i = 0; i < count; i++) {
            Ball newBall = new Ball(
                    gameState.getBall().getX(),
                    gameState.getBall().getY(),
                    gameState.getBall().getRadius(),
                    Color.WHITE
            );
            newBall.setDx(gameState.getDifficulty().getBallSpeed() * (Math.random() > 0.5 ? 1 : -1));
            newBall.setDy(-gameState.getDifficulty().getBallSpeed());
            extraBalls.add(newBall);
        }
    }

    public List<Ball> getExtraBalls() {
        return new ArrayList<>(extraBalls);
    }

    public void removeBall(Ball ball) {
        extraBalls.remove(ball);
    }

    public void activatePaddleBonus() {
        gameState.getPaddle().enlarge();
    }

    public GameState getGameState() {
        return gameState;
    }
}