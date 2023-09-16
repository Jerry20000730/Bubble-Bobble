package com.example.BubbleBobble.Model.InteractableWorld;

import com.example.BubbleBobble.Model.SpecialItem.*;

/**
 * ItemFactory is the concrete factory of SpecialItem Factory to create special items
 * Inside its CreateItem method, the method would return corresponding type of special item
 * according to String attribute 'type'
 */
public class ItemFactory extends SpecialItemFactory {

    /**
     * Concrete method to create specific kind of special item
     * @param type the type of the special item
     * @param world the interactable world parameter
     * @param colNum the column number
     * @param rowNum the row number
     * @return the specific kind of special item based on the type
     */
    @Override
    public SpecialItem CreateItem(String type, InteractableWorld world, int colNum, int rowNum) {
        if (type.equalsIgnoreCase("bomb")) {
            return new BombItem(world, colNum, rowNum);
        } else if (type.equalsIgnoreCase("ice")) {
            return new IceItem(world, colNum, rowNum);
        } else if (type.equalsIgnoreCase("medicine")) {
            return new MedicineItem(world, colNum, rowNum);
        }
        return null;
    }
}
