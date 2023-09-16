package com.example.BubbleBobble.Model.Character.HeroState;

import com.example.BubbleBobble.Model.Character.Hero;

/**
 * The Context class used to see change in behaviour when Hero State changes.
 */
public class GameContext {
    private HeroState state = null;
    private static final NormalHeroState normalHeroState = new NormalHeroState();
    private static final ShieldHeroState shieldHeroState = new ShieldHeroState();
    private static final RecoverHeroState recoverHeroState = new RecoverHeroState();
    private static final StunnedHeroState stunnedHeroState = new StunnedHeroState();
    private static final FreezeHeroState freezedHeroState = new FreezeHeroState();
    private Hero hero;

    /**
     * Constructor for Game Context
     * @param hero the hero parameter
     */
    public GameContext(Hero hero) {
        this.hero = hero;
    }

    /**
     * Setting state method, changing to five states according to state in string format:
     * <ol>
     *     <li>"normal" = Normal Hero State</li>
     *     <li>"shield" = Shield Hero State</li>
     *     <li>"recover" = Recover Hero State</li>
     *     <li>"stunned" = Stunned Hero State</li>
     *     <li>"freeze" = Freezed Hero State</li>
     * </ol>
     * @param state
     */
    public void setState(String state) {
        if (state.equalsIgnoreCase("normal")) {
            this.state = normalHeroState;
        } else if (state.equalsIgnoreCase("shield")) {
            this.state = shieldHeroState;
        } else if (state.equalsIgnoreCase("recover")) {
            this.state = recoverHeroState;
        } else if (state.equalsIgnoreCase("stunned")) {
            this.state = stunnedHeroState;
        } else if (state.equalsIgnoreCase("freeze")) {
            this.state = freezedHeroState;
        } else {
            this.state = null;
        }
    }

    /**
     * Getter method to get the current state of the hero
     * @return the current state of the hero
     */
    public HeroState getState() {
        return state;
    }

    /**
     * The implementation of different action in different hero state
     */
    public void gameAction() {
        state.action(this.hero, this);
    }
}
