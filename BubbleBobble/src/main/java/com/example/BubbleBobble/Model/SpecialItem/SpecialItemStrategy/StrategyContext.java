package com.example.BubbleBobble.Model.SpecialItem.SpecialItemStrategy;

import com.example.BubbleBobble.Model.Character.Hero;
import com.example.BubbleBobble.Model.SpecialItem.SpecialItem;

/**
 * Use the StrategyContext to see change in behaviour when it changes its Strategy.
 */
public class StrategyContext {
    private SpecialItemStrategy specialItemStrategy;

    /**
     * The method to initialize the strategy
     * @param specialItemStrategy the strategy to be applied
     */
    public void context(SpecialItemStrategy specialItemStrategy) {
        this.specialItemStrategy = specialItemStrategy;
    }

    /**
     * The method to execute the strategy detailed operation
     * @param hero hero parameter
     * @param specialItem specialItem parameter
     */
    public void executeStrategy(Hero hero, SpecialItem specialItem) {
        specialItemStrategy.doOperation(hero, specialItem);
    }
}
