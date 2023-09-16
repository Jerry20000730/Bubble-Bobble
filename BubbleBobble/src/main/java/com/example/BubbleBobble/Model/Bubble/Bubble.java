package com.example.BubbleBobble.Model.Bubble;

import com.example.BubbleBobble.Model.Character.Boss;
import com.example.BubbleBobble.Model.Character.Enemy;
import com.example.BubbleBobble.GameObject;
import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The main.Bubble class handles everything with the main.Hero's special ability, named the bubble.
 *  It begins at the hero, and grows covering the whole screen.
 *  Once it collides with an enemy, that enemy is bubbled.
 *  Once hero collides with an enemy in bubbled state, then a fruit will be produced, indicating the enemy is dead.
 *  However, if a bubble collides with the boss, the boss will not be in a bubbled state, but deduct its health point instead.
 */
public class Bubble extends GameObject {
	private int accel;

	/**
	 * constructor for bubble gameobject
	 * @param world interactable world parameter
	 * @param x X coordinate parameter
	 * @param y Y coordinate parameter
	 */
	public Bubble(InteractableWorld world, int x, int y) {
		super(x, y, 0, 0, world);
		accel = 1;
	}

	/**
	 * update the position of the bubble
	 */
	@Override
	public void update() {
		if (width >= 2500) {
			markToRemove();
		}
		x -= accel / 2;
		y -= accel / 2;
		width += accel;
		height += accel;
		accel += 1;
	}

	/**
	 * draw bubble on the graphic context
	 * @param gc graphic context that the shape will be on
	 */
	@Override
	public void drawOn(GraphicsContext gc) {
		if (width <= 2500) {
			gc.setFill(Color.rgb(255, 204, 102, (double) (255 - (int) (width * ((double) 255 / 2500)))/255));
		} else {
			gc.setFill(Color.rgb(255, 204, 102, 0));
		}
		gc.fillOval(x, y, width, height);
		gc.setFill(Color.BLACK);
	}

	/**
	 * Method handles collision with floor
	 */
	@Override
	public void collideWithFloor() {
		// Nothing happens
	}

	/**
	 * Method handles collision with ceiling
	 */
	@Override
	public void collideWithCeiling() {
		// Nothing happens
	}

	/**
	 * Method handles collision with wall
	 */
	@Override
	public void collideWithWall() {
		// Nothing happens
	}

	/**
	 * Method for handling collision with enemy
	 * <p>
	 *     If the bubble hit the enemy, the enemy will be in bubbled state
	 * </p>
	 * @param enemy enemy parameter
	 */
	public void collideWith(Enemy enemy) {
		if (this.overlaps(enemy)) {
			enemy.collideWithProjectile();
		}
	}

	/**
	 * Method for handling collision with boss
	 * <p>If the bubble hit the boss, the boss will not be in bubbled state, but deduct its health point value</p>
	 * @param boss boss parameter
	 */
	public void collideWith(Boss boss) {
		if (this.overlaps(boss)) {
			boss.setHP(boss.getHP() - 2500);
		}
	}
}
