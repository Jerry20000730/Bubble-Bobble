package com.example.BubbleBobble.Model.Character;

import com.example.BubbleBobble.GameObject;
import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import javafx.scene.canvas.GraphicsContext;

/**
 * Abstract class for game character
 * The game character contains three concrete class:
 * <ol>
 *     <li>hero</li>
 *     <li>enemy</li>
 *     <li>boss</li>
 * </ol>
 * game character class specifies the generic methods of game character class.
 */
public abstract class GameCharacter extends GameObject {
    public GameCharacter(InteractableWorld world, int colNum, int rowNum, int width, int height) {
        super(world, colNum, rowNum, width, height);
    }

    /**
     * draw game character on the graphic context
     * @param gc graphic context parameter
     */
    public void drawOn(GraphicsContext gc) {}
}
