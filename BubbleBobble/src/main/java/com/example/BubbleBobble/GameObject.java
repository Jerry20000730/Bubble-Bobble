package com.example.BubbleBobble;

import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

/**
 * GameObjects are the objects on the InteractableWorld screen.
 * Every GameObject has a velocity, acceleration, position, direction, and dimensions.
 * GameObjects can detect if they are overlapping another GameObject and
 * must implement methods for collisions with every type of Unit.
 */
public abstract class GameObject extends Node {
	private static final double STATIC_FRICTION = 0.1;
	protected static final int GRAVITY = 1;
	private static final int TERMINAL_FALL_SPEED = 20;

	public InteractableWorld world;
	public int x, y;
	public int width, height;

	public double xVelocity, yVelocity;
	public double xAccel, yAccel;
	public int terminal_xVelocity, terminal_yVelocity;

	public boolean canRemove;
	public int direction;

	/**
	 * First constructor for creating a game object (according to int value of the column and row)
	 * @param world interactable world parameter
	 * @param colNum number of column
	 * @param rowNum number of row
	 * @param width width of the object
	 * @param height height of the object
	 */
	public GameObject(InteractableWorld world, int colNum, int rowNum, int width, int height) {
		this.world = world;
		x = colNum * Main.UNIT_SIZE;
		y = rowNum * Main.UNIT_SIZE;
		this.width = width;
		this.height = height;

		xVelocity = 0;
		yVelocity = 0;
		xAccel = 0;
		yAccel = GRAVITY;
		terminal_xVelocity = 0;
		terminal_yVelocity = TERMINAL_FALL_SPEED;
		canRemove = false;
		direction = -1;
	}

	/**
	 * Second Constructor of creating a game object (according to specific x and y parameter of the object)
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param width width of the object
	 * @param height height of the object
	 * @param world an interactable world.
	 */
	public GameObject(int x, int y, int width, int height, InteractableWorld world) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.world = world;

		xVelocity = 0;
		yVelocity = 0;
		xAccel = 0;
		yAccel = GRAVITY;
		terminal_xVelocity = 0;
		terminal_yVelocity = TERMINAL_FALL_SPEED;
		canRemove = false;
		direction = -1;
	}

	/**
	 * abstract method of drawing shapes on graphic context
	 * @param gc graphic context parameter
	 */
	public abstract void drawOn(GraphicsContext gc);

	/**
	 * abstract method of colliding with floor
	 */
	public abstract void collideWithFloor();

	/**
	 * abstract method of colliding with ceiling
	 */
	public abstract void collideWithCeiling();

	/**
	 * abstarct method of colliding with wall
	 */
	public abstract void collideWithWall();

	/**
	 * General update method of every game object
	 */
	public void update() {
		// if x velocity is smaller than the x terminal velocity, then x velocity plus x acceleration speed
		if (Math.abs(xVelocity) < terminal_xVelocity) {
			xVelocity += xAccel;
		}

		// if x velocity is bigger than static friction, then add one according to the direction
		if (Math.abs(xVelocity) > STATIC_FRICTION) {
			if (xVelocity < 0) {
				xVelocity += 1;
			} else {
				xVelocity -= 1;
			}
			x += xVelocity;
		}

		// if y velocity is smaller than terminal y velocity, then y velocity plus y acceleration speed
		if (yVelocity < terminal_yVelocity) {
			yVelocity += yAccel;
		}
		y += yVelocity;

		// dealing with the situation if the object is off the screen
		if (isOffScreen()) {
			if (y > world.getHeight()) {
				y = 0;
			} else {
				y = (int) world.getHeight();
			}
		}
	}

	/**
	 * reverse game object's direction
	 */
	protected void reverseDirection() {
		xAccel *= -1;
		direction *= -1;
	}

	/**
	 * getter method for x coordinate
	 * @return x coordinate of upper left corner
	 */
	public double getX() {
		return x;
	}

	/**
	 * getter method for y coordinate
	 * @return y coordinate of upper left corner
	 */
	public double getY() {
		return y;
	}

	/**
	 * getter method for width of the object
	 * @return width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * getter method for height of the object
	 * @return height
	 */
	public double getHeight() {
		//returns height
		return height;
	}

	/**
	 * Method to set whether something can be removed or not
	 */
	public void markToRemove() {
		canRemove = true;
	}

	/**
	 * set hitbox for each game object
	 * @return a rectangle representing a hitbox
	 */
	public Rectangle2D getHitbox(){
		//sets hitbox for each game object
		return new Rectangle2D(x, y, width, height);
	}

	/**
	 * check if two objects overlap or collide
	 * @param obj the object to check whether overlap or collide
	 * @return a boolean true for collide and false for not collide
	 */
	public boolean overlaps(GameObject obj) {
		//checks if two objects overlap or collide
		return getHitbox().intersects(obj.getHitbox());
	}

	/**
	 * check if something is offscreen
	 * @return a boolean true for offing the screen, false otherwise
	 */
	public boolean isOffScreen() {
		//checks if something is offscreen
		boolean xLow = x + width < 0;
		boolean xHigh = x > world.getWidth();
		boolean yLow = y + height < 0;
		boolean yHigh = y > world.getHeight();
		return xLow || xHigh || yLow || yHigh;
	}

	/**
	 * move the object to a point
	 * @param point the point that object move to
	 */
	public void moveTo(Point2D point) {
		//moves object to a point
		x = (int) point.getX();
		y = (int) point.getY();
	}

}
