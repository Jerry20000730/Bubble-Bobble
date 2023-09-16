package com.example.BubbleBobble.Controller;

import com.example.BubbleBobble.Main;
import com.example.BubbleBobble.Model.User.UserHolder;
import com.example.BubbleBobble.Model.User.UserSetting;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * The class, StartScreenController, is the controller for StartScreen, and is linked with StartScreen.fxml
 * The start screen contains the entry to:
 * <ol>
 *     <li>start: start the game</li>
 *     <li>info: the info of the game</li>
 *     <li>high score: the high score list entry</li>
 *     <li>quit: quit the application</li>
 * </ol>
 * The start screen also contains combo box to choose the color of the hero
 */
public class StartScreenController {
    @FXML private ImageView HeroIcon;
    @FXML private ComboBox<String> HeroColourSelector;
    private static final String DEFAULT_HERO_COLOR = "yellow";
    private Stage stage;
    private Scene scene;
    private String color;

    /**
     * The initialize method for the StartScreenController
     */
    @FXML
    public void initialize() {
        HeroColourSelector.getItems().addAll("blue", "red", "green", "yellow", "orange", "purple", "grey", "pink");
        color = DEFAULT_HERO_COLOR;
        HeroColourSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            color = newValue;
            URL hero_right_image_url = Main.class.getResource("picture/Hero_right_" + color + ".png");
            Image hero_right_image = new Image(String.valueOf(hero_right_image_url));
            HeroIcon.setImage(hero_right_image);
        });
    }

    /**
     * Methods for clicking the start game button
     * @param mouseEvent click the button
     * @throws Exception
     */
    public void OnStartGameClicked(MouseEvent mouseEvent) throws Exception {
        UserSetting userSetting = new UserSetting();
        userSetting.setHeroColor(color);
        UserHolder userholder = UserHolder.getInstance();
        userholder.setUserSetting(userSetting);

        // scene = new Scene(Main.loadFXML("BackgroundSettingScreen"));
        scene = new Scene(Main.loadFXML("BackgroundSettingScreen"));
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Methods for clicking the info button
     * @param mouseEvent click the button
     * @throws Exception
     */
    public void OnGameInfoClicked(MouseEvent mouseEvent) throws Exception {
        scene = new Scene(Main.loadFXML("InfoScreen"));
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Methods for clicking the high score list button
     * @param mouseEvent click the button
     * @throws Exception
     */
    public void OnGameHighScoreListClicked(MouseEvent mouseEvent) throws Exception {
        scene = new Scene(Main.loadFXML("HighScoreListScreen"));
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Methods for clicking the quit game button
     * @param mouseEvent click the button
     */
    public void OnQuitButtonClicked(MouseEvent mouseEvent) {
        System.exit(0);
    }

}

