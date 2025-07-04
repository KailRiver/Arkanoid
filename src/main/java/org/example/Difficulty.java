package org.example;

public enum Difficulty {
    EASY(3, 5, 100),
    MEDIUM(5, 7, 200),
    HARD(7, 10, 300);

    private final int ballSpeed;
    private final int paddleSpeed;
    private final int pointsPerBlock;

    Difficulty(int ballSpeed, int paddleSpeed, int pointsPerBlock) {
        this.ballSpeed = ballSpeed;
        this.paddleSpeed = paddleSpeed;
        this.pointsPerBlock = pointsPerBlock;
    }

    public int getBallSpeed() {
        return ballSpeed;
    }

    public int getPaddleSpeed() {
        return paddleSpeed;
    }

    public int getPointsPerBlock() {
        return pointsPerBlock;
    }
}