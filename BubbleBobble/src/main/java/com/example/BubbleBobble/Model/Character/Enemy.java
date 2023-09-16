package com.example.BubbleBobble.Model.Character;

import com.example.BubbleBobble.Model.Projectile.ProjectileCache;
import com.example.BubbleBobble.Model.SpecialItem.Fruit;
import com.example.BubbleBobble.SoundEffect;
import com.example.BubbleBobble.GameObject;
import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import com.example.BubbleBobble.Main;
import com.example.BubbleBobble.Model.Projectile.EnemyProjectile;
import com.example.BubbleBobble.Model.Unit.CeilingUnit;
import com.example.BubbleBobble.Model.Unit.FloorUnit;
import com.example.BubbleBobble.Model.Unit.WallUnit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.net.URL;

/**
 * An Enemy is a non-controllable main.GameObject that kills the main.Hero whenever it or its projectile comes in contact.
 * Enemies are able to be bubbled and free themselves from these bubbles after a period of time.
 * Enemies change direction at random intervals, when hitting a wall, and when hitting the main.Hero's shield.
 * Enemies jump at random intervals as well.
 */
public class Enemy extends GameCharacter {
	private static final int WIDTH = Main.UNIT_SIZE + 10;
	private static final int HEIGHT = Main.UNIT_SIZE + 10;
	private static final int JUMP_SPEED = 20;
	private static final int TERMINAL_VELOCITY_X = 4;
	private static final int BUBBLED_FRAMES = 300;
	private double change_movement_chance;

	private static final URL enemy_right_photo_url = Main.class.getResource("picture/Enemy_right.png");
	private static final Image enemy_right_image = new Image(String.valueOf(enemy_right_photo_url));

	private static final URL enemy_left_photo_url = Main.class.getResource("picture/Enemy_left.png");
	private static final Image enemy_left_image = new Image(String.valueOf(enemy_left_photo_url));

	boolean isBubbled;
	int timer;
	private boolean turningAwayFromShield;
	private int turningAwayCount;
	private boolean isOnAPlatform;
	private double jumpSpeed;

	/**
	 * the constructor of the enemy
	 * @param world the interactable world
	 * @param colNum the number of the column
	 * @param rowNum the number of the row
	 */
	public Enemy(InteractableWorld world, int colNum, int rowNum) {
		//initializes enemy
		super(world, colNum, rowNum, WIDTH, HEIGHT);
		isOnAPlatform = false;
		jumpSpeed = JUMP_SPEED;
		terminal_xVelocity = TERMINAL_VELOCITY_X;
		X_terminal_speed_difficulty(world.getDifficulty());

		xAccel = 1.5;
		direction = 1;
		RandomReverseDirection();
		isBubbled = false;
		timer = BUBBLED_FRAMES;
		turningAwayFromShield = false;
		turningAwayCount = 10;
	}

	/**
	 * draw enemy on the graphic context
	 * @param gc graphic context that the shape will be on
	 */
	@Override
	public void drawOn(GraphicsContext gc) {
		//draws mook
//        gc.setFill(Color.BLUE);
//        gc.fillRect(x, y, WIDTH, HEIGHT);
		if (direction == 1) {
			gc.drawImage(enemy_right_image, x, y, width, height);
		} else if (direction == -1) {
			gc.drawImage(enemy_left_image, x, y, width, height);
		}
		if (isBubbled) {
			gc.setFill(Color.rgb(0, 255, 255,  (timer * ((double) 255 / 300)) / 255));
			gc.fillRect(x - 5, y - 5, WIDTH + 10, HEIGHT + 10);
		}
		gc.setFill(Color.BLACK);
	}

	/**
	 * method dealing with the situation when enemy collide with the floor
	 */
	@Override
	public void collideWithFloor() {
		//handles floor collision values
		yVelocity = 0;
		if (!isOnAPlatform) {
			isOnAPlatform = true;
		}
	}

	/**
	 * method dealing with the situation when enemy collide with the ceiling
	 */
	@Override
	public void collideWithCeiling() {
		//handles ceiling collision values
		yVelocity = 0;
	}

	/**
	 * update the enemy, handling movement
	 */
	@Override
	public void update() {
		super.update();
		updateWithDifficulty(world.getDifficulty());
	}

	/**
	 * update the enemy according to different types of difficulty in following format: .
	 * <ol>
	 *     <li>"easy": the change-movement possibility was set to 0.005</li>
	 *     <li>"medium": the change-movement possibility was set to 0.01</li>
	 *     <li>"hard": the change-movement possibility was set to 0.02</li>
	 * </ol>
	 * @param difficulty
	 */
	private void updateWithDifficulty(String difficulty) {
		if (difficulty.equalsIgnoreCase("easy")) {
			if (isBubbled) {
				timer -= 1;
				if (timer <= 0) {
					isBubbled = false;
					timer = BUBBLED_FRAMES;
					xAccel = 1.5;
					direction = 1;
					if (Math.random() < 0.5) {
						reverseDirection();
					}
					yAccel = GameObject.GRAVITY;
				}
			} else {
				change_movement_chance = 0.005;
				if (Math.random() < change_movement_chance) {
					jump();
				}
				if (Math.random() < change_movement_chance) {
					reverseDirection();
				}
			}
		} else if (difficulty.equalsIgnoreCase("medium")) {
			if (isBubbled) {
				timer -= 1;
				if (timer <= 0) {
					isBubbled = false;
					timer = BUBBLED_FRAMES;
					xAccel = 1.5;
					direction = 1;
					if (Math.random() < 0.5) {
						reverseDirection();
					}
					yAccel = GameObject.GRAVITY;
				}
			} else {
				change_movement_chance = 0.01;
				if (Math.random() < change_movement_chance) {
					jump();
				}
				if (Math.random() < change_movement_chance) {
					reverseDirection();
				}if (Math.random() < change_movement_chance) {
					shootProjectile();
				}
			}
		} else if (difficulty.equalsIgnoreCase("hard")) {
			if (isBubbled) {
				timer -= 1;
				if (timer <= 0) {
					isBubbled = false;
					timer = BUBBLED_FRAMES;
					xAccel = 1.5;
					direction = 1;
					if (Math.random() < 0.5) {
						reverseDirection();
					}
					yAccel = GameObject.GRAVITY;
				}
			} else {
				change_movement_chance = 0.02;
				if (Math.random() < change_movement_chance) {
					jump();
				}
				if (Math.random() < change_movement_chance) {
					reverseDirection();
				}if (Math.random() < change_movement_chance) {
					shootProjectile();
				}
			}
		}
	}

	/**
	 * Set the terminal_xVelocity according to game difficulty
	 * @param Difficulty the current game difficulty
	 */
	private void X_terminal_speed_difficulty(String Difficulty) {
		if (Difficulty.equalsIgnoreCase("hard")) {
			terminal_xVelocity = TERMINAL_VELOCITY_X + 4;
		} else if (Difficulty.equalsIgnoreCase("easy")) {
			terminal_xVelocity = TERMINAL_VELOCITY_X - 1;
		}
	}

	/**
	 * Methods to randomly reverse the direction of the enemy
	 */
	private void RandomReverseDirection() {
		if (Math.random() < 0.5) {
			reverseDirection();
		}
	}

	/**
	 * Method for enemy to jump
	 */
	private void jump() {
		if (isOnAPlatform) {
			y -= 1;
			yVelocity = -jumpSpeed;
			isOnAPlatform = false;
		}
	}

	/**
	 * Methods handling enemy to shoot projectile
	 */
	private void shootProjectile() {
		// Nothing happens
		ProjectileCache projectileCache = new ProjectileCache(world, x, y, direction);
		projectileCache.loadCache();
		world.addEnemyProjectile((EnemyProjectile) projectileCache.getShape("2"));
	}

	/**
	 * handles what to do if hit with a projectile by the hero
	 */
	public void collideWithProjectile() {
		if (!isBubbled) {
			SoundEffect.BUBBLED.setToLoud();
			SoundEffect.BUBBLED.play();
			isBubbled = true;
			yVelocity = 0;
			xAccel = 0;
			yAccel = -0.1;
		}
	}

	/**
	 * handles what to do on collision with a wall
	 */
	public void collideWithWall() {
		reverseDirection();
	}

	/**
	 * handles what to do if dead
	 */
	void die() {
		world.addFruit(new Fruit(x, y, world));
		markToRemove();
	}

	/**
	 * handles collision with hero and what to do
	 * @param hero the hero parameter
	 */
	public void collideWith(Hero hero) {
		if (this.overlaps(hero)) {
			if (!isBubbled) {
				hero.collideWithMook();
				if (hero.getShielding() && !turningAwayFromShield) {
					turningAwayFromShield = true;
					reverseDirection();
				}
			}
			else if (!canRemove){
				SoundEffect.POP.play();
				die();
			}
		}
		if (turningAwayFromShield) {
			if (turningAwayCount <= 0) {
				turningAwayCount = 10;
				turningAwayFromShield = false;
			}
			turningAwayCount -= 1;
		}
	}

	/**
	 * handles unit collision with ceiling unit
	 * @param unit the ceiling unit parameter
	 */
	public void collideWith(CeilingUnit unit) {
		if (this.overlaps(unit)) {
			if (isBubbled) {
				yVelocity = 0;
				yAccel = 0;
			}
		}
	}

	/**
	 * handles unit collision with floor unit
	 * @param floorUnit the floor unit parameter
	 */
	public void collideWith(FloorUnit floorUnit) {
		//handles unit collision
		if (this.overlaps(floorUnit)) {
			if (isBubbled) {
				yVelocity = 0;
				yAccel = 0;
			}
		}
	}

	/**
	 * handles unit collision with the wall unit
	 * @param wallUnit the wall unit parameter
	 */
	public void collideWith(WallUnit wallUnit) {
		if (this.overlaps(wallUnit)) {
			if (isBubbled) {
				yVelocity = 0;
				yAccel = 0;
			}
		}
	}
}
