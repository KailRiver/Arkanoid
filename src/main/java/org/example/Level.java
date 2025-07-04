package org.example;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Level {
    public static List<Block> createLevel(int level) {
        List<Block> blocks = new ArrayList<>();

        switch (level) {
            case 1:
                createLevel1(blocks);
                break;
            case 2:
                createLevel2(blocks);
                break;
            case 3:
                createLevel3(blocks);
                break;
        }

        return blocks;
    }

    private static void createLevel1(List<Block> blocks) {
        // Простой уровень - несколько рядов блоков
        Color[] colors = {Color.RED, Color.ORANGE, Color.GREEN, Color.YELLOW, Color.BLUE};

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 10; col++) {
                double x = 50 + col * 70;
                double y = 50 + row * 30;

                if (row == 0 && col == 4) {
                    blocks.add(new BonusBlock(x, y, 60, 20));
                } else if (row == 2 && col == 7) {
                    blocks.add(new UnbreakableBlock(x, y, 60, 20));
                } else {
                    blocks.add(new Block(x, y, 60, 20, colors[row % colors.length]));
                }
            }
        }
    }

    private static void createLevel2(List<Block> blocks) {
        // Уровень с узором
        Color[] colors = {Color.DEEPPINK, Color.CYAN, Color.LIME, Color.GOLD};

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 12; col++) {
                if (row == 0 || row == 5 || col == 0 || col == 11 ||
                        (row == 2 && col > 3 && col < 8) ||
                        (row == 3 && (col == 3 || col == 8))) {

                    double x = 20 + col * 60;
                    double y = 30 + row * 25;

                    if ((row + col) % 3 == 0) {
                        blocks.add(new BonusBlock(x, y, 50, 20));
                    } else if ((row + col) % 5 == 0) {
                        blocks.add(new UnbreakableBlock(x, y, 50, 20));
                    } else {
                        blocks.add(new Block(x, y, 50, 20, colors[(row + col) % colors.length]));
                    }
                }
            }
        }
    }

    private static void createLevel3(List<Block> blocks) {
        // Сложный уровень с пирамидой
        Color[] colors = {Color.PURPLE, Color.TEAL, Color.CORAL, Color.SKYBLUE};

        int center = 6;
        for (int row = 0; row < 6; row++) {
            for (int col = center - row; col <= center + row; col++) {
                double x = 100 + col * 50;
                double y = 50 + row * 30;

                if (row == 3 && col == center) {
                    blocks.add(new BonusBlock(x, y, 40, 20));
                } else if (row == 5) {
                    blocks.add(new UnbreakableBlock(x, y, 40, 20));
                } else {
                    blocks.add(new Block(x, y, 40, 20, colors[row % colors.length]));
                }
            }
        }
    }
}