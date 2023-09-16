package com.example.BubbleBobble.Controller;

import com.example.BubbleBobble.Main;
import com.example.BubbleBobble.Model.User.UserHolder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * The class, BackgroundController, is the controller for BackgroundSettingScreen, and is linked with BackgroundSettingScreen.fxml
 * The Background Setting screen contains the radio box to choose from three themes of background:
 * <ol>
 *     <li>"sea"/li>
 *     <li>"mountain"</li>
 *     <li>"jungle"</li>
 * </ol>
 * The corresponding settings will be transferred to the interactable world
 */
public class BackgroundSettingController {
    @FXML private ImageView BackgroundImage;
    @FXML private RadioButton seaRadioButton;
    @FXML private RadioButton mountainRadioButton;
    @FXML private RadioButton jungleRadioButton;
    private Stage stage;
    private Scene scene;
    private ToggleGroup group = new ToggleGroup();
    private String background_theme = "sea";
    private final URL background_sea_url = Main.class.getResource("picture/background_sea.png");
    private final URL background_mountain_url = Main.class.getResource("picture/background_mountain.png");
    private final URL background_jungle_url = Main.class.getResource("picture/background_jungle.png");
    private final Image background_sea_image = new Image(String.valueOf(background_sea_url));
    private final Image background_mountain_image = new Image(String.valueOf(background_mountain_url));
    private final Image background_jungle_image = new Image(String.valueOf(background_jungle_url));

    /**
     * The initialize method for the DifficultySettingScreenController
     */
    public void initialize() {
        seaRadioButton.setToggleGroup(group);
        seaRadioButton.setUserData("Sea");
        seaRadioButton.setSelected(true);

        mountainRadioButton.setToggleGroup(group);
        mountainRadioButton.setUserData("mountain");

        jungleRadioButton.setToggleGroup(group);
        jungleRadioButton.setUserData("jungle");

        BackgroundImage.setFitWidth(400);
        BackgroundImage.setFitHeight(340);
        BackgroundImage.setImage(background_sea_image);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                background_theme = newValue.getUserData().toString();
                setCorrespondingImage(group.getSelectedToggle().getUserData().toString());
            }
        });
    }

    /**
     * Methods to set corresponding image according to the theme string attribute
     * @param theme the image needed for the theme
     */
    public void setCorrespondingImage(String theme) {
        if (theme.equalsIgnoreCase("sea")) {
            BackgroundImage.setImage(background_sea_image);
        } else if (theme.equalsIgnoreCase("mountain")) {
            BackgroundImage.setImage(background_mountain_image);
        } else if (theme.equalsIgnoreCase("jungle")) {
            BackgroundImage.setImage(background_jungle_image);
        }
    }

    /**
     * Methods for clicking the Next button
     * @param mouseEvent click the button
     * @throws IOException throws IOException
     */
    public void OnNextButtonClicked(MouseEvent mouseEvent) throws IOException {
        UserHolder userholder = UserHolder.getInstance();
        userholder.getUserSetting().setBackgroundColor(background_theme);

        scene = new Scene(Main.loadFXML("DifficultySettingScreen"));
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
