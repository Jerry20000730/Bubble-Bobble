package com.example.BubbleBobble.Model.Unit;

import com.example.BubbleBobble.GameObject;
import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import com.example.BubbleBobble.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.net.URL;


/**
 * class FloorUnit is the unit for the floor (The ground that hero and enemy can jump on in the air)
 */
public class FloorUnit extends Unit {

	private static final URL wall_photo_black_url = Main.class.getResource("picture/wall_brick_black.png");
	private static final Image wall_block_black_image = new Image(String.valueOf(wall_photo_black_url));
	private static final URL wall_photo_white_url = Main.class.getResource("picture/wall_brick_white.png");
	private static final Image wall_block_white_image = new Image(String.valueOf(wall_photo_white_url));
	/**
	 * Constructor of the FloorUnit
	 * @param world the interactable world
	 * @param colNum the number of the column
	 * @param rowNum the number of the row
	 */
	public FloorUnit(InteractableWorld world, int colNum, int rowNum) {
		super(world, colNum, rowNum);
	}

	/**
	 * method of handling collision with any game object
	 * @param obj any game object
	 */
	public void collideWith(GameObject obj) {
		double top = obj.getY();
		double bottom = top + obj.getHeight();
		if (this.overlaps(obj) && obj.yVelocity > 0) {
			if (bottom < y + height) {
				moveAboveUnit(obj);
				obj.collideWithFloor();
			}
			if (top > y){
				moveBelowUnit(obj);
				obj.collideWithCeiling();
			}
		}
	}

	/**
	 * draw a floor unit on the graphic context
	 * @param gc graphic context that the shape will be on
	 */
	@Override
	public void drawOn(GraphicsContext gc) {
//        gc.setFill(Color.BLACK);
//        gc.fillRect(x, y, width, height);
		if (world.getBackground().equalsIgnoreCase("mountain")) {
			gc.drawImage(wall_block_white_image, x, y, width, height);
		} else {
			gc.drawImage(wall_block_black_image, x, y, width, height);
		}
	}


	/**
	 * Method to deal with the situation when game object is moved above the unit
	 * @param obj game object parameter
	 */
	void moveAboveUnit(GameObject obj) {
		obj.moveTo(new Point2D(obj.getX(), y - obj.getHeight()));
	}

	/**
	 * Method to deal with the situation when game object is moved below the unit
	 * @param obj game object parameter
	 */
	void moveBelowUnit(GameObject obj) {
		obj.moveTo(new Point2D(obj.getX(), y + height));
	}

	/**
	 * Method to deal with the situation when game object is moved left of the unit
	 * @param obj game object parameter
	 */
	void moveLeftOfUnit(GameObject obj) {
		obj.moveTo(new Point2D(x - obj.getWidth(), obj.getY()));
	}

	/**
	 * Method to deal with the situation when game object is moved left of the unit
	 * @param obj game object parameter
	 */
	void moveRightOfUnit(GameObject obj) {
		obj.moveTo(new Point2D(x + width, obj.getY()));
	}

	/**
	 * concrete method for handling floor unit collision with floor
	 */
	@Override
	public void collideWithFloor() {
		// nothing happens
	}

	/**
	 * concrete method for handling floor unit collision with ceiling
	 */
	@Override
	public void collideWithCeiling() {
		//nothing happens
	}

	/**
	 * concrete method for handling floor unit collision with wall
	 */
	@Override
	public void collideWithWall() {
		//nothing happens
	}
}
