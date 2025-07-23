package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenu {
    private final Stage stage;

    public MainMenu(Stage primaryStage) {
        this.stage = primaryStage;
        createMenu();
    }

    private void createMenu() {
        VBox menuLayout = new VBox(30);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPadding(new Insets(50));

        try {
            Image backgroundImage = new Image(getClass().getResourceAsStream("/background.jpg"));
            BackgroundImage background = new BackgroundImage(
                    backgroundImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, false, true)
            );
            menuLayout.setBackground(new Background(background));
        } catch (Exception e) {
            menuLayout.setStyle("-fx-background-color: black;");
        }

        Text title = new Text("ARKANOID");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        title.setFill(Color.WHITE);
        title.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0);");

        Button startBtn = createStyledButton("Новая игра", Color.DODGERBLUE);
        Button exitBtn = createStyledButton("Выход", Color.INDIANRED);

        startBtn.setOnAction(e -> showDifficultyMenu());
        exitBtn.setOnAction(e -> System.exit(0));

        menuLayout.getChildren().addAll(title, startBtn, exitBtn);
        Scene scene = new Scene(menuLayout, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private Button createStyledButton(String text, Color color) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        btn.setTextFill(Color.WHITE);
        btn.setBackground(new Background(new BackgroundFill(
                color, new CornerRadii(10), Insets.EMPTY)));
        btn.setPadding(new Insets(10, 30, 10, 30));
        btn.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 5, 0, 0, 1);");

        btn.setOnMouseEntered(e -> btn.setEffect(new javafx.scene.effect.Glow(0.3)));
        btn.setOnMouseExited(e -> btn.setEffect(null));

        return btn;
    }

    private void showDifficultyMenu() {
        VBox difficultyMenu = new VBox(20);
        difficultyMenu.setAlignment(Pos.CENTER);
        difficultyMenu.setPadding(new Insets(50));

        try {
            Image backgroundImage = new Image(getClass().getResourceAsStream("/background.jpg"));
            BackgroundImage background = new BackgroundImage(
                    backgroundImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, false, true)
            );
            difficultyMenu.setBackground(new Background(background));
        } catch (Exception e) {
            difficultyMenu.setStyle("-fx-background-color: rgba(0,0,0,0.7);");
        }

        Text title = new Text("Выберите сложность");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        title.setFill(Color.WHITE);

        Button easyBtn = createStyledButton("Легкая", Color.LIMEGREEN);
        Button mediumBtn = createStyledButton("Средняя", Color.GOLD);
        Button hardBtn = createStyledButton("Сложная", Color.TOMATO);

        easyBtn.setOnAction(e -> startGame(Difficulty.EASY));
        mediumBtn.setOnAction(e -> startGame(Difficulty.MEDIUM));
        hardBtn.setOnAction(e -> startGame(Difficulty.HARD));

        difficultyMenu.getChildren().addAll(title, easyBtn, mediumBtn, hardBtn);

        StackPane root = new StackPane(difficultyMenu);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
    }

    private void startGame(Difficulty difficulty) {
        stage.close();
        new Game(new Stage(), difficulty);
    }
}