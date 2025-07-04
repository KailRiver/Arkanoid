package org.example;

import java.io.*;

public class ScoreManager {
    private int score;
    private int highScore;
    private final String HIGH_SCORE_FILE = "highscore.dat";

    public ScoreManager() {
        score = 0;
        loadHighScore();
    }

    public void addPoints(int points) {
        score += points;
        if (score > highScore) {
            highScore = score;
            saveHighScore();
        }
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void reset() {
        score = 0;
    }

    private void loadHighScore() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(HIGH_SCORE_FILE))) {
            highScore = (int) ois.readObject();
        } catch (Exception e) {
            highScore = 0;
        }
    }

    private void saveHighScore() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HIGH_SCORE_FILE))) {
            oos.writeObject(highScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}