package com.hairapp.game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;

public class Circlegame extends Application {

    int height = 400;
    int width = 600;

    Pane pane;
    Rectangle player;
    Circle circle;
    Text score;
    Text gameover;
    Text durummesajı;
    Text timerText;

    Timeline countdown;
    int timeleft = 20;

    int intolanskor = 0;
    int hedefskor = 6;
    @Override
    public void start(Stage stage) {
        pane = new Pane();
        Scene scene = new Scene(pane, width, height);

        // Oyuncu kare
        player = new Rectangle(20, 20, Color.BLUE);
        player.setX(width / 2 - 10);
        player.setY(height / 2 - 10);

        // Kırmızı daire
        circle = new Circle(10, Color.RED);
        panecircle();

        // Skor metni
        score = new Text("Score: " + intolanskor);
        score.setFill(Color.RED);
        score.setX(10);
        score.setY(20);

        // Game over metni
        gameover = new Text("Game Over");
        gameover.setFill(Color.RED);
        gameover.setX(width / 3);
        gameover.setY(height / 3);
        gameover.setVisible(false);

        // Başarı/başarısızlık mesajı
        durummesajı = new Text();
        durummesajı.setX(width / 3 - 10);
        durummesajı.setY(height / 3 - 10);
        durummesajı.setFill(Color.BLUE);
        durummesajı.setVisible(false);

        // Zaman metni
        timerText = new Text("Time: " + timeleft);
        timerText.setX(520);
        timerText.setY(20);
        timerText.setFill(Color.BLACK);

        pane.getChildren().addAll(player, circle, score, gameover, durummesajı, timerText);

        // Zamanlayıcı tanımı (başlangıçta durdurulmuş)
        countdown = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    timeleft--;
                    timerText.setText("Time: " + timeleft);
                    if (timeleft <= 0) {
                        countdown.stop();
                        endGame();
                    }
                })
        );
        countdown.setCycleCount(Animation.INDEFINITE);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    if (player.getY() > 0)
                        player.setY(player.getY() - 10);
                    break;
                case DOWN:
                    if (player.getY() + player.getHeight() < height)
                        player.setY(player.getY() + 10);
                    break;
                case LEFT:
                    if (player.getX() > 0)
                        player.setX(player.getX() - 10);
                    break;
                case RIGHT:
                    if (player.getX() + player.getWidth() < width)
                        player.setX(player.getX() + 10);
                    break;
                case ENTER:
                    // Oyun sıfırlanıyor
                    intolanskor = 0;
                    timeleft = 20;
                    timerText.setText("Time: " + timeleft);
                    score.setText("Score: 0");
                    score.setVisible(true);
                    gameover.setVisible(false);
                    durummesajı.setVisible(false);
                    circle.setVisible(true);
                    player.setX(width / 2 - 10);
                    player.setY(height / 2 - 10);
                    panecircle();
                    countdown.stop();
                    countdown.play();
                    break;
            }

            // Daireye çarpınca skor artar
            if (circle.getBoundsInParent().intersects(player.getBoundsInParent())) {
                intolanskor++;
                score.setText("Score: " + intolanskor);
                if (intolanskor>= hedefskor){
                    countdown.stop();
                    endGame();
                }else{
                panecircle();
                }
            }
        });

        stage.setTitle("Alperen Alkan Game");
        stage.setScene(scene);
        stage.show();
        countdown.play();
    }

    // Dairenin konumunu rastgele belirler
    public void panecircle() {
        Random random = new Random();
        double x = random.nextDouble() * (width - 20) + 10;
        double y = random.nextDouble() * (height - 20) + 10;
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    // Oyun bittiğinde yapılacaklar
    public void endGame() {
        circle.setVisible(false);
        if (intolanskor >= hedefskor) {
            gameover.setText("Success!");
            gameover.setFill(Color.GREEN);
            durummesajı.setText("You Win!");
            durummesajı.setFill(Color.GREEN);

        } else {
            gameover.setText("Failed!");
            gameover.setFill(Color.RED);
            durummesajı.setText("You can restart the game. Don't worry!");
            durummesajı.setFill(Color.RED);
        }
        gameover.setVisible(true);
        durummesajı.setVisible(true);
    }

    public static void main(String[] args) {
        launch();
    }
}
