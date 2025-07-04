package org.example;

import javafx.geometry.Bounds;
import java.util.Iterator;
import java.util.List;

public class CollisionManager {
    private static final double GAME_WIDTH = 800;
    private static final double GAME_HEIGHT = 600;

    public void checkCollisions(GameState gameState) {
        checkWallCollisions(gameState);
        checkPaddleCollision(gameState);
        checkBlocksCollision(gameState);
    }

    private void checkWallCollisions(GameState gameState) {
        Ball ball = gameState.getBall();
        double radius = ball.getRadius();

        if (ball.getX() <= radius || ball.getX() >= GAME_WIDTH - radius) {
            ball.reverseX();
        }
        if (ball.getY() <= radius) {
            ball.reverseY();
        }
    }

    private void checkPaddleCollision(GameState gameState) {
        Ball ball = gameState.getBall();
        Paddle paddle = gameState.getPaddle();

        if (ball.getCircle().getBoundsInParent().intersects(paddle.getRect().getBoundsInParent())) {
            double paddleCenter = paddle.getRect().getX() + paddle.getRect().getWidth() / 2;
            double ballX = ball.getX();
            double offset = (ballX - paddleCenter) / (paddle.getRect().getWidth() / 2);
            ball.setDx(offset * gameState.getDifficulty().getBallSpeed());
            ball.reverseY();
        }
    }

    private void checkBlocksCollision(GameState gameState) {
        Ball ball = gameState.getBall();
        List<Block> blocks = gameState.getBlocks();

        Iterator<Block> iter = blocks.iterator();
        while (iter.hasNext()) {
            Block block = iter.next();
            if (ball.getCircle().getBoundsInParent().intersects(block.getRect().getBoundsInParent())) {
                handleBlockCollision(ball, block, iter, gameState);
                break;
            }
        }
    }

    private void handleBlockCollision(Ball ball, Block block, Iterator<Block> iter, GameState gameState) {
        if (ball.getX() < block.getRect().getX() ||
                ball.getX() > block.getRect().getX() + block.getRect().getWidth()) {
            ball.reverseX();
        } else {
            ball.reverseY();
        }

        block.onHit(new GameContext(gameState));

        if (block.isDestructible()) {
            iter.remove();
            gameState.addScore(gameState.getDifficulty().getPointsPerBlock());
        }
    }
}