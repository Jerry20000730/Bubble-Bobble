package com.example.BubbleBobble.Model.InteractableWorld;

import com.example.BubbleBobble.Model.Unit.*;

/**
 * StaticComponentFactory is the concrete factory of Unit Factory to create Unit
 * Inside its CreateUnit method, the method would return corresponding type of unit
 * according to attribute 'type'
 */
public class StaticComponentFactory extends UnitFactory {
    /**
     * Concrete method to create specific kind of unit
     * @param type the type of the unit
     * @param world the world parameter
     * @param colNum the column number
     * @param rowNum the row number
     * @return the specific unit according to its type
     */
    @Override
    public Unit CreateUnit(char type, InteractableWorld world, int colNum, int rowNum) {
        if (type == '*') {
            return new FloorUnit(world, colNum, rowNum);
        } else if (type == '|') {
            return new WallUnit(world, colNum, rowNum);
        } else if (type == '_') {
            return new CeilingUnit(world, colNum, rowNum);
        }
        return null;
    }
}
