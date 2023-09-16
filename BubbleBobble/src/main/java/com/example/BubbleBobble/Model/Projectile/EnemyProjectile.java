package com.example.BubbleBobble.Model.Projectile;

import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import com.example.BubbleBobble.Model.Character.Hero;
import com.example.BubbleBobble.Model.Character.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The EnemyProjectile class handles the specificities with the projectile being shot from an enemy.
 * For example, the enemy's projectile has a different color than the projectile of a hero.
 * It also can only hurt a hero.
 * The EnemyProjectile class extends ProjectileShape
 */
public class EnemyProjectile extends ProjectileShape {
	private static final int SIZE = 20;
	private static final int SPEED = 15;
	private static final int TERMINAL_VELOCITY_Y = 5;

	private boolean isActive;
	private int activeFrames;
	private int timer;

	/**
	 * constructor for enemy projectile
	 * @param world the interactable world
	 * @param x x coordinate of the projectile
	 * @param y y coordinate of the projectile
	 * @param direction the direction of the enemy projectile
	 */
	public EnemyProjectile(InteractableWorld world, int x, int y, int direction) {
		super(x, y, SIZE, SIZE, world);
		this.direction = direction;

		xVelocity = SPEED;
		yAccel = 0;

		isActive = true;
		activeFrames = 20;
		timer = activeFrames;
	}

	/**
	 * the graphic context that enemy projectile draw on
	 * @param gc graphic context that the shape will be on
	 */
	@Override
	public void drawOn(GraphicsContext gc) {
		if (isActive) {
			gc.setFill(Color.rgb(0, 102, 0, 1.0));
		} else {
			gc.setFill(Color.rgb(0, 102, 0, 0.4));
		}
		gc.fillOval(x, y, width, height);
		gc.setFill(Color.BLACK);
	}

	/**
	 * the method to update the projectile
	 */
	@Override
	public void update() {
		y += yVelocity;
		x += xVelocity * direction;
		updateVelocity();

		if(y < 25) {
			y = 25;
		}

		if (timer < 0) {
			isActive = false;
		}

		if (timer < -200) {
			markToRemove();
		}
		timer -= 1;
	}

	/**
	 * the method to update the velocity of the projectile
	 */
	private void updateVelocity() {
		if (xVelocity > 0) {
			xVelocity -= 0.25;
		} else {
			xVelocity = 0;
		}

		if (Math.abs(yVelocity) < TERMINAL_VELOCITY_Y && !isActive) {
			yVelocity -= 0.1;
		}
	}

	/**
	 * handles collision with floor
	 */
	@Override
	public void collideWithFloor() {
		// Nothing happen
	}

	/**
	 * handles collision with ceiling
	 */
	@Override
	public void collideWithCeiling() {
		// Nothing happen
	}

	/**
	 * handles collision with wall
	 */
	@Override
	public void collideWithWall() {
		// Nothing happen
	}

	/**
	 * the method to deal with the situation to collide with hero
	 * @param hero
	 */
	public void collideWith(Hero hero) {
		if(this.overlaps(hero) && isActive) {
			hero.collideWithProjectile();
		}
	}

	/**
	 * the method to deal with the situation to collide with enemy
	 * @param enemy
	 */
	public void collideWith(Enemy enemy) {
		//Nothing happens
	}
}