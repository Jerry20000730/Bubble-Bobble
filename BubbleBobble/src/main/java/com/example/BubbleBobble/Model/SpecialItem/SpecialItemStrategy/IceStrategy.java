package com.example.BubbleBobble.Model.SpecialItem.SpecialItemStrategy;

import com.example.BubbleBobble.Model.Character.Hero;
import com.example.BubbleBobble.SoundEffect;
import com.example.BubbleBobble.Model.SpecialItem.SpecialItem;

/**
 * Concrete Ice Strategy class
 */
public class IceStrategy implements SpecialItemStrategy {
    /**
     * Methods of detailed operation if strategy is adapted between ice item and hero
     * @param hero the hero parameter
     * @param specialItem the specialItem parameter
     */
    @Override
    public void doOperation(Hero hero, SpecialItem specialItem) {
        if (specialItem.overlaps(hero) && specialItem.isReadyToCollect()) {
            SoundEffect.FREEZE.play();
            specialItem.setReadyToCollect(false);
            specialItem.markToRemove();
            hero.setFreezed(true);
        }
    }
}
