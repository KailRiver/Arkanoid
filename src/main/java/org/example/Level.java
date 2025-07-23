package org.example;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
    private static final Color[] colors = {
            Color.RED, Color.ORANGE, Color.GREEN,
            Color.YELLOW, Color.BLUE, Color.PURPLE
    };

    public static List<Block> createLevel(int level) {
        List<Block> blocks = new ArrayList<>();
        Random random = new Random();

        switch (level) {
            case 1:
                createLevel1(blocks, random);
                break;
            case 2:
                createLevel2(blocks, random);
                break;
            case 3:
                createLevel3(blocks, random);
                break;
        }
        return blocks;
    }

    private static void createLevel1(List<Block> blocks, Random random) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 10; col++) {
                double x = 50 + col * 70;
                double y = 50 + row * 30;

                if (random.nextDouble() < 0.1) {
                    blocks.add(new MultiBallBonusBlock(x, y, 60, 20));
                } else if (row == 2 && col == 7) {
                    blocks.add(new UnbreakableBlock(x, y, 60, 20));
                } else {
                    blocks.add(new Block(x, y, 60, 20, colors[row % colors.length]));
                }
            }
        }
    }

    private static void createLevel2(List<Block> blocks, Random random) {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 12; col++) {
                if (row == 0 || row == 5 || col == 0 || col == 11 ||
                        (row == 2 && col > 3 && col < 8) ||
                        (row == 3 && (col == 3 || col == 8))) {

                    double x = 20 + col * 60;
                    double y = 30 + row * 25;

                    if ((row + col) % 3 == 0) {
                        blocks.add(new MultiBallBonusBlock(x, y, 50, 20));
                    } else if ((row + col) % 5 == 0) {
                        blocks.add(new UnbreakableBlock(x, y, 50, 20));
                    } else {
                        blocks.add(new Block(x, y, 50, 20, colors[(row + col) % colors.length]));
                    }
                }
            }
        }
    }

    private static void createLevel3(List<Block> blocks, Random random) {
        int center = 6;
        for (int row = 0; row < 6; row++) {
            for (int col = center - row; col <= center + row; col++) {
                double x = 100 + col * 50;
                double y = 50 + row * 30;

                if (row == 3 && col == center) {
                    blocks.add(new MultiBallBonusBlock(x, y, 40, 20));
                } else if (row == 5) {
                    blocks.add(new UnbreakableBlock(x, y, 40, 20));
                } else {
                    blocks.add(new Block(x, y, 40, 20, colors[row % colors.length]));
                }
            }
        }
    }
}