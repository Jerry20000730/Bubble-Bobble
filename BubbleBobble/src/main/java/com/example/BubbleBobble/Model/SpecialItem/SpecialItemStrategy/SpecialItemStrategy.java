package com.example.BubbleBobble.Model.SpecialItem.SpecialItemStrategy;

import com.example.BubbleBobble.Model.Character.Hero;
import com.example.BubbleBobble.Model.SpecialItem.SpecialItem;

/**
 * Interface for specialItemStrategy
 * special item strategy contains three concrete strategy:
 * <ol>
 *     <li>Bomb Strategy: dealing with collision between bomb item and hero</li>
 *     <li>Ice Strategy: dealing with collision between ice item and hero</li>
 *     <li>Medicine Strategy: dealing with collision between medicine item and hero</li>
 * </ol>
 */
public interface SpecialItemStrategy {
    public void doOperation(Hero hero, SpecialItem specialItem);
}
