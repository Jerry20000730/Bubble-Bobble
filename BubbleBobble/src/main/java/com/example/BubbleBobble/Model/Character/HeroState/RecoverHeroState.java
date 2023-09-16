package com.example.BubbleBobble.Model.Character.HeroState;

import com.example.BubbleBobble.Model.Character.Hero;

/**
 * Concrete Recover Hero state class
 */
public class RecoverHeroState implements HeroState {
    /**
     * Methods implementing concrete actions regarding this state
     * @param hero the hero parameter
     * @param context the game context for changing the hero state
     */
    @Override
    public void action(Hero hero, GameContext context) {
        hero.setShieldTimer(hero.getShieldTimer() + 1);
        if (hero.getShieldTimer() == hero.getShieldtimeConstant()) {
            context.setState("normal");
        }
    }
}
