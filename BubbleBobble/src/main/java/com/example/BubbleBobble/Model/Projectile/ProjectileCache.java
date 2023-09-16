package com.example.BubbleBobble.Model.Projectile;

import com.example.BubbleBobble.Model.InteractableWorld.InteractableWorld;

import java.util.Hashtable;

/**
 * Create a class to get concrete classes from database and store them in a Hashtable.
 */
public class ProjectileCache {
    private static Hashtable<String, ProjectileShape> shapeHashMap = new Hashtable<String, ProjectileShape>();
    private static InteractableWorld world;
    private static int x;
    private static int y;
    private static int direction;

    /**
     * Constructor to create a projectile cache
     * @param world the interactable world parameter
     * @param x the x coordinate parameter
     * @param y the y coordicate parameter
     * @param direction the direction of the projectile
     */
    public ProjectileCache(InteractableWorld world, int x, int y, int direction) {
        ProjectileCache.x = x;
        ProjectileCache.y = y;
        ProjectileCache.world = world;
        ProjectileCache.direction = direction;
    }

    /**
     * Static method to get the current shape of shapeID ("1" for HeroProjectile and "2" for EnemyProjectile)
     * @param shapeID the shapeID to create corresponding projectile
     * @return the shape of the projectile
     */
    public static ProjectileShape getShape(String shapeID) {
        ProjectileShape cachedShape = shapeHashMap.get(shapeID);
        return (ProjectileShape) cachedShape.clone();
    }

    /**
     * Static method to load and put shape into the hashmap
     * for each projectile run database query and create projectileShape
     * shapeMap.put(shapeKey, projectileShape)
     */
    public static void loadCache() {
        HeroProjectile heroProjectile = new HeroProjectile(world, x, y, direction);
        heroProjectile.setID("1");
        shapeHashMap.put(heroProjectile.getID(), heroProjectile);

        EnemyProjectile enemyProjectile = new EnemyProjectile(world, x, y, direction);
        enemyProjectile.setID("2");
        shapeHashMap.put(enemyProjectile.getID(), enemyProjectile);
    }
}
