package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RenderManager {
    private final Pane root;
    private final StackPane uiLayer;
    private HBox infoPanel;
    private Label scoreLabel;
    private Label levelLabel;
    private Label livesLabel;
    private VBox pauseMenu;
    private VBox endGameMenu;

    public RenderManager() {
        this.root = new Pane();
        this.uiLayer = new StackPane();
        this.root.getChildren().add(uiLayer);
        createUI();
    }

    public Pane getRoot() {
        return root;
    }

    public Scene createScene() {
        return new Scene(root, 800, 600, Color.BLACK);
    }

    public void setBackground(Background background) {
        root.setBackground(background);
    }

    private void createUI() {
        infoPanel = new HBox(20);
        infoPanel.setStyle("-fx-background-color: rgba(0,0,0,0.7);");
        infoPanel.setLayoutX(10);
        infoPanel.setLayoutY(10);

        scoreLabel = createInfoLabel("Score: 0");
        levelLabel = createInfoLabel("Level: 1");
        livesLabel = createInfoLabel("Lives: 3");

        infoPanel.getChildren().addAll(scoreLabel, levelLabel, livesLabel);
        root.getChildren().add(infoPanel);

        pauseMenu = new VBox(20);
        pauseMenu.setAlignment(Pos.CENTER);
        pauseMenu.setStyle("-fx-background-color: rgba(0,0,0,0.8);");
        pauseMenu.setVisible(false);

        Label pauseLabel = new Label("PAUSED");
        pauseLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        pauseLabel.setTextFill(Color.WHITE);

        Button resumeBtn = createMenuButton("Resume", Color.GREEN);
        Button menuBtn = createMenuButton("Main Menu", Color.DODGERBLUE);

        pauseMenu.getChildren().addAll(pauseLabel, resumeBtn, menuBtn);
        uiLayer.getChildren().add(pauseMenu);

        endGameMenu = new VBox(20);
        endGameMenu.setAlignment(Pos.CENTER);
        endGameMenu.setStyle("-fx-background-color: rgba(0,0,0,0.8);");
        endGameMenu.setVisible(false);
        uiLayer.getChildren().add(endGameMenu);
    }

    private Label createInfoLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private Button createMenuButton(String text, Color color) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        btn.setTextFill(Color.WHITE);
        btn.setBackground(new Background(new BackgroundFill(
                color, new CornerRadii(10), Insets.EMPTY)));
        btn.setPadding(new Insets(10, 30, 10, 30));
        btn.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 5, 0, 0, 1);");

        btn.setOnMouseEntered(e -> {
            btn.setScaleX(1.05);
            btn.setScaleY(1.05);
        });
        btn.setOnMouseExited(e -> {
            btn.setScaleX(1.0);
            btn.setScaleY(1.0);
        });

        return btn;
    }

    public void showPauseMenu() {
        pauseMenu.setVisible(true);
    }

    public void hidePauseMenu() {
        pauseMenu.setVisible(false);
    }

    public void showEndGameMessage(String message, boolean won, Runnable onMenuClick) {
        // Очищаем все элементы игры
        root.getChildren().clear();

        // Создаем полупрозрачный фон для меню
        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.7);");
        overlay.setPrefSize(800, 600);

        // Создаем контейнер для сообщения
        VBox messageBox = new VBox(20);
        messageBox.setAlignment(Pos.CENTER);
        messageBox.setLayoutX(200);
        messageBox.setLayoutY(200);
        messageBox.setPrefSize(400, 200);
        messageBox.setStyle("-fx-background-color: rgba(0,0,0,0.9);");

        Label messageLabel = new Label(message);
        messageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        messageLabel.setTextFill(won ? Color.GOLD : Color.WHITE);

        Button menuBtn = new Button("Main Menu");
        menuBtn.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        menuBtn.setTextFill(Color.WHITE);
        menuBtn.setStyle("-fx-background-color: #3498db; -fx-background-radius: 5;");
        menuBtn.setOnAction(e -> onMenuClick.run());

        messageBox.getChildren().addAll(messageLabel, menuBtn);
        root.getChildren().addAll(overlay, messageBox);
    }

    public void updateGameInfo(GameState gameState) {
        scoreLabel.setText("Score: " + gameState.getScore());
        levelLabel.setText("Level: " + gameState.getCurrentLevel());
        livesLabel.setText("Lives: " + gameState.getLives());
    }

    public void renderGameObjects(GameState gameState) {
        root.getChildren().clear();
        root.getChildren().add(infoPanel);

        root.getChildren().add(gameState.getBall().getCircle());
        root.getChildren().add(gameState.getPaddle().getRect());

        for (Block block : gameState.getBlocks()) {
            root.getChildren().add(block.getRect());
        }

        uiLayer.toFront();
    }
}