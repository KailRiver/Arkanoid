package org.example;

import javafx.scene.paint.Color;
import java.util.List;
import java.util.ArrayList;

public class GameState {
    private int score;
    private int lives;
    private int currentLevel;
    private boolean gameRunning;
    private boolean gamePaused;

    private final Difficulty difficulty;
    private Ball ball;
    private Paddle paddle;
    private List<Block> blocks;

    public GameState(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.blocks = new ArrayList<>();
        reset();
    }

    public void reset() {
        this.score = 0;
        this.lives = 3;
        this.currentLevel = 1;
        this.gameRunning = true;
        this.gamePaused = false;

        this.ball = new Ball(400, 300, 10, Color.WHITE);
        this.paddle = new Paddle(350, 550, 100, 20, difficulty.getPaddleSpeed());

        ball.setDx(difficulty.getBallSpeed() * (Math.random() > 0.5 ? 1 : -1));
        ball.setDy(-difficulty.getBallSpeed());
    }

    public int getScore() { return score; }
    public void addScore(int points) { score += points; }
    public int getLives() { return lives; }
    public void loseLife() { lives--; }
    public int getCurrentLevel() { return currentLevel; }
    public void nextLevel() { currentLevel++; }
    public boolean isGameRunning() { return gameRunning; }
    public void setGameRunning(boolean running) { gameRunning = running; }
    public boolean isGamePaused() { return gamePaused; }
    public void setGamePaused(boolean paused) { gamePaused = paused; }
    public Difficulty getDifficulty() { return difficulty; }
    public Ball getBall() { return ball; }
    public Paddle getPaddle() { return paddle; }
    public List<Block> getBlocks() { return blocks; }
    public void setBlocks(List<Block> blocks) { this.blocks = blocks; }
}