package com.example.BubbleBobble.Model.InteractableWorld;

import com.example.BubbleBobble.Model.Character.*;

/**
 * DynamicComponentFactory is the concrete factory of GameCharacter Factory to create Game Character
 * Inside its CreateCharacter method, the method would return corresponding type of character
 * according to attribute 'type'
 */
public class DynamicComponentFactory extends GameCharacterFactory {
    /**
     * Concrete method to create character according to its type
     * @param type the char type of game character
     * @param world the interactable world parameter
     * @param colNum the column number
     * @param rowNum the row number
     * @return the specific kind of game character
     */
    @Override
    public GameCharacter CreateCharacter(char type, InteractableWorld world, int colNum, int rowNum) {
        if (type == 'H') {
            return new Hero(world, colNum, rowNum);
        } else if (type == 'M') {
            return new Enemy(world, colNum, rowNum);
        } else if (type == 'B') {
            return new Boss(world, colNum, rowNum);
        }
        return null;
    }
}
