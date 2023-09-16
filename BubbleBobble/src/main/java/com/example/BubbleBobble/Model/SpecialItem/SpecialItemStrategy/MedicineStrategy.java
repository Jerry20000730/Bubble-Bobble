package com.example.BubbleBobble.Model.SpecialItem.SpecialItemStrategy;

import com.example.BubbleBobble.Model.Character.Hero;
import com.example.BubbleBobble.SoundEffect;
import com.example.BubbleBobble.Model.SpecialItem.SpecialItem;

/**
 * Concrete Medicine Strategy class
 */
public class MedicineStrategy implements SpecialItemStrategy {
    /**
     * Methods of detailed operation if strategy is adapted between medicine item and hero
     * @param hero the hero parameter
     * @param specialItem the specialItem parameter
     */
    @Override
    public void doOperation(Hero hero, SpecialItem specialItem) {
        if (specialItem.overlaps(hero) && specialItem.isReadyToCollect()) {
            SoundEffect.MEDICINE.play();
            specialItem.setReadyToCollect(false);
            specialItem.markToRemove();
            hero.world.setLives(hero.world.getLives() + 1);
        }
    }
}
