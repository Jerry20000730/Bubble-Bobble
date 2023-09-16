package com.example.BubbleBobble.Model.Character.HeroState;

import com.example.BubbleBobble.Model.Character.Hero;

/**
 * Concrete Stunned Hero state class
 */
public class StunnedHeroState implements HeroState {

    /**
     * Methods implementing concrete actions regarding this state
     * @param hero the hero parameter
     * @param context the game context for changing the hero state
     */
    @Override
    public void action(Hero hero, GameContext context) {
        hero.setStunTimer(hero.getStunTimer() - 1);
        if (hero.getStunTimer() <= 0) {
            hero.setStunned(false);
            hero.setStunTimer(250);
            hero.setShieldTimer(hero.getShieldtimeConstant());
            context.setState("normal");
        }
    }
}
