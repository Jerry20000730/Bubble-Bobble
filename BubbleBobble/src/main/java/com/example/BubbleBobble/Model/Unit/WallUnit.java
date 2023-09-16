package com.example.BubbleBobble.Model.Unit;

import com.example.BubbleBobble.GameObject;
import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import com.example.BubbleBobble.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.net.URL;

/**
 * class WallUnit is the unit for the wall (The surrounding and the bound of the interactable world)
 */
public class WallUnit extends Unit {

	private static final URL wall_photo_black_url = Main.class.getResource("picture/wall_brick_black.png");
	private static final Image wall_block_black_image = new Image(String.valueOf(wall_photo_black_url));
	private static final URL wall_photo_white_url = Main.class.getResource("picture/wall_brick_white.png");
	private static final Image wall_block_white_image = new Image(String.valueOf(wall_photo_white_url));

	/**
	 * Constructor of the wallunit
	 * @param world the interactable world
	 * @param colNum the number of the column
	 * @param rowNum the number of the row
	 */
	public WallUnit(InteractableWorld world, int colNum, int rowNum) {
		super(world, colNum, rowNum);
	}

	/**
	 * Method to deal with collision with any game object
	 * @param obj game object parameter
	 */
	public void collideWith(GameObject obj) {
		double center = getCenterX(obj.getHitbox());
		if (this.overlaps(obj)) {
			if (center > getCenterX(this.getHitbox())) {
				moveRightOfUnit(obj);
				obj.collideWithWall();
			} else if (center < getCenterX(this.getHitbox())){
				moveLeftOfUnit(obj);
				obj.collideWithWall();
			} else {
				moveBelowUnit(obj);
				obj.collideWithCeiling();
			}
		}
	}


	/**
	 * Draw the wall unit on the interactable world.
	 * @param gc the graphic context that shape are drawn on.
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
	 * concrete method for handling wall unit collision with floor
	 */
	@Override
	public void collideWithFloor() {
		// do nothing
	}

	/**
	 * concrete method for handling wall unit collision with ceiling
	 */
	@Override
	public void collideWithCeiling() {
		// do nothing
	}

	/**
	 * concrete method for handling wall unit collision with wall
	 */
	@Override
	public void collideWithWall() {
		// do nothing
	}


	/**
	 * To get the center x coordinate of the rectangle.
	 * @param rec input a rectangle
	 * @return the x coordinate of the input rectangle.
	 */
	public static Double getCenterX(Rectangle2D rec) {
		double centerX;
		if (rec == null) {
			throw new IllegalArgumentException("Rectangle can't be null.");
		}
		centerX = rec.getMinX() + (rec.getWidth() / 2);
		return centerX;
	}
}
