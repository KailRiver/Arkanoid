package org.example;

public class GameContext {
    private final GameState gameState;

    public GameContext(GameState gameState) {
        this.gameState = gameState;
    }

    public void activatePaddleBonus() {
        gameState.getPaddle().enlarge();
        // Здесь можно добавить таймер для бонуса
    }

    public GameState getGameState() {
        return gameState;
    }
}