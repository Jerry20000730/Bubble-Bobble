package com.example.BubbleBobble.Model.Unit;

import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;

/**
 * Abstract class for unit factory, to create specific unit according to type specified in the
 * CreateUnit(char type, InteractableWorld world, int colNum, int rowNum) method.
 */
public abstract class UnitFactory {
    /**
     * Abstract method for creating unit
     * @param type the type of the unit
     * @param world the world parameter
     * @param colNum the column number
     * @param rowNum the row number
     * @return the specific unit based on the type
     */
    public abstract Unit CreateUnit(char type, InteractableWorld world, int colNum, int rowNum);
}
