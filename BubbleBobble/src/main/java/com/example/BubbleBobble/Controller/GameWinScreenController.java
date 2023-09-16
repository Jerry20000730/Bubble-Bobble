package com.example.BubbleBobble.Controller;

import com.example.BubbleBobble.Main;
import com.example.BubbleBobble.Model.File.HighScoreFileOperator;
import com.example.BubbleBobble.Model.User.UserHolder;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.Random;

import static com.example.BubbleBobble.Main.loadFXML;

/**
 * The class, GameWinController, is the controller for GameWinScreen, and is linked with GameWinScreen.fxml
 * The GameWin screen contains information:
 * <ol>
 *     <li>GameWin Title</li>
 *     <li>final score</li>
 * </ol>
 * And the Game win screen contains several functionalities and entry:
 * <ol>
 *     <li>NameInput: input the player game</li>
 *     <li>submit: button to submit the player name</li>
 *     <li>New Game: back to the original start screen</li>
 *     <li>High Score List: view the high score list</li>
 *     <li>quit: quit the application</li>
 * </ol>
 * The win screen also includes some of the validation because it involves writing data to the file, which means
 * data from user manual input should be checked.
 */
public class GameWinScreenController {

    public Label FinalScore;
    public TextField NameInput;
    private String DEFAULT_PLAYER_NAME = "Unknown";
    private Stage stage;
    private Scene scene;
    private boolean isWritten = false;

    /**
     * The initialize method for the InfoScreenController
     */
    @FXML
    public void initialize() {
        UserHolder userHolder = UserHolder.getInstance();
        FinalScore.setText(Integer.toString(userHolder.getUserSetting().getScore()));
    }

    /**
     * Methods for clicking the New Game button
     * Writing player name and score to the file
     * Validate the player name
     * @param mouseEvent click the button
     * @throws Exception
     */
    public void OnNewGameButtonClicked(MouseEvent mouseEvent) throws Exception {
        UserHolder userHolder = UserHolder.getInstance();
        String name = null;
        if (NameInput.getText().trim().isEmpty()) {
            Random r = new Random();
            name = DEFAULT_PLAYER_NAME + r.nextInt();
        } else {
            if (inputcheck(NameInput.getText())) {
                name = NameInput.getText();
            } else {
                Alert invalid_name_alert = new Alert(Alert.AlertType.WARNING, "The name contains invalid character, please reenter the player name!", ButtonType.OK);
                invalid_name_alert.setTitle("Input name is invalid");
                return;
            }
        }
        if (!isWritten) {
            HighScoreFileOperator.WriteScoreToTextFile(name, userHolder.getUserSetting().getScore());
            isWritten = true;
        }
        scene = new Scene(loadFXML("StartScreen"), 730, 650);
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setTitle("Bubble Bobble");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Methods for clicking the High Score List button
     * Writing player name and score to the file
     * Validate the player name
     * @param mouseEvent click the button
     * @throws Exception
     */
    public void OnGameOverHighScoreListClicked(MouseEvent mouseEvent) throws Exception {
        UserHolder userHolder = UserHolder.getInstance();
        String name = null;
        if (NameInput.getText().trim().isEmpty()) {
            Random r = new Random();
            name = DEFAULT_PLAYER_NAME + r.nextInt();
        } else {
            if (inputcheck(NameInput.getText())) {
                name = NameInput.getText();
            } else {
                Alert invalid_name_alert = new Alert(Alert.AlertType.WARNING, "The name contains invalid character, please reenter the player name!", ButtonType.OK);
                invalid_name_alert.setTitle("Input name is invalid");
                return;
            }
        }
        if (!isWritten) {
            HighScoreFileOperator.WriteScoreToTextFile(name, userHolder.getUserSetting().getScore());
            isWritten = true;
        }

        scene = new Scene(Main.loadFXML("HighScoreListScreen"));
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Methods for clicking Quit Game button
     * Writing player name and score to the file
     * Validate the player name
     * @param mouseEvent click the button
     * @throws Exception
     */
    public void OnGameOverQuitButtonClicked(MouseEvent mouseEvent) throws Exception {
        UserHolder userHolder = UserHolder.getInstance();
        String name = null;
        if (NameInput.getText().trim().isEmpty()) {
            Random r = new Random();
            name = DEFAULT_PLAYER_NAME + r.nextInt();
        } else {
            if (inputcheck(NameInput.getText())) {
                name = NameInput.getText();
            } else {
                Alert invalid_name_alert = new Alert(Alert.AlertType.WARNING, "The name contains invalid character, please reenter the player name!", ButtonType.OK);
                invalid_name_alert.setTitle("Input name is invalid");
                return;
            }
        }

        if (!isWritten) {
            HighScoreFileOperator.WriteScoreToTextFile(name, userHolder.getUserSetting().getScore());
            isWritten = true;
        }
        System.exit(0);
    }

    /**
     * Methods for clicking the Submit button
     * Writing player name and score to the file
     * Validate the player name
     * Proper Warnings to warn the player about name input
     * @param mouseEvent click the button
     * @throws Exception
     */
    public void OnSubmitButtonClicked(MouseEvent mouseEvent) throws Exception {
        UserHolder userHolder = UserHolder.getInstance();
        String name;
        if (NameInput.getText().trim().isEmpty()) {
            Alert null_name_alert = new Alert(Alert.AlertType.WARNING, "The input name is null, are you sure to continue?", ButtonType.YES, ButtonType.CANCEL);
            null_name_alert.setTitle("Input name is empty");
            Optional<ButtonType> result = null_name_alert.showAndWait();

            if (result.get() == ButtonType.YES) {
                Random r = new Random();
                name = DEFAULT_PLAYER_NAME + r.nextInt();
            } else {
                return;
            }
        } else {
            if (inputcheck(NameInput.getText())) {
                name = NameInput.getText();
            } else {
                Alert invalid_name_alert = new Alert(Alert.AlertType.WARNING, "The name contains invalid character, please reenter the player name!", ButtonType.OK);
                invalid_name_alert.setTitle("Input name is invalid");
                return;
            }
        }
        if (!isWritten) {
            HighScoreFileOperator.WriteScoreToTextFile(name, userHolder.getUserSetting().getScore());
            isWritten = true;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successful information");
        alert.setContentText("The player name and score have been saved!");
        alert.show();
    }

    /**
     * Methods for name input check
     * @param text input player name
     */
    private boolean inputcheck(String text) {
        return !text.contains(",");
    }
}
