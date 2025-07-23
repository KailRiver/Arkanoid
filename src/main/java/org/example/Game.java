package org.example;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Optional;

public class Game {
    private final Stage stage;
    private final GameState gameState;
    private final RenderManager renderManager;
    private final CollisionManager collisionManager;
    private final InputHandler inputHandler;
    private final GameContext gameContext;
    private MediaPlayer backgroundMusic;
    private Timeline gameLoop;
    private BackgroundImage gameBackground;

    public Game(Stage primaryStage, Difficulty difficulty) {
        this.stage = primaryStage;
        this.gameState = new GameState(difficulty);
        this.renderManager = new RenderManager();
        this.collisionManager = new CollisionManager();
        this.inputHandler = new InputHandler();
        this.gameContext = new GameContext(gameState);

        initializeGame();
        loadBackgroundMusic();
    }

    private void loadBackgroundMusic() {
        try {
            Media sound = new Media(getClass().getResource("/game_music.mp3").toExternalForm());
            backgroundMusic = new MediaPlayer(sound);
            backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundMusic.setVolume(0.3);
            backgroundMusic.play();
        } catch (Exception e) {
            System.out.println("Could not load background music: " + e.getMessage());
        }
    }

    private void initializeGame() {
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

    private void startGameLoop() {
        gameLoop = new Timeline(new KeyFrame(Duration.millis(16), e -> update()));
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.play();
    }

    private void update() {
        if (gameState.isGamePaused()) return;

        inputHandler.handleInput(gameState);
        gameState.getBall().move();

        for (Ball ball : gameContext.getExtraBalls()) {
            ball.move();
        }

        collisionManager.checkCollisions(gameState, gameContext);
        checkGameConditions();
        renderManager.updateGameInfo(gameState);
        renderManager.renderGameObjects(gameState, gameContext);
    }

    private void checkGameConditions() {
        boolean mainBallLost = gameState.getBall().getY() > 600;
        boolean extraBallsLost = gameContext.getExtraBalls().isEmpty() ||
                gameContext.getExtraBalls().stream().allMatch(ball -> ball.getY() > 600);

        if (mainBallLost && extraBallsLost) {
            handleLifeLost();
        } else if (mainBallLost) {
            gameState.getBall().reset(400, 300);
        }

        if (gameState.getBlocks().stream().noneMatch(Block::isDestructible)) {
            handleLevelComplete();
        }
    }

    private void loadLevel(int level) {
        gameState.setBlocks(Level.createLevel(level));
        renderManager.renderGameObjects(gameState, gameContext);
    }

    public void pauseGame() {
        gameState.setGamePaused(true);
        renderManager.showPauseMenu();
        if (backgroundMusic != null) {
            backgroundMusic.pause();
        }
    }

    public void resumeGame() {
        gameState.setGamePaused(false);
        renderManager.hidePauseMenu();
        if (backgroundMusic != null) {
            backgroundMusic.play();
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
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }

        Platform.runLater(() -> {
            TextInputDialog dialog = new TextInputDialog("Player");
            dialog.setTitle("Game Over");
            dialog.setHeaderText("Your score: " + gameState.getScore());
            dialog.setContentText("Enter your name:");

            Optional<String> result = dialog.showAndWait();
            String playerName = result.orElse("Anonymous");

            HighScoreManager highScoreManager = new HighScoreManager();
            highScoreManager.addScore(playerName, gameState.getScore());

            renderManager.showHighScores(highScoreManager.getHighScores(), () -> {
                stage.close();
                new MainMenu(new Stage());
            });
        });
    }

    private void gameWon() {
        gameState.setGameRunning(false);
        gameLoop.stop();
        Platform.runLater(() -> {
            renderManager.showEndGameMessage("Congratulations! Final Score: " + gameState.getScore(),
                    true, this::returnToMainMenu);
        });
    }

    private void returnToMainMenu() {
        stage.close();
        new MainMenu(new Stage());
    }
}