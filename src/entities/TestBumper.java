package entities;

import java.awt.*;
import graphics.*;
import util.*;
import game.*;

public class TestBumper extends Entity {

    private static final int PX = Constants.PX;
    private static final int SPRITE_WIDTH = 16 * PX;
    private static final int SPRITE_HEIGHT = 16 * PX;

    private Animation img;

    public TestBumper(Game gameContext) {
        super(gameContext);
        x = 300;
        y = 250;
        shadow = new Rectangle((int) x - 16, (int) y - 16, 32, 32);
        try {
            SpriteSheet sheet = new SpriteSheet("res/test_entities.png", 4, 4);
            img = new Animation(sheet.getImages(new int[][] { { 0, 0 }, { 0, 2 } }), 20);
        } catch (Exception e) {
            Logger.log("Failed to load TestBumper");
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g, int originX, int originY) {
        var sprite = img.getImage();
        g.drawImage(sprite, (int) (x - SPRITE_WIDTH / 2) + originX,
                (int) (y - SPRITE_HEIGHT + shadow.height / 2) + originY, SPRITE_WIDTH, SPRITE_HEIGHT, null);
        g.setColor(new Color(1f, 1f, 0f, 0.5f));
        g.fillRect(shadow.x + originX, shadow.y + originY, shadow.width, shadow.height);
    }

    @Override
    public void update() {
        img.update();
        gameContext.getEntities().forEach((Entity e) -> {
            if (e != this && e.shadow.intersects(shadow) && e instanceof Player) {
                ((Player) e).bounce();
            }
        });
    }

}
