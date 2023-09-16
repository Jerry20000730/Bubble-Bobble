package com.example.BubbleBobble.Model.Cooldown;

import com.example.BubbleBobble.Model.Character.Hero;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * The class for cool down timer (cd timer)
 * <p>The maximum cool down timer is set to 5000</p>
 * <p>Each time when hero reset the world, the cool down timer is set to 2000</p>
 * <p>Each timer hero shoot the projectile, the cool down timer will be deducted by 100</p>
 * <p>Every time hero moves, the cool down timer will continue to increase by 2 every frame</p>
 * <p>Once the cool down timer reaches 5000 (MAX), the big move will be activated and hero can release the big move
 * , after which the cool down timer will be set to zero.</p>
 */
public class CoolDownTimer {
    private static final int cooldownTimerMax = 5000;
    private SimpleIntegerProperty cooldownTimer = new SimpleIntegerProperty(this, "cooldownTimer", 2000);

    private static CoolDownTimer coolDownTimerInstance = new CoolDownTimer();

    public CoolDownTimer() {}

    /**
     * Methods to return the single instance of the class CoolDownTimer
     * Cool down timer use singleton design pattern
     * @return the only instance of the class CoolDownTimer
     */
    public static CoolDownTimer getInstance() {
        return coolDownTimerInstance;
    }

    /**
     * Methods of recovering cool down timer each frame
     * @param hero the hero parameter
     */
    public void action(Hero hero) {
        cooldownTimer.set(cooldownTimer.get() + 2);
        if (cooldownTimer.get() == cooldownTimerMax) {
            hero.setChargeToReady();
        }
    }

    /**
     * Methods dealing with deducting cool down timer each time hero shoot a projectile
     */
    public void shootProjectileDecrease() {
        cooldownTimer.set(cooldownTimer.get() - 100);
        if (cooldownTimer.get() < 0) {
            cooldownTimer.set(0);
        }
    }

    /**
     * Methods reset the timer to initial state, after the world is reset;
     */
    public void reset() {
        cooldownTimer.set(2000);
    }

    /**
     * Methods reset the timer to zero
     */
    public void zero() {
        cooldownTimer.set(0);
    }

    /**
     * Getter method to get the cool down timer of the hero in int value
     * @return the int value of the cool down timer
     */
    public int getCooldownTimer() {
        return cooldownTimer.get();
    }

    /**
     * Getter method to get the cool down timer in property
     * @return the simple integer property of the timer
     */
    public SimpleIntegerProperty cooldownTimerProperty() {
        return cooldownTimer;
    }
}
