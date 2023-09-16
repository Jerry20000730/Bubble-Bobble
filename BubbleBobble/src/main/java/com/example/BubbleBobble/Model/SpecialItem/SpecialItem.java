package com.example.BubbleBobble.Model.SpecialItem;

import com.example.BubbleBobble.GameObject;
import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import javafx.scene.canvas.GraphicsContext;

/**
 * The Abstract SpecialItem is the items on the interactable world where hero can collide with,
 * There are three kinds of special items available in the current game:
 * <ol>
 *     <li>Bomb item: when hero collide with the bomb item, the live of the hero will be deducted</li>
 *     <li>Ice item: when hero collide with the ice item, the hero's X and Y velocity will be decreased</li>
 *     <li>Medicine item: when hero collide with the medicine item, the hero's lives will be recover by one</li>
 * </ol>
 */
public abstract class SpecialItem extends GameObject {
    private boolean readyToCollect;
    private static final int SIZE = 20;
    private static final int TERMINAL_VELOCITY_Y = 10;

    /**
     * The constructor for the specialItem
     * @param world the interactable world
     * @param colNum the int value of column number
     * @param rowNum the int value of row number
     */
    public SpecialItem(InteractableWorld world, int colNum, int rowNum) {
        super(world, colNum, rowNum, SIZE, SIZE);
        terminal_yVelocity = TERMINAL_VELOCITY_Y;
    }

    /**
     * Abstract method to draw the item on the graphic context
     * @param gc graphic context parameter
     */
    public void drawOn(GraphicsContext gc) {}

    /**
     * Getter Method to see whether the item is ready to collect
     * @return a boolean value, true for ready to collect, false otherwise
     */
    public boolean isReadyToCollect() {
        return readyToCollect;
    }

    /**
     * Setter method to set whether the item is ready to collect
     * @param readyToCollect a boolean value, true for ready to collect, false otherwise
     */
    public void setReadyToCollect(boolean readyToCollect) {
        this.readyToCollect = readyToCollect;
    }
}
