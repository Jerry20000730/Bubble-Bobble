package com.example.BubbleBobble.Model.SpecialItem;

import com.example.BubbleBobble.Main;
import com.example.BubbleBobble.Model.Character.Hero;
import com.example.BubbleBobble.SoundEffect;
import com.example.BubbleBobble.GameObject;
import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Fruit class handles how the fruit is created and interacts with the hero.
 * The fruits are created after a bubble containing an enemy is popped.
 */
public class Fruit extends GameObject {
	private static final int SIZE = 15;
	private static final int TERMINAL_VELOCITY_Y = 10;
	private static final URL apple_image_url = Main.class.getResource("picture/fruit_apple.png");
	private static final Image apple_image = new Image(String.valueOf(apple_image_url));
	private static final URL cherry_image_url = Main.class.getResource("picture/fruit_cherry.png");
	private static final Image cherry_image = new Image(String.valueOf(cherry_image_url));
	private static final URL watermelon_image_url = Main.class.getResource("picture/fruit_watermelon.png");
	private static final Image watermelon_image = new Image(String.valueOf(watermelon_image_url));

	private static final ArrayList<Image> fruit_image = new ArrayList<Image>();
	private Image random_fruit_image;

	private boolean readyToCollect;

	/**
	 * constructor for Fruit
	 * @param x X coordinate of the fruit
	 * @param y Y coordinate of the fruit
	 * @param world the interactable world
	 */
	public Fruit(int x, int y, InteractableWorld world) {
		//initializes fruit
		super(x, y, SIZE, SIZE, world);
		fruit_image.add(apple_image);
		fruit_image.add(cherry_image);
		fruit_image.add(watermelon_image);
		Random r = new Random();
		random_fruit_image = fruit_image.get(r.nextInt(3));
		terminal_yVelocity = TERMINAL_VELOCITY_Y;
		readyToCollect = false;
	}

	/**
	 * method to draw shape on graphic context
	 * @param gc graphic context that the shape will be on
	 */
	@Override
	public void drawOn(GraphicsContext gc) {
		//draws fruit
		gc.drawImage(random_fruit_image, x, y, SIZE, SIZE);
		gc.setFill(Color.BLACK);
	}

	/**
	 * method dealing with colliding with the hero
	 * @param hero the hero
	 */
	public void collideWith(Hero hero) {
		//checks for collision with hero and tells it what to do if it is colliding
		if (this.overlaps(hero) && readyToCollect) {
			SoundEffect.FRUIT.setToLoud();
			SoundEffect.FRUIT.play();
			this.world.setScore(this.world.getScore() + 20);
			this.world.setEnemyNumber(this.world.getEnemyNumber() - 1);
			readyToCollect = false;
			markToRemove();
		}
	}

	/**
	 * method dealing with collision with the floor
	 */
	@Override
	public void collideWithFloor() {
		yVelocity = 0;
		if (!canRemove) {
			readyToCollect = true;
		}
	}

	/**
	 * method dealing with collision with the ceiling
	 */
	@Override
	public void collideWithCeiling() {
		// Nothing happens
	}

	/**
	 * method dealing with collision with the wall
	 */
	@Override
	public void collideWithWall() {
		// Nothing happens
	}
}
