package org.example;

import java.io.*;
import java.util.*;

public class HighScoreManager {
    private static final String FILE_NAME = "highscores.dat";
    private final List<HighScore> highScores = new ArrayList<>();

    public HighScoreManager() {
        loadHighScores();
    }

    public void addScore(String playerName, int score) {
        highScores.add(new HighScore(playerName, score));
        highScores.sort((h1, h2) -> Integer.compare(h2.getScore(), h1.getScore()));

        if (highScores.size() > 10) {
            highScores.subList(10, highScores.size()).clear();
        }

        saveHighScores();
    }

    public List<HighScore> getHighScores() {
        return new ArrayList<>(highScores);
    }

    private void loadHighScores() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            @SuppressWarnings("unchecked")
            List<HighScore> loadedScores = (List<HighScore>) ois.readObject();
            highScores.clear();
            highScores.addAll(loadedScores);
        } catch (Exception e) {
            System.out.println("Creating new high scores file");
        }
    }

    private void saveHighScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(highScores);
        } catch (IOException e) {
            System.err.println("Error saving high scores: " + e.getMessage());
        }
    }

    public static class HighScore implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String playerName;
        private final int score;
        private final Date date;

        public HighScore(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
            this.date = new Date();
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getScore() {
            return score;
        }

        public Date getDate() {
            return date;
        }
    }
}