package org.example;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class InputHandler {
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public void setupInputHandlers(Scene scene, GameState gameState, Game game) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) leftPressed = true;
            if (e.getCode() == KeyCode.RIGHT) rightPressed = true;
            if (e.getCode() == KeyCode.ESCAPE) {
                if (gameState.isGamePaused()) {
                    game.resumeGame();
                } else {
                    game.pauseGame();
                }
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) leftPressed = false;
            if (e.getCode() == KeyCode.RIGHT) rightPressed = false;
        });
    }

    public void handleInput(GameState gameState) {
        if (!gameState.isGamePaused()) {
            if (leftPressed) gameState.getPaddle().moveLeft();
            if (rightPressed) gameState.getPaddle().moveRight();
        }
    }
}