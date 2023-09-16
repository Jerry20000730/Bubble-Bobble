package com.example.BubbleBobble.Model.Character.HeroState;

import com.example.BubbleBobble.Model.Character.Hero;

/**
 * Interface for hero state
 * hero state contains five concrete state:
 * <ol>
 *     <li>Normal Hero State</li>
 *     <li>Shield Hero State</li>
 *     <li>Recover Hero State</li>
 *     <li>Stunned Hero State</li>
 *     <li>Freezed Hero State</li>
 * </ol>
 */
public interface HeroState {
    /**
     * Abstract method for hero state
     * @param hero the hero parameter
     * @param context
     */
    public void action(Hero hero, GameContext context);
}
