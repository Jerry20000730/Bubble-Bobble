package com.example.BubbleBobble.Model.Character;

import com.example.BubbleBobble.Main;
import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import com.example.BubbleBobble.Model.SpecialItem.Fruit;
import com.example.BubbleBobble.SoundEffect;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.net.URL;

/**
 * Class for the boss character at the last level
 */
public class Boss extends GameCharacter {
    private static final int WIDTH = 40;
    private static final int HEIGHT = 60;
    private static final int JUMP_SPEED = 15;
    private static final int TERMINAL_VELOCITY_X = 4;
    private double change_movement_chance;

    private static final URL boss_right_photo_url = Main.class.getResource("picture/boss_right.png");
    private static final Image boss_right_image = new Image(String.valueOf(boss_right_photo_url));

    private static final URL boss_left_photo_url = Main.class.getResource("picture/boss_left.png");
    private static final Image boss_left_image = new Image(String.valueOf(boss_left_photo_url));
    private SimpleIntegerProperty HP = new SimpleIntegerProperty(5000);

    private boolean turningAwayFromShield;
    private int turningAwayCount;
    private boolean isOnAPlatform;
    private double jumpSpeed;

    /**
     * The constructor of the boss character.
     * @param world the interactable world
     * @param colNum the column number
     * @param rowNum the row number
     */
    public Boss(InteractableWorld world, int colNum, int rowNum) {
        //initializes boss
        super(world, colNum, rowNum, WIDTH, HEIGHT);
        isOnAPlatform = false;
        jumpSpeed = JUMP_SPEED;
        terminal_xVelocity = TERMINAL_VELOCITY_X;
        X_terminal_speed_difficulty(world.getDifficulty());

        xAccel = 1.5;
        direction = 1;
        RandomReverseDirection();
        turningAwayFromShield = false;
        turningAwayCount = 10;
    }

    /**
     * The specific method that implement the abstract method in GameObject, draw graphics content on GraphicsContext
     * For boss character, a health point indicator will be drawn on the top of the hero.
     * Three color indicates corresponding value range
     * <ul>
     *     <li>0-1000: red</li>
     *     <li>1000-2500: orange</li>
     *     <li>2500-5000: green</li>
     * </ul>
     * @param gc graphic context
     */
    @Override
    public void drawOn(GraphicsContext gc) {
        //draws mook
//        gc.setFill(Color.BLUE);
//        gc.fillRect(x, y, WIDTH, HEIGHT);
        gc.setFill(Color.rgb(255, 255, 255, 0.8));
        gc.fillRect(x-20, y-20, 80, 10);
        if (HP.get()>=2500) {
            gc.setFill(Color.rgb(0, 255, 0, 0.9));
            gc.fillRect(x-20, y-20, (HP.get()/5000.0) * 80, 10);
        } else if (HP.get() >= 1000 && HP.get() <= 2500) {
            gc.setFill(Color.rgb(255, 157, 0, 0.9));
            gc.fillRect(x-20, y-20, (HP.get()/5000.0) * 80, 10);
        } else {
            gc.setFill(Color.rgb(255, 0, 0, 0.9));
            gc.fillRect(x-20, y-20, (HP.get()/5000.0) * 80, 10);
        }
        if (direction == 1) {
            gc.drawImage(boss_right_image, x, y, width, height);
        } else if (direction == -1) {
            gc.drawImage(boss_left_image, x, y, width, height);
        }
        gc.setFill(Color.BLACK);
    }

    /**
     * The method to randomly reverse the direction.
     */
    private void RandomReverseDirection() {
        if (Math.random() < 0.5) {
            reverseDirection();
        }
    }

    /**
     * The method for boss to die.
     */
    void die() {
        world.addFruit(new Fruit(x, y, world));
        markToRemove();
    }

    /**
     * Method handling collision with hero.
     * @param hero the hero object
     */
    public void collideWith(Hero hero) {
        if (this.overlaps(hero)) {
            hero.collideWithBoss();
            if (hero.getShielding() && !turningAwayFromShield) {
                turningAwayFromShield = true;
                reverseDirection();
            }
            else if (!canRemove){
                SoundEffect.POP.play();
                die();
            }
        }
        if (turningAwayFromShield) {
            if (turningAwayCount <= 0) {
                turningAwayCount = 10;
                turningAwayFromShield = false;
            }
            turningAwayCount -= 1;
        }
    }

    /**
     * Method handling update of boss
     */
    @Override
    public void update() {
        super.update();
        updateWithDifficulty(world.getDifficulty());
    }

    /**
     * Method to update boss according to the difficulty
     * Three difficulties will consider three kinds of attribute altering methodology
     * <ol>
     *     <li>Easy: the change movement possibility of the boss is set to 0.005</li>
     *     <li>Medium: the change movement possibility of the boss is set to 0.01</li>
     *     <li>Hard: the change movement possibility of the boss is set to 0.02</li>
     * </ol>
     * @param difficulty current difficulty for the interactable world
     */
    private void updateWithDifficulty(String difficulty) {
        if (difficulty.equalsIgnoreCase("easy")) {
            change_movement_chance = 0.005;
            if (Math.random() < change_movement_chance) {
                jump();
            }
            if (Math.random() < change_movement_chance) {
                reverseDirection();
            }
        } else if (difficulty.equalsIgnoreCase("medium")) {
            change_movement_chance = 0.01;
            if (Math.random() < change_movement_chance) {
                jump();
            }
            if (Math.random() < change_movement_chance) {
                reverseDirection();
            }

        } else if (difficulty.equalsIgnoreCase("hard")) {
            change_movement_chance = 0.02;
            if (Math.random() < change_movement_chance) {
                jump();
            }
            if (Math.random() < change_movement_chance) {
                reverseDirection();
            }
        }
    }

    /**
     * Set the terminal_xVelocity according to game difficulty
     * @param Difficulty the current game difficulty
     */
    private void X_terminal_speed_difficulty(String Difficulty) {
        if (Difficulty.equalsIgnoreCase("hard")) {
            terminal_xVelocity = TERMINAL_VELOCITY_X + 4;
        } else if (Difficulty.equalsIgnoreCase("easy")) {
            terminal_xVelocity = TERMINAL_VELOCITY_X - 1;
        }
    }

    /**
     * Method for boss to jump, changing three attributes of the boss
     * <ol>
     *    <li>the y coordinate</li>
     *    <li>the y velocity</li>
     *    <li>the attribute of whether the item is on the platform</li>
     * </ol>
     */
    private void jump() {
        if (isOnAPlatform) {
            y -= 1;
            yVelocity = -jumpSpeed;
            isOnAPlatform = false;
        }
    }

    /**
     * Methods for handling collision with floor.
     */
    @Override
    public void collideWithFloor() {
        yVelocity = 0;
        if (!isOnAPlatform) {
            isOnAPlatform = true;
        }
    }

    /**
     * Methods for handling collision with ceiling
     */
    @Override
    public void collideWithCeiling() {
        yVelocity = 0;
    }

    /**
     * Methods for handling collision with wall
     */
    @Override
    public void collideWithWall() {
        reverseDirection();
    }

    /**
     * Methods for handling collision with projectile (for boss)
     *
     *     when boss collide with projectile, the following situation will happen:
     *     <ol>
     *     <li>the health points of the boss will be declined by 50</li>
     *     <li>if the health points of the boss is less than zero, boss died</li>
     *     </ol>
     *
     */
    public void collideWithProjectile() {
        HP.set(HP.get() - 50);
        if (HP.get() < 0) {
            if (!canRemove) {
                SoundEffect.POP.play();
                die();
            }
        }
    }

    /**
     * Method to get the health point of the boss
     * @return the hp point in int type
     */
    public int getHP() {
        return HP.get();
    }

    /**
     * Method to get the property of the health point
     * @return the hp point in property type
     */
    public SimpleIntegerProperty HPProperty() {
        return HP;
    }

    /**
     * Method to set the health point of the boss
     * @param HP the detailed health point value parameter in int type
     */
    public void setHP(int HP) {
        this.HP.set(HP);
    }
}
