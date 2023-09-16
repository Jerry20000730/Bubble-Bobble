package com.example.BubbleBobble.Controller;

import com.example.BubbleBobble.Main;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class InfoScreenController {
    /**
     * The class, InfoScreenController, is the controller for StartScreen, and is linked with InfoScreen.fxml
     * The info screen also contains the basic information about the game BubbleBobble
     * as well as guidance to operate on the keyboard
     */
    @FXML
    public TextFlow GameIntroduction;
    public ImageView keyboard;
    private Stage stage;

    /**
     * The initialize method for the InfoScreenController
     */
    @FXML
    public void initialize() {
        GameIntroduction.getChildren().add(new Text("Bubble Bobble is a running and jumping game where you control a small dragon that can blow and jump on bubbles."));
        URL wall_photo_url = Main.class.getResource("picture/keyboard.png");
        Image instruction = new Image(String.valueOf(wall_photo_url));
        keyboard.setImage(instruction);
    }

    /**
     * Methods for clicking the back button
     * @param mouseEvent click the button
     * @throws IOException throws IOException
     */
    public void OnGameInfoBackClicked(MouseEvent mouseEvent) throws IOException {
        Scene scene = new Scene(Main.loadFXML("StartScreen"));
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
