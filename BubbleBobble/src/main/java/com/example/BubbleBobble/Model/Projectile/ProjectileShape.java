package com.example.BubbleBobble.Model.Projectile;

import com.example.BubbleBobble.GameObject;
import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;
import javafx.scene.canvas.GraphicsContext;

/**
 * Create an abstract class implementing Clonable interface.
 */
public abstract class ProjectileShape extends GameObject implements Cloneable {

    private String type;
    private String id;

    /**
     * Abstract method to draw the projectile on the graphic context
     * @param gc graphic context parameter
     */
    public abstract void drawOn(GraphicsContext gc);

    /**
     * Abstract method to update the projectile in the interactable world
     */
    public abstract void update();

    /**
     * Constructor to initialize a projectile shape
     * @param x the x coordinate parameter
     * @param y the y coordinate parameter
     * @param width the width of the shape
     * @param height the height of the shape
     * @param world the interactable world parameter
     */
    public ProjectileShape(int x, int y, int width, int height, InteractableWorld world) {
        super(x, y, width, height, world);
    }

    /**
     * clone method to clone the projectile shape
     * @return
     */
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    /**
     * Getter method to get the type of the projectile shape
     * @return the projectile shape in string format
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method to set the type of the projectile shape
     * @param type the string type of the projectile shape
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter method to get the ID of the projectile shape
     * @return the shapeID of projectile shape in string format
     */
    public String getID() {
        return id;
    }

    /**
     * Setter method to set the ID of the projectile shape
     * @param id the shapeID of projectile shape in string format
     */
    public void setID(String id) {
        this.id = id;
    }
}
