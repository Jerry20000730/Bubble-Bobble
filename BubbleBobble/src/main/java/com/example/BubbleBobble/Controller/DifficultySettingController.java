package com.example.BubbleBobble.Controller;

import com.example.BubbleBobble.Model.Cooldown.CoolDownTimer;
import com.example.BubbleBobble.GamePanel;
import com.example.BubbleBobble.Main;
import com.example.BubbleBobble.SoundEffect;
import com.example.BubbleBobble.Model.User.UserHolder;
import com.example.BubbleBobble.Model.User.UserSetting;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * The class, DifficultyController, is the controller for DifficultyScreen, and is linked with DifficultySettingScreen.fxml
 * The Difficulty screen contains the radio box to choose from three difficulties:
 * <ol>
 *     <li>"easy"/li>
 *     <li>"medium"</li>
 *     <li>"hard"</li>
 * </ol>
 * The corresponding settings will be transferred to the interactable world
 */
public class DifficultySettingController {
    @FXML private RadioButton EasyButton;
    @FXML private RadioButton MediumButton;
    @FXML private RadioButton HardButton;

    private static final String DEFAULT_DIFFICULTY = "easy";

    private static final String family = "Helvetica";
    private static final double size = 20;
    private final TextFlow scoreTextFlow = new TextFlow();
    private final TextFlow lifeTextFlow = new TextFlow();
    private final TextFlow EPTextFlow = new TextFlow();
    private final TextFlow DifficultyTextFlow = new TextFlow();
    private final URL live_url = Main.class.getResource("picture/live.png");
    private final Image live_heart = new Image(String.valueOf(live_url));
    private ImageView iv1 = new ImageView();
    private ImageView iv2 = new ImageView();
    private ImageView iv3 = new ImageView();
    final Text scoreTitle = new Text("Score: ");
    final Text score = new Text();
    final Text liveTitle = new Text("Lives: ");
    final Text live = new Text();
    final Text difficultyTitle = new Text("Difficulty: ");
    final Text difficulty = new Text();
    final Label levelTitle = new Label("LEVEL");
    final Label level = new Label();
    final Text powerTitle = new Text("Experience Point: ");
    final HBox hbox_lives = new HBox();
    final HBox hbox_progress = new HBox();
    final VBox vbox_level = new VBox();

    private ProgressBar pb = new ProgressBar();
    private ProgressIndicator pi = new ProgressIndicator();
    private CoolDownTimer CDT = CoolDownTimer.getInstance();

    private Stage stage;

    private ToggleGroup group = new ToggleGroup();

    private UserSetting userSetting = new UserSetting();

    private String Difficulty;

    /**
     * The initialize method for the DifficultySettingScreenController
     */
    @FXML
    public void initialize() {
        Difficulty = DEFAULT_DIFFICULTY;
        EasyButton.setToggleGroup(group);
        EasyButton.setUserData("easy");
        EasyButton.setSelected(true);

        MediumButton.setToggleGroup(group);
        MediumButton.setUserData("medium");

        HardButton.setToggleGroup(group);
        HardButton.setUserData("hard");

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            Difficulty = newValue.getUserData().toString();
        });

        CDT.cooldownTimerProperty().addListener(((observable, oldValue, newValue) -> {
            pb.setProgress(newValue.doubleValue()/5000);
            pi.setProgress(newValue.doubleValue()/5000);
        }));
    }

    /**
     * Methods for clicking the start game button
     * @param mouseEvent click the button
     */
    public void OnStartGameClicked(MouseEvent mouseEvent) {
        UserHolder userHolder = UserHolder.getInstance();
        userHolder.getUserSetting().setDifficuties(Difficulty);
        setupScoreText();
        setupLiveDisplay();
        setUpPowerDisplay();
        setUpDifficultyDisplay();
        setUpLevelDisplay();

        // begin the settings for the game
        Group root = new Group();
        GamePanel gp = new GamePanel(root, Main.WIDTH * Main.UNIT_SIZE, Main.HEIGHT * Main.UNIT_SIZE, userHolder.getUserSetting().getHeroColor(), userHolder.getUserSetting().getBackgroundColor(), userHolder.getUserSetting().getDifficuties());
        root.getChildren().addAll(scoreTextFlow, lifeTextFlow, hbox_lives, EPTextFlow, hbox_progress, DifficultyTextFlow, vbox_level);
        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(gp);
        stage.centerOnScreen();
        stage.show();

        // binding score
        score.textProperty().bind(Bindings.convert(gp.getDynamicWorld().scoreProperty()));

        // binding level
        level.textProperty().bind(Bindings.convert(gp.getDynamicWorld().currentLevelProperty()));

        // binding difficulty
        difficulty.textProperty().bind(Bindings.convert((gp.getDynamicWorld().difficultyProperty())));

        // update the live image
        gp.getDynamicWorld().livesProperty().addListener(((observable, oldValue, newValue) -> {
            liveImageUpdate(gp);
        }));

        // game over listener
        gp.getDynamicWorld().isGameOverProperty().addListener(((observable, oldValue, newValue) -> {
            gp.getTimeline().stop();
            SoundEffect.OVER.play();
            userHolder.getUserSetting().setScore(gp.getDynamicWorld().getScore());
            userHolder.getUserSetting().setLevel(gp.getDynamicWorld().getCurrentLevel());
            try {
                Scene scene = new Scene(Main.loadFXML("GameOverScreen"));
                stage = (Stage) gp.getWindow();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        // game win listener
        gp.getDynamicWorld().isWinProperty().addListener(((observable, oldValue, newValue) -> {
            gp.getTimeline().stop();
            SoundEffect.WIN.play();
            userHolder.getUserSetting().setScore(gp.getDynamicWorld().getScore());
            try {
                Scene scene = new Scene(Main.loadFXML("GameWinScreen"));
                stage = (Stage) gp.getWindow();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    /**
     * Methods for setting up score text flow
     */
    public void setupScoreText() {
        scoreTextFlow.setLayoutX(40);
        scoreTextFlow.setLayoutY(40);
        scoreTitle.setFont(Font.font(family, size));
        score.setFont(Font.font(family, size));
        scoreTextFlow.getChildren().addAll(scoreTitle, score);
    }

    /**
     * Methods for setting up hero's lives text flow
     */
    public void setupLiveDisplay() {
        lifeTextFlow.setLayoutX(40);
        lifeTextFlow.setLayoutY(70);
        liveTitle.setFont(Font.font(family, size));
        live.setFont(Font.font(family, size));
        lifeTextFlow.getChildren().addAll(liveTitle, live);

        iv1.setImage(live_heart);
        iv2.setImage(live_heart);
        iv3.setImage(live_heart);
        hbox_lives.getChildren().addAll(iv1, iv2, iv3);
        hbox_lives.setLayoutX(100);
        hbox_lives.setLayoutY(70);
        hbox_lives.setSpacing(10);
    }

    /**
     * Methods for setting up Experience Point text flow
     */
    public void setUpPowerDisplay() {
        EPTextFlow.setLayoutX(40);
        EPTextFlow.setLayoutY(100);
        powerTitle.setFont(Font.font(family, size));
        EPTextFlow.getChildren().add(powerTitle);

        pb.setStyle("-fx-text-box-border: skyblue;\n" +
                "-fx-control-inner-background: palegreen;\n" + "-fx-background-insets: 0;\n" + "-fx-background-radius: 0;\n");

        hbox_progress.setLayoutX(210);
        hbox_progress.setLayoutY(105);
        hbox_progress.setSpacing(5);
        hbox_progress.getChildren().addAll(pb, pi);
    }

    /**
     * Methods for setting up Difficulty display text flow
     */
    public void setUpDifficultyDisplay() {
        DifficultyTextFlow.setLayoutX(40);
        DifficultyTextFlow.setLayoutY(130);
        difficultyTitle.setFont(Font.font(family, size));
        difficulty.setFont(Font.font(family, size));
        DifficultyTextFlow.getChildren().addAll(difficultyTitle, difficulty);
    }

    /**
     * Methods for setting up Level Display text flow
     */
    public void setUpLevelDisplay() {
        levelTitle.setFont(Font.font(family, 30));
        levelTitle.setStyle("-fx-font-weight: bold");
        level.setFont(Font.font(family, 30));
        levelTitle.setStyle("-fx-font-weight: bold");
        vbox_level.getChildren().addAll(levelTitle, level);
        vbox_level.setLayoutX(400);
        vbox_level.setLayoutY(40);
        vbox_level.setSpacing(5);
        vbox_level.setAlignment(Pos.CENTER);
    }

    /**
     * Methods to update the live (heart) image according to the lives in the real time
     * @param gp the game panel parameter
     */
    public void liveImageUpdate(GamePanel gp) {
        for (int i=0; i<gp.getDynamicWorld().getLives();i++) {
            hbox_lives.getChildren().get(i).setVisible(true);
        }
        for (int j=gp.getDynamicWorld().getLives(); j<3;j++) {
            hbox_lives.getChildren().get(j).setVisible(false);
        }
    }
}
