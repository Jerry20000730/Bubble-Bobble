package com.example.BubbleBobble.Controller;

import com.example.BubbleBobble.Model.File.HighScoreFileOperator;
import com.example.BubbleBobble.Model.User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import static com.example.BubbleBobble.Main.loadFXML;

/**
 * The class, HighScoreListController, is the controller for High Score List Screen, and is linked with HighScoreListScreen.fxml
 * The high score list screen contains the information as a table view with two columns and data of:
 * <ol>
 *     <li>playerName</li>
 *     <li>score</li>
 * </ol>
 */
public class HighScoreListController {
    public TableView<User> HighScoreList;
    public TableColumn playerName;
    public TableColumn score;

    private Stage stage;
    private Scene scene;

    /**
     * The initialize method for the HighScoreListController
     * @throws IOException
     */
    @FXML
    @SuppressWarnings("unchecked")
    public void initialize() throws IOException {
        List<User> tempHighScoreList = HighScoreFileOperator.LoadMapIntoTempList(HighScoreFileOperator.sortByComparator(HighScoreFileOperator.LoadHashMapFromTextFile()));
        ObservableList<User> observableHighScoreList = FXCollections.observableArrayList();
        for (User user : tempHighScoreList) {
            observableHighScoreList.add(user);
        }

        playerName.setCellValueFactory(new PropertyValueFactory<User, String>("playerName"));
        score.setCellValueFactory(new PropertyValueFactory<User, Integer>("score"));
        HighScoreList.setItems(observableHighScoreList);
    }

    /**
     * Methods for clicking the back button
     * @param mouseEvent click the button
     * @throws IOException throws IOException
     */
    public void OnBackButtonClicked(MouseEvent mouseEvent) throws IOException {
        scene = new Scene(loadFXML("StartScreen"), 730, 650);
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setTitle("Bubble Bobble");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
