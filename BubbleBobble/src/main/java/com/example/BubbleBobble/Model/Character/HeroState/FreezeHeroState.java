package com.example.BubbleBobble.Model.Character.HeroState;

import com.example.BubbleBobble.Model.Character.Hero;

/**
 * Concrete Freeze Hero state class
 */
public class FreezeHeroState implements HeroState{
    /**
     * Methods implementing concrete actions regarding this state
     * @param hero the hero parameter
     * @param context the game context for changing the hero state
     */
    @Override
    public void action(Hero hero, GameContext context) {
        hero.setFreezeTimer(hero.getFreezeTimer() - 1);
        if (hero.getFreezeTimer() <= 0) {
            hero.setFreezed(false);
            Hero.setWALK(5);
            hero.terminal_xVelocity = 6;
            hero.terminal_yVelocity = 20;
            hero.setJumpSpeed(22);
            context.setState("normal");
        }
    }
}
