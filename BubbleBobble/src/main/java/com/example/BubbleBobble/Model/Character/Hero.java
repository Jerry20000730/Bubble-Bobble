package com.example.BubbleBobble.Model.Character;

import com.example.BubbleBobble.Model.Character.HeroState.GameContext;
import com.example.BubbleBobble.Model.Cooldown.CoolDownTimer;
import com.example.BubbleBobble.Model.Bubble.Bubble;
import com.example.BubbleBobble.SoundEffect;
import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import com.example.BubbleBobble.Main;
import com.example.BubbleBobble.Model.Projectile.HeroProjectile;
import com.example.BubbleBobble.Model.Projectile.ProjectileCache;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.net.URL;


/**
 * the class of hero, also known as the player.
 */
public class Hero extends GameCharacter {
	private static int JUMP_SPEED = 22;
	private static int TERMINAL_VELOCITY_X = 6;
	private static final int SIZE = 20;
	private static int WALK = 5;
	private static int RUN = 10;
	private static int WALK_ACCEL = 6;
	private static double RUN_ACCEL = 20;
	private static final int SHIELD_TIME = 100;
	private final URL hero_right_image_url = Main.class.getResource("picture/Hero_right_" + world.getHeroColor() + ".png");
	private final Image hero_right_image = new Image(String.valueOf(hero_right_image_url));
	private final URL hero_left_image_url = Main.class.getResource("picture/Hero_left_" + world.getHeroColor() + ".png");
	private final Image hero_left_image = new Image(String.valueOf(hero_left_image_url));
	private boolean isShielding;
	private int shieldTimer;
	private boolean isStunned;
	private int stunTimer;
	private boolean isFreezed;
	private int freezeTimer;
	private int shootDelay;
	private boolean readyToCharge;
	private boolean isOnAPlatform;
	private double jumpSpeed;
	private CoolDownTimer CDT = CoolDownTimer.getInstance();
	private GameContext gameContext = new GameContext(this);

	/**
	 * The constructor of hero, also known as the player
	 * @param world the interactable world
	 * @param colNum the number of the column distinguished in World.txt
	 * @param rowNum the number of the row specified in World.txt
	 */
	public Hero(InteractableWorld world, int colNum, int rowNum) {
		//initializes hero
		super(world, colNum, rowNum, SIZE, SIZE);
		isOnAPlatform = false;

		terminal_xVelocity = TERMINAL_VELOCITY_X;
		jumpSpeed = JUMP_SPEED;

		isShielding = false;
		shieldTimer = SHIELD_TIME;
		isStunned = false;
		stunTimer = 250;
		isFreezed = false;
		freezeTimer = 50;
		shootDelay = 0;
		readyToCharge = false;
		gameContext.setState("normal");

		// add key bindings for hero to move
		addKeyBinding();
	}

	/**
	 * Method to add key bindings to the hero
	 */
	private void addKeyBinding() {
		this.world.getScene().setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.RIGHT) {
				if (!isShielding && !isStunned) {
					xAccel = WALK_ACCEL;
					direction = 1;
				}
			} else if (event.getCode() == KeyCode.LEFT) {
				if (!isShielding && !isStunned) {
					xAccel = -WALK_ACCEL;
					direction = -1;
				}
			} else if (event.getCode() == KeyCode.UP) {
				if (!isShielding && !isStunned) {
					jump();
					SoundEffect.JUMP.play();
				}
			} else if (event.getCode() == KeyCode.SPACE) {
				terminal_xVelocity = RUN;
			} else if (event.getCode() == KeyCode.E) {
				if (!isShielding && !isStunned) {
					shootDelay -= 1;
					if (shootDelay <= 0) {
						shootProjectile();
						shootDelay = 10;
					}
				}
			} else if (event.getCode() == KeyCode.Q) {
				if (!isStunned) {
					xVelocity = 0;
					xAccel = 0;
					isShielding = true;
				}
			} else if (event.getCode() == KeyCode.W) {
				if (readyToCharge) {
					world.addBubble(new Bubble(world, x, y));
					SoundEffect.EXPLODE.setToLoud();
					SoundEffect.EXPLODE.play();
					readyToCharge = false;
					CDT.zero();
				} else {
					SoundEffect.EMPTY.play();
				}
			}
		});
		this.world.getScene().setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.RIGHT) {
				xAccel = 0;
			} else if (event.getCode() == KeyCode.LEFT) {
				xAccel = 0;
			} else if (event.getCode() == KeyCode.SPACE) {
				terminal_xVelocity = WALK;
			} else if (event.getCode() == KeyCode.E) {
				shootDelay = 0;
			}
			else if (event.getCode() == KeyCode.Q) {
				isShielding = false;
			}
		});
	}

	/**
	 * the specific method that implement the abstract method in GameObject, draw graphics content on GraphicsContext
	 * @param gc graphic context that the shape will be on
	 */
	@Override
	public void drawOn(GraphicsContext gc) {
		//draws hero
//        gc.setFill(Color.RED);
//        gc.fillRect(x, y, SIZE, SIZE);
		if (direction == 1) {
			gc.drawImage(hero_right_image, x, y, width, height);
		} else {
			gc.drawImage(hero_left_image, x, y, width, height);
		}
		if (isShielding) {
			gc.setFill(Color.rgb(0, (int) (shieldTimer * ((double) 255 / SHIELD_TIME)), (int) (shieldTimer * ((double) 255 / SHIELD_TIME)), (double)190/255));
			gc.fillOval(x - 10, y - 10, SIZE + 20, SIZE + 20);
		} else if (isStunned) {
			gc.setFill(Color.MAGENTA);
			gc.fillRect(x, y, SIZE, SIZE);
		} else if (isFreezed) {
			gc.setFill(Color.rgb(173, 232, 230, 0.8));
			gc.fillRect(x, y, SIZE, SIZE);
		}
		gc.setFill(Color.BLACK);
	}

	/**
	 * make hero shoot projectile
	 */
	public void shootProjectile() {
		//makes hero shoot projectile
		if (CDT.getCooldownTimer() > 500) {
			SoundEffect.SHOOT.play();
			ProjectileCache projectileCache = new ProjectileCache(world, x, y, direction);
			projectileCache.loadCache();
			world.addHeroProjectile((HeroProjectile) projectileCache.getShape("1"));
			CDT.shootProjectileDecrease();
		} else {
			SoundEffect.EMPTY.play();
		}
	}

	/**
	 * handles colliding with a mook
	 */
	public void collideWithMook() {
		if (!isShielding) {
			die();
			this.world.setLives(this.world.getLives() - 1);
		}
	}

	/**
	 * handles colliding with a boss
	 */
	public void collideWithBoss() {
		if (!isShielding) {
			die();
			this.world.setLives(this.world.getLives() - 1);
		}
	}

	/**
	 * handles jumping
	 */
	public void jump() {
		//handles jumping
		if (isOnAPlatform) {
			y -= 1;
			yVelocity = -jumpSpeed;
			isOnAPlatform = false;
		}
	}

	/**
	 * When hero collideWithWall, x velocity becomes 0
	 */
	@Override
	public void collideWithWall() {
		xVelocity = 0;
	}

	/**
	 * handles death of hero, play death sound and reset the world.
	 */
	public void die() {
		SoundEffect.DEATH.setToLoud();
		SoundEffect.DEATH.play();
		world.markToReset();
	}

	/**
	 * handles collision with projectiles
	 */
	public void collideWithProjectile() {
		if (!isShielding) {
			die();
			this.world.setLives(this.world.getLives() - 1);
		}
	}

	/**
	 * handles collision with floor
	 */
	@Override
	public void collideWithFloor() {
		//handles collision with floor
		yVelocity = 0;
		if (!isOnAPlatform) {
			isOnAPlatform = true;
			SoundEffect.LAND.play();
		}
	}

	/**
	 * handles collision with ceiling
	 */
	@Override
	public void collideWithCeiling() {
		// nothing happens
	}

	/**
	 * update the position of hero, according to different ambien
	 * including whether or not the hero is shielding, or the hero is stunned.
	 */
	@Override
	public void update() {
		//updates position of hero, according to many variables
		//including whether or not the hero is shielding,
		//or if the hero is stunned
		super.update();
		CDT.action(this);
//		if (isShielding) {
//			shieldTimer -= 1;
//			if (shieldTimer <= 0) {
//				shieldTimer = 0;
//				isShielding = false;
//				isStunned = true;
//			}
//		} else {
//			if (shieldTimer < SHIELD_TIME && !isStunned) {
//				shieldTimer += 1;
//			}
//		}
//		if (isStunned) {
//			stunTimer -= 1;
//			if (stunTimer <= 0) {
//				isStunned = false;
//				stunTimer = 250;
//				shieldTimer = SHIELD_TIME;
//			}
//		}
		gameContext.gameAction();
	}

	/**
	 * Getter method to get the result whether or not hero is on a platform
	 * @return a boolean value, true indicates hero is on a platform, false otherwise
	 */
	public boolean isOnAPlatform() {
		return isOnAPlatform;
	}

	/**
	 * Setter method to set the result whether or not hero is on a platform
	 * @param onAPlatform a boolean value, true indicates hero is on a platform, false otherwise
	 */
	public void setOnAPlatform(boolean onAPlatform) {
		isOnAPlatform = onAPlatform;
	}

	/**
	 * Getter method to get the result whether or not the hero is shielding on this frame
	 * @return true for hero is shielding, and false otherwise.
	 */
	public boolean getShielding() {
		//gets whether or not the hero is shielding on this frame and returns it
		return isShielding;
	}

	/**
	 * Setter method to set the boolean value whether or not the hero is shielding on this frame
	 * @param shielding the boolean value true that hero should be shielding, false otherwise.
	 */
	public void setShielding(boolean shielding) {
		isShielding = shielding;
	}

	/**
	 * Getter method to get the boolean value whether or not hero is stunned on this frame.
	 * @return the boolean value, true indicating that hero should be stunned, false otherwise.
	 */
	public boolean isStunned() {
		return isStunned;
	}

	/**
	 * Setter method to set the boolean value whether or not hero is stunned on this frame.
	 * @param stunned true indicating that hero should be stunned, false otherwise.
	 */
	public void setStunned(boolean stunned) {
		isStunned = stunned;
	}

	/**
	 * Getter method to get the shield timer of the hero is stunned
	 * @return the int timer value of the hero in shield state
	 */
	public int getShieldTimer() {
		return shieldTimer;
	}

	/**
	 * Setter method to set the shield time for the hero
	 * @param shieldTimer the shield time value in int type
	 */
	public void setShieldTimer(int shieldTimer) {
		this.shieldTimer = shieldTimer;
	}

	/**
	 * Getter method to get the constant shield timer of the hero
	 * @return the int timer value of the hero in stunned state
	 */
	public int getShieldtimeConstant() {
		return SHIELD_TIME;
	}

	/**
	 * Getter method to get the stunned timer of the hero
	 * @return the int timer value of the hero in stunned state
	 */
	public int getStunTimer() {
		return stunTimer;
	}

	/**
	 * Setter method to set the stunned timer for the hero
	 * @param stunTimer the stun timer value in int type
	 */
	public void setStunTimer(int stunTimer) {
		this.stunTimer = stunTimer;
	}

	/**
	 * Getter method to get a boolean value indicating whether the hero is freezed or not.
	 * @return the int timer value of the hero in stunned state
	 */
	public boolean isFreezed() {
		return isFreezed;
	}

	/**
	 * Setter method to set whether the hero is freezed or not
	 * @param freezed true indicating hero is freezed, false otherwise
	 */
	public void setFreezed(boolean freezed) {
		isFreezed = freezed;
	}

	/**
	 * Getter method to get the freeze timer of the hero
	 * @return the int timer value of the hero in stunned state
	 */
	public int getFreezeTimer() {
		return freezeTimer;
	}

	/**
	 * Setter method to set the freeze timer for the hero
	 * @param freezeTimer the freeze timer value in int type
	 */
	public void setFreezeTimer(int freezeTimer) {
		this.freezeTimer = freezeTimer;
	}

	/**
	 * Setter method to set the walk speed for the hero
	 * @param WALK the walk speed for in int type
	 */
	public static void setWALK(int WALK) {
		Hero.WALK = WALK;
	}

	/**
	 * Getter method to get the walk acceleration speed for the hero
	 * @return the walk acceleration speed for walk in int type
	 */
	public static int getWalkAccel() {
		return WALK_ACCEL;
	}

	/**
	 * Getter method to get the walk acceleration speed for the hero
	 * @param walkAccel the walk acceleration speed for walk in int type
	 */
	public static void setWalkAccel(int walkAccel) {
		WALK_ACCEL = walkAccel;
	}

	/**
	 * Getter method to get the running acceleration speed for the hero
	 * @return the double value for running acceleration speed
	 */
	public static double getRunAccel() {
		return RUN_ACCEL;
	}

	/**
	 * Setter method to set the running acceleration speed for the hero
	 * @param runAccel the double value for running acceleration speed
	 */
	public static void setRunAccel(double runAccel) {
		RUN_ACCEL = runAccel;
	}

	/**
	 * Getter method to get the jump speed of current hero
	 * @return the jump speed in double type
	 */
	public double getJumpSpeed() {
		return jumpSpeed;
	}

	/**
	 * Setter method to set the jump speed of current hero
	 * @param jumpSpeed double type jump speed parameter
	 */
	public void setJumpSpeed(double jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}

	/**
	 * Setter method to set the hero is ready to charge and release a big move
	 */
	public void setChargeToReady() {
		readyToCharge = true;
	}
}