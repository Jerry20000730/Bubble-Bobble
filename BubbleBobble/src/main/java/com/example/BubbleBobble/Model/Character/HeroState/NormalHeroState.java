package com.example.BubbleBobble.Model.Character.HeroState;

import com.example.BubbleBobble.Model.Character.Hero;

/**
 * Concrete Normal Hero state class
 */
public class NormalHeroState implements HeroState{

    /**
     * Methods implementing concrete actions regarding this state
     * @param hero the hero parameter
     * @param context the game context for changing the hero state
     */
    @Override
    public void action(Hero hero, GameContext context) {
        hero.setShieldTimer(100);
        hero.setStunTimer(250);
        hero.setFreezeTimer(250);
        if (hero.getShielding()) {
            context.setState("shield");
        }
        if (hero.isStunned()) {
            context.setState("stunned");
        }
        if (hero.isFreezed()) {
            hero.xVelocity = 0;
            hero.yVelocity = 0;
            Hero.setWALK(1);
            hero.terminal_xVelocity = 2;
            hero.terminal_yVelocity = 10;
            hero.setJumpSpeed(15);
            context.setState("freeze");
        }
    }

}
