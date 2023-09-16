package com.example.BubbleBobble.Model.SpecialItem;

import com.example.BubbleBobble.Model.Character.Hero;
import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import com.example.BubbleBobble.Main;
import com.example.BubbleBobble.Model.SpecialItem.SpecialItemStrategy.BombStrategy;
import com.example.BubbleBobble.Model.SpecialItem.SpecialItemStrategy.StrategyContext;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;

/**
 * The concrete class for bomb item, the bomb item handles all interactions between the hero and the bomb item
 * The class uses strategy design pattern, specified in SpecialItemStrategy package
 * When hero hit a bomb item, the lives value of hero will be deducted by one.
 */
public class BombItem extends SpecialItem {
    private static final int SIZE = 20;
    private static final URL bomb_image_url = Main.class.getResource("picture/bomb.png");
    private static final Image bomb_image = new Image(String.valueOf(bomb_image_url));
    private final StrategyContext sc = new StrategyContext();
    private final BombStrategy bs = new BombStrategy();

    /**
     * The constructor for the BombItem
     * @param world the interactable world parameter
     * @param colNum the column number parameter
     * @param rowNum the row number parameter
     */
    public BombItem(InteractableWorld world, int colNum, int rowNum) {
        super(world, colNum, rowNum);
    }

    /**
     * The concrete draw method to draw the bomb item on the graphic context
     * @param gc graphic context parameter
     */
    @Override
    public void drawOn(GraphicsContext gc) {
        gc.drawImage(bomb_image, x, y, SIZE, SIZE);
    }

    /**
     * The concrete method dealing with collision with floor
     */
    @Override
    public void collideWithFloor() {
        yVelocity = 0;
        if (!canRemove) {
            this.setReadyToCollect(true);
        }
    }

    /**
     * The concrete method dealing with collision with ceiling
     */
    @Override
    public void collideWithCeiling() {
        // do nothing
    }

    /**
     * The concrete method dealing with collision with wall
     */
    @Override
    public void collideWithWall() {
        // do nothing
    }

    /**
     * Method dealing with collision with hero and set strategy to BombStrategy
     */
    public void collideWith(Hero hero){
        sc.context(bs);
        sc.executeStrategy(hero, this);
    }
}
