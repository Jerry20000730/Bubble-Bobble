package com.example.BubbleBobble.Model.Character.HeroState;

import com.example.BubbleBobble.Model.Character.Hero;

/**
 * Concrete Shield Hero State class
 */
public class ShieldHeroState implements HeroState{

    /**
     * Methods implementing concrete actions regarding this state
     * @param hero the hero parameter
     * @param context the game context for changing the hero state
     */
    @Override
    public void action(Hero hero, GameContext context) {
        hero.setShieldTimer(hero.getShieldTimer() - 1);
        if (hero.getShieldTimer() <= 0) {
            hero.setShieldTimer(0);
            hero.setShielding(false);
            hero.setStunned(true);
            context.setState("stunned");
        }
        if (!hero.getShielding()) {
            context.setState("recover");
        }
    }
}
