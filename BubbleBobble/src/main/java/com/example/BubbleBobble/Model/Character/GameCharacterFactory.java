package com.example.BubbleBobble.Model.Character;

import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;

/**
 * Abstract class for game character factory, to create specific game character according to type specified in the
 * CreateCharacter(char type, InteractableWorld world, int colNum, int rowNum) method.
 */
public abstract class GameCharacterFactory {
    /**
     * Abstract method for creating game character
     * @param type the char type of game character
     * @param world the interactable world parameter
     * @param colNum the column number
     * @param rowNum the row number
     * @return the needed game character
     */
    public abstract GameCharacter CreateCharacter(char type, InteractableWorld world, int colNum, int rowNum);
}
