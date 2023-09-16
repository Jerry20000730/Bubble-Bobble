package com.example.BubbleBobble.Model.SpecialItem;

import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;

/**
 * Abstract class for special item factory, to create specific special item according to type specified in the
 * CreateCharacter(char type, InteractableWorld world, int colNum, int rowNum) method.
 */
public abstract class SpecialItemFactory {
    /**
     * Abstract method for creating special item
     * @param type the type of the special item
     * @param world the interactable world parameter
     * @param colNum the column number
     * @param rowNum the row number
     * @return the special item
     */
    public abstract SpecialItem CreateItem(String type, InteractableWorld world, int colNum, int rowNum);
}
