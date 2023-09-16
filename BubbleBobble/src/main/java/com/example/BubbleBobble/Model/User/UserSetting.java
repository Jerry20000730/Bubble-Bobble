package com.example.BubbleBobble.Model.User;

/**
 * Class of UserSetting contains all the information before user starts the game, and after the game is set.
 * UserSetting instance contains all the information, including:
 * <ol>
 *     <li>String Hero Color</li>
 *     <li>String Background Color</li>
 *     <li>String Difficulties</li>
 *     <li>int score</li>
 *     <li>int level</li>
 * </ol>
 */
public class UserSetting {
    private String HeroColor;
    private String BackgroundColor;
    private String Difficuties;
    private int score;
    private int level;

    /**
     * Getter method to get the hero color of the user setting
     * @return the hero color in String format
     */
    public String getHeroColor() {
        return HeroColor;
    }

    /**
     * Setter method to set the hero color
     * @param heroColor the hero color in String format
     */
    public void setHeroColor(String heroColor) {
        HeroColor = heroColor;
    }

    /**
     * Getter method to get the background color
     * @return the background color in String format
     */
    public String getBackgroundColor() {
        return BackgroundColor;
    }

    /**
     * Setter method to set the background color
     * @param backgroundColor the background color in String format
     */
    public void setBackgroundColor(String backgroundColor) {
        BackgroundColor = backgroundColor;
    }

    /**
     * Getter method to get the difficulties
     * @return the difficulty in String format
     */
    public String getDifficuties() {
        return Difficuties;
    }

    /**
     * Setter method to set the difficulty
     * @param difficuties the difficulty in String format
     */
    public void setDifficuties(String difficuties) {
        Difficuties = difficuties;
    }

    /**
     * Getter method to get the final score
     * @return the score in int format
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter method to set the score
     * @param score the score in int format
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Getter method to get the final level
     * @return the level in int format
     */
    public int getLevel() {
        return level;
    }

    /**
     * Setter method to set the final level
     * @param level the level in int format
     */
    public void setLevel(int level) {
        this.level = level;
    }
}
