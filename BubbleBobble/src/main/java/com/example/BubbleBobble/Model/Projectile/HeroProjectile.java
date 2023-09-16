package com.example.BubbleBobble.Model.Projectile;

import com.example.BubbleBobble.Model.Character.Boss;
import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import com.example.BubbleBobble.Model.Character.Enemy;
import com.example.BubbleBobble.Model.Character.Hero;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The HeroProjectile class handles the specificities with the projectile being shot from a hero.
 * For example, the hero's projectile has a different color than the projectile of an enemy.
 * It also can only hurt an enemy.
 * The hero projectile extends ProjectileShape.
 */
public class HeroProjectile extends ProjectileShape {
	private static final int SIZE = 20;
	private static final int SPEED = 15;
	private static final int TERMINAL_VELOCITY_Y = 5;

	private boolean isActive;
	private int activeFrames;
	private int timer;

	/**
	 * The constructor for HeroProjectile
	 * @param world the interactable world
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param direction the moving direction of the projectile
	 */
	public HeroProjectile(InteractableWorld world, int x, int y, int direction) {
		super(x, y, SIZE, SIZE, world);
		this.direction = direction;
		this.setType("HeroProjectile");
		xVelocity = SPEED;
		yAccel = 0;

		isActive = true;
        activeFrames = 35;
        timer = activeFrames;
	}

	/**
	 * draw hero projectile to the graphic context
	 * @param gc graphic context that the shape will be on
	 */
	@Override
	public void drawOn(GraphicsContext gc) {
		if (isActive) {
			gc.setFill(Color.rgb(102, 204, 255));
		} else {
			gc.setFill(Color.rgb(51, 204, 255, 0.4));
		}
		gc.fillOval(x, y, width, height);
		gc.setFill(Color.BLACK);
	}

	/**
	 * update the location of the projectile
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
	 * update the velocity of the projectile
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
	 * methods colliding with floor
	 */
	@Override
	public void collideWithFloor() {
		// Nothing happens
	}

	/**
	 * methods colliding with ceiling
	 */
	@Override
	public void collideWithCeiling() {
		// Nothing happens
	}

	/**
	 * methods colliding with wall
	 */
	@Override
	public void collideWithWall() {
		// Nothing happens
	}

	/**
	 * methods colliding with hero
	 * @param hero the hero game object
	 */
	public void collideWith(Hero hero) {
		// Nothing happens
	}

	/**
	 * methods colliding with enemy
	 * @param enemy the enemy game object
	 */
	public void collideWith(Enemy enemy) {
		if (this.overlaps(enemy) && isActive) {
			enemy.collideWithProjectile();
		}
	}

	/**
	 * methods colliding with enemy
	 * @param boss the boss game object
	 */
	public void collideWith(Boss boss) {
		if (this.overlaps(boss) && isActive) {
			boss.collideWithProjectile();
		}
	}
}