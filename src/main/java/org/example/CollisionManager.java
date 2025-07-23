package org.example;

import javafx.geometry.Bounds;
import java.util.Iterator;
import java.util.List;

public class CollisionManager {
    private static final double GAME_WIDTH = 800;
    private static final double GAME_HEIGHT = 600;

    public void checkCollisions(GameState gameState, GameContext context) {
        checkWallCollisions(gameState, context);
        checkPaddleCollision(gameState, context);
        checkBlocksCollision(gameState, context);
    }

    private void checkWallCollisions(GameState gameState, GameContext context) {
        // Проверка для основного шара
        checkBallWallCollision(gameState.getBall());

        // Проверка для дополнительных шаров
        for (Ball ball : context.getExtraBalls()) {
            checkBallWallCollision(ball);
        }
    }

    private void checkBallWallCollision(Ball ball) {
        double radius = ball.getRadius();

        if (ball.getX() <= radius || ball.getX() >= GAME_WIDTH - radius) {
            ball.reverseX();
        }
        if (ball.getY() <= radius) {
            ball.reverseY();
        }
    }

    private void checkPaddleCollision(GameState gameState, GameContext context) {
        Paddle paddle = gameState.getPaddle();

        // Проверка для основного шара
        checkBallPaddleCollision(gameState.getBall(), paddle, gameState.getDifficulty());

        // Проверка для дополнительных шаров
        for (Ball ball : context.getExtraBalls()) {
            checkBallPaddleCollision(ball, paddle, gameState.getDifficulty());
        }
    }

    private void checkBallPaddleCollision(Ball ball, Paddle paddle, Difficulty difficulty) {
        if (ball.getCircle().getBoundsInParent().intersects(paddle.getRect().getBoundsInParent())) {
            double paddleCenter = paddle.getRect().getX() + paddle.getRect().getWidth() / 2;
            double ballX = ball.getX();
            double offset = (ballX - paddleCenter) / (paddle.getRect().getWidth() / 2);
            ball.setDx(offset * difficulty.getBallSpeed());
            ball.reverseY();
        }
    }

    private void checkBlocksCollision(GameState gameState, GameContext context) {
        List<Block> blocks = gameState.getBlocks();

        // Проверка для основного шара
        checkBallBlocksCollision(gameState.getBall(), blocks, gameState, context);

        // Проверка для дополнительных шаров
        Iterator<Ball> ballIterator = context.getExtraBalls().iterator();
        while (ballIterator.hasNext()) {
            Ball ball = ballIterator.next();
            if (ball.getY() > 600) {
                ballIterator.remove();
                continue;
            }
            checkBallBlocksCollision(ball, blocks, gameState, context);
        }
    }

    private void checkBallBlocksCollision(Ball ball, List<Block> blocks, GameState gameState, GameContext context) {
        Iterator<Block> iter = blocks.iterator();
        while (iter.hasNext()) {
            Block block = iter.next();
            if (ball.getCircle().getBoundsInParent().intersects(block.getRect().getBoundsInParent())) {
                handleBlockCollision(ball, block, iter, gameState, context);
                break;
            }
        }
    }

    private void handleBlockCollision(Ball ball, Block block, Iterator<Block> iter, GameState gameState, GameContext context) {
        if (ball.getX() < block.getRect().getX() ||
                ball.getX() > block.getRect().getX() + block.getRect().getWidth()) {
            ball.reverseX();
        } else {
            ball.reverseY();
        }

        block.onHit(context);

        if (block.isDestructible()) {
            iter.remove();
            gameState.addScore(gameState.getDifficulty().getPointsPerBlock());
        }
    }
}