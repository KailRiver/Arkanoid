package org.example;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game {
    private final Stage stage;
    private final GameState gameState;
    private final RenderManager renderManager;
    private final CollisionManager collisionManager;
    private final InputHandler inputHandler;
    private Timeline gameLoop;
    private BackgroundImage gameBackground;

    public Game(Stage primaryStage, Difficulty difficulty) {
        this.stage = primaryStage;
        this.gameState = new GameState(difficulty);
        this.renderManager = new RenderManager();
        this.collisionManager = new CollisionManager();
        this.inputHandler = new InputHandler();

        try {
            Image bgImage = new Image(getClass().getResourceAsStream("/game_bg.jpg"));
            this.gameBackground = new BackgroundImage(
                    bgImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, false, true)
            );
        } catch (Exception e) {
            this.gameBackground = null;
        }

        initializeGame();
    }

    private void initializeGame() {
        stage.setTitle("Arkanoid - Level " + gameState.getCurrentLevel());
        Scene scene = renderManager.createScene();

        if (gameBackground != null) {
            renderManager.setBackground(new Background(gameBackground));
        }

        stage.setScene(scene);
        inputHandler.setupInputHandlers(scene, gameState, this);
        loadLevel(gameState.getCurrentLevel());
        startGameLoop();
        stage.show();
    }

    public void pauseGame() {
        gameState.setGamePaused(true);
        renderManager.showPauseMenu();
    }

    public void resumeGame() {
        gameState.setGamePaused(false);
        renderManager.hidePauseMenu();
    }

    public void returnToMainMenu() {
        gameLoop.stop();
        new MainMenu(stage);
    }

    private void loadLevel(int level) {
        gameState.setBlocks(Level.createLevel(level));
        renderManager.renderGameObjects(gameState);
    }

    private void startGameLoop() {
        gameLoop = new Timeline(new KeyFrame(Duration.millis(16), e -> update()));
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.play();
    }

    private void update() {
        if (gameState.isGamePaused()) return;

        inputHandler.handleInput(gameState);
        gameState.getBall().move();
        collisionManager.checkCollisions(gameState);

        checkGameConditions();
        renderManager.updateGameInfo(gameState);
        renderManager.renderGameObjects(gameState);
    }

    private void checkGameConditions() {
        if (gameState.getBall().getY() > 600) {
            handleLifeLost();
        }

        if (gameState.getBlocks().stream().noneMatch(Block::isDestructible)) {
            handleLevelComplete();
        }
    }

    private void handleLifeLost() {
        gameState.loseLife();

        if (gameState.getLives() <= 0) {
            gameOver();
        } else {
            resetBallAndPaddle();
        }
    }

    private void handleLevelComplete() {
        gameState.nextLevel();

        if (gameState.getCurrentLevel() > 3) {
            gameWon();
        } else {
            loadLevel(gameState.getCurrentLevel());
            stage.setTitle("Arkanoid - Level " + gameState.getCurrentLevel());
            resetBallAndPaddle();
        }
    }

    private void resetBallAndPaddle() {
        gameState.getBall().reset(400, 300);
        gameState.getBall().setDx(gameState.getDifficulty().getBallSpeed() * (Math.random() > 0.5 ? 1 : -1));
        gameState.getBall().setDy(-gameState.getDifficulty().getBallSpeed());
        gameState.getPaddle().reset(350, 550);
    }

    private void gameOver() {
        gameState.setGameRunning(false);
        gameLoop.stop();

        // Добавляем небольшую задержку перед показом меню
        new Thread(() -> {
            try {
                Thread.sleep(500); // 0.5 секунды задержки
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                renderManager.showEndGameMessage("Game Over! Score: " + gameState.getScore(),
                        false, () -> {
                            stage.close();
                            new MainMenu(new Stage());
                        });
            });
        }).start();
    }

    private void gameWon() {
        gameState.setGameRunning(false);
        gameLoop.stop();
        Platform.runLater(() -> {
            renderManager.showEndGameMessage("Congratulations! Final Score: " + gameState.getScore(),
                    true, this::returnToMainMenu);
        });
    }
}