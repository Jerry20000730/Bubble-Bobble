package com.example.BubbleBobble.Model.SpecialItem;

import com.example.BubbleBobble.Model.Character.Hero;
import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import com.example.BubbleBobble.Main;
import com.example.BubbleBobble.Model.SpecialItem.SpecialItemStrategy.IceStrategy;
import com.example.BubbleBobble.Model.SpecialItem.SpecialItemStrategy.StrategyContext;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;

/**
 * The concrete class for ice item, the ice item handles all interactions between the hero and the ice item
 * The class uses strategy design pattern, specified in SpecialItemStrategy package
 * When hero hit a ice item, hero's velocity attribute will be altered to a certain extent
 */
public class IceItem extends SpecialItem {

    private static final int SIZE = 20;
    private static final URL ice_image_url = Main.class.getResource("picture/ice.png");
    private static final Image ice_image = new Image(String.valueOf(ice_image_url));
    private final StrategyContext sc = new StrategyContext();
    private final IceStrategy is = new IceStrategy();

    /**
     * The constructor for the IceItem
     * @param world the interactable world parameter
     * @param colNum the column number parameter
     * @param rowNum the row number parameter
     */
    public IceItem(InteractableWorld world, int colNum, int rowNum) {
        super(world, colNum, rowNum);
    }

    /**
     * The concrete draw method to draw the ice item on the graphic context
     * @param gc graphic context parameter
     */
    public void drawOn(GraphicsContext gc) {
        gc.drawImage(ice_image, x, y, SIZE, SIZE);
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
     * Method dealing with collision with hero and set strategy to IceStrategy
     */
    public void collideWith(Hero hero){
        sc.context(is);
        sc.executeStrategy(hero, this);
    }
}
