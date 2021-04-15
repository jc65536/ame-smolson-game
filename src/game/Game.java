package game;

import java.util.*;
import java.awt.*;

import entities.*;
import tiles.*;

/**
 * A simple state machine to handle everything in the game
 */
public class Game {

    private java.util.List<Entity> entities = new ArrayList<>();
    private Entity trackingEntity;
    private TileMap tileMap;

    /**
     * Loads the game.
     */
    public void load() {
        trackingEntity = new Player(this);
        tileMap = new TileMap("res/test.map");
        entities.add(trackingEntity);
        entities.add(new TestBumper(this));
    }

    /**
     * Updates the state machine.
     */
    public void update() {
        for (Entity e : entities) {
            e.update();
        }
    }

    /**
     * Updates the graphics.
     * 
     * @param g            Graphics2D object to draw with
     * @param screenWidth  screen width
     * @param screenHeight screen height
     */
    public void draw(Graphics2D g, int screenWidth, int screenHeight) {
        /*
         * Offset from the top left corner of the screen to the top left corner of the
         * map, calculated so that trackingEntity is centered, except when it's close to
         * the edges of the map. If the map is smaller than the screen, the map is
         * simply centered.
         */
        int cameraOffsetX, cameraOffsetY;
        if (tileMap.getMapWidth() < screenWidth) {
            cameraOffsetX = -(screenWidth - tileMap.getMapWidth()) / 2;
        } else {
            cameraOffsetX = (int) (trackingEntity.x - screenWidth / 2d);
            cameraOffsetX = Math.min(Math.max(cameraOffsetX, 0), tileMap.getMapWidth() - screenWidth);
        }
        if (tileMap.getMapHeight() < screenHeight) {
            cameraOffsetY = -(screenHeight - tileMap.getMapHeight()) / 2;
        } else {
            cameraOffsetY = (int) (trackingEntity.y - screenHeight / 2d);
            cameraOffsetY = Math.min(Math.max(cameraOffsetY, 0), tileMap.getMapHeight() - screenHeight);
        }

        tileMap.draw(g, -cameraOffsetX, -cameraOffsetY, screenWidth, screenHeight);
        Collections.sort(entities, (Entity o1, Entity o2) -> {
            if (o1.y < o2.y)
                return -1;
            else if (o1.y > o2.y)
                return 1;
            return 0;
        });
        for (Entity e : entities) {
            e.draw(g, -cameraOffsetX, -cameraOffsetY);
        }
    }

    // TODO: TESTING
    public TileMap getTileMap() {
        return tileMap;
    }

    // TODO: TESTING
    public java.util.List<Entity> getEntities() {
        return entities;
    }

    // TODO: TESTING
    public void loadNewMap(String filename) {
        tileMap = new TileMap(filename);
    }

}
