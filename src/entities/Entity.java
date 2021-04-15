package entities;

import java.awt.*;
import game.*;

/**
 * Any game object that moves relative to the tile map.
 */
public abstract class Entity {

    // TODO: these probably shouldn't all be public
    public double x, y, z; // (x, y) is the center of the shadow
    public Rectangle shadow;
    public int zHeight;
    public Game gameContext;

    public Entity(Game gameContext) {
        this.gameContext = gameContext;
    }

    /**
     * Draws the entity.
     * 
     * @param g       Graphics2D object to draw with
     * @param originX the x coordinate of the top left corner of the map WRT the top
     *                left corner of the screen
     * @param originY the y coordinate of the top left corner of the map WRT the top
     *                left corner of the screen
     */
    public abstract void draw(Graphics2D g, int originX, int originY);

    /**
     * Updates any logic for the entity.
     */
    public abstract void update();

}
