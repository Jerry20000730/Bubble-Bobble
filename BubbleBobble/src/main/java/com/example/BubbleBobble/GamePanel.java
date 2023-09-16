package com.example.BubbleBobble;

import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.net.URL;


/**
 * Game panel is where entire game is constantly updated.
 * After every few milliseconds, GamePanel calls the methods that update its InteractableWorld,
 * then repaints the window to display the new drawn graphics.
 */
public class GamePanel extends Scene {
	private InteractableWorld dynamicWorld;
	private InteractableWorld staticWorld;
	private Canvas background;
	private Timeline timeline;
	private static final URL background_sea_url = Main.class.getResource("picture/background_sea.png");
	private static final Image background_sea = new Image(String.valueOf(background_sea_url));
	private static final URL background_mountain_url = Main.class.getResource("picture/background_mountain.png");
	private static final Image background_mountain = new Image(String.valueOf(background_mountain_url));
	private static final URL background_jungle_url = Main.class.getResource("picture/background_jungle.png");
	private static final Image  background_jungle = new Image(String.valueOf(background_jungle_url));

	/**
	 * Constructor of game panel, involving several attributes that need for this game panel.
	 * @param root the group involves all the components wrapped, including background, static_world, dynamic_world
	 * @param width the width of the scene
	 * @param height the height of the scene
	 * @param color the color of the hero
	 * @param background_theme the theme of the background
	 * @param difficulty the difficulty for the game.
	 */
	public GamePanel(Group root, int width, int height, String color, String background_theme, String difficulty) {
		super(root, width, height);

		// draw the background
		background = new Canvas(width, height);
		GraphicsContext background_gc = background.getGraphicsContext2D();
		background_gc.drawImage(chooseBackgroundImage(background_theme), 0, 0, width, height);

		// set the dynamic world
		dynamicWorld = new InteractableWorld(width, height);
		dynamicWorld.setHeroColor(color);
		dynamicWorld.setBackground(background_theme);
		dynamicWorld.setDifficulty(difficulty);

		// set the static world
		staticWorld = new InteractableWorld(width, height);
		staticWorld.setHeroColor(color);
		staticWorld.setBackground(background_theme);
		staticWorld.setDifficulty(difficulty);

		// adding all the component to the root, background in the back, second is static world, and the top is dynamic world
		root.getChildren().addAll(background, staticWorld, dynamicWorld);

		// move dynamic world to the front
		dynamicWorld.toFront();
		dynamicWorld.setHeroColor(color);

		// initialize the static world (ceiling unit, wall unit, floor unit)
		// and the dynamic world (hero, enemy, items, bubbles, etc.,)
		staticWorld.startGame();
		dynamicWorld.startGame();

		// get the graphic context of the world
		GraphicsContext gcd = dynamicWorld.getGraphicsContext2D();
		GraphicsContext gcs = staticWorld.getGraphicsContext2D();

		// paint the static component and dynamic component on static world and dynamic world respectively.
		staticWorld.paintStaticComponent(gcs);
		dynamicWorld.paintDynamicComponent(gcd);

		// listen on the dynamic world's NextLevelProperty to know whether the level has changed.
		dynamicWorld.isNextLevelProperty().addListener((observable, oldValue, newValue) -> {
			timeline.pause();
			staticWorld.setCurrentLevel(dynamicWorld.getCurrentLevel());
			staticWorld.startGame();
			dynamicWorld.startGame();
			gcd.clearRect(0,0, dynamicWorld.getWidth(), dynamicWorld.getHeight());
			gcs.clearRect(0,0, staticWorld.getWidth(), staticWorld.getHeight());
			staticWorld.paintStaticComponent(gcs);
			dynamicWorld.setIsNextLevel(false);
			timeline.play();
		});

		// Timeline to make animation
		timeline = new Timeline(new KeyFrame(Duration.millis(1000/60), event -> {
			gcd.clearRect(0, 0, dynamicWorld.getWidth(), dynamicWorld.getHeight());
			dynamicWorld.updatePosition();
			dynamicWorld.paintDynamicComponent(gcd);
			dynamicWorld.CheckNextLevel();
			dynamicWorld.CheckGameOver();
			dynamicWorld.CheckWin();
		}));
		// set animation to indefinite.
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	/**
	 * Method to choose the background image
	 * @param type the type of the background
	 * @return the image needed for the background.
	 */
	public Image chooseBackgroundImage(String type) {
		if (type.equalsIgnoreCase("sea")) {
			return background_sea;
		} else if (type.equalsIgnoreCase("mountain")) {
			return background_mountain;
		} else if (type.equalsIgnoreCase("jungle")) {
			return background_jungle;
		}
		return null;
	}

	/**
	 * get the dynamic world in this game panel
	 * @return the dynamic world
	 */
	public InteractableWorld getDynamicWorld() {
		return dynamicWorld;
	}

	/**
	 * get the timeline in the game panel
	 * @return the timeline
	 */
	public Timeline getTimeline() {
		return timeline;
	}
}
