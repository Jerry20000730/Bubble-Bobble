package com.example.BubbleBobble.Model.SpecialItem;

import com.example.BubbleBobble.Model.Character.Hero;
import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import com.example.BubbleBobble.Main;
import com.example.BubbleBobble.Model.SpecialItem.SpecialItemStrategy.MedicineStrategy;
import com.example.BubbleBobble.Model.SpecialItem.SpecialItemStrategy.StrategyContext;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;

/**
 * The concrete method for medicine item, the medicine item handles all interactions between the hero and the medicine item
 * The class uses strategy design pattern, specified in SpecialItemStrategy package
 * When user hit a medicine item, the lives of the hero will recover by one
 */
public class MedicineItem extends SpecialItem {

    private static final int SIZE = 20;
    private static final URL medicine_image_url = Main.class.getResource("picture/medicine.png");
    private static final Image medicine_image = new Image(String.valueOf(medicine_image_url));
    private final StrategyContext sc = new StrategyContext();
    private final MedicineStrategy ms = new MedicineStrategy();

    /**
     * The constructor for the MedicineItem
     * @param world the interactable world parameter
     * @param colNum the column number parameter
     * @param rowNum the row number parameter
     */
    public MedicineItem(InteractableWorld world, int colNum, int rowNum) {
        super(world, colNum, rowNum);
    }

    /**
     * The concrete draw method to draw the medicine item on the graphic context
     * @param gc graphic context parameter
     */
    public void drawOn(GraphicsContext gc) {
        gc.drawImage(medicine_image, x, y, SIZE, SIZE);
    }

    @Override
    public void collideWithFloor() {
        yVelocity = 0;
        if (!canRemove) {
            this.setReadyToCollect(true);
        }
    }

    @Override
    public void collideWithCeiling() {
        // do nothing
    }

    @Override
    public void collideWithWall() {
        // do nothing
    }

    public void collideWith(Hero hero){
        sc.context(ms);
        sc.executeStrategy(hero, this);
    }
}
