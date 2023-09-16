package com.example.BubbleBobble.Model.Unit;

import com.example.BubbleBobble.GameObject;
import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import com.example.BubbleBobble.Main;
import javafx.scene.canvas.GraphicsContext;

/**
 * Abstract class for unit
 * The unit abstract class contains three concrete class:
 * <ol>
 *     <li>Ceiling Unit</li>
 *     <li>Floor Unit</li>
 *     <li>Wall Unit</li>
 * </ol>
 */
public abstract class Unit extends GameObject {
    public Unit(InteractableWorld world, int colNum, int rowNum) {
        super(world, colNum, rowNum, Main.UNIT_SIZE, Main.UNIT_SIZE);
    }

    /**
     * Abstract method for drawing unit on graphic context
     * @param gc graphic context parameter
     */
    public abstract void drawOn(GraphicsContext gc);

    /**
     * Abstract method for colliding with game object
     * @param obj game object parameter
     */
    public abstract void collideWith(GameObject obj);
}
