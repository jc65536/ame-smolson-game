package entities;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.awt.event.*;

import graphics.*;
import main.*;
import tiles.Tile;
import tiles.TileMap;
import util.*;

public class Player extends Entity {

    // Displayable assets
    private Map<Direction, Animation> directionSprites = new HashMap<>();
    private Map<Direction, Boolean> arrowsPressed = new HashMap<>();
    private Direction currentDir = Direction.S;
    private boolean moving = false;
    private int accelDelay = 5;

    private Game gameContext;

    public Player(Game gameContext) {
        this.gameContext = gameContext;

        try {
            SpriteSheet ameSheet = new SpriteSheet("res/ame_walk16.png", 4, 6);
            Direction[] enumDirs = Direction.enumDirs();
            for (int i = 0; i < enumDirs.length; i++) {
                int r = i / 2;
                int c = (i % 2) * 3;
                directionSprites.put(enumDirs[i], new Animation(
                        ameSheet.getImages(new int[][] { { r, c }, { r, c + 1 }, { r, c }, { r, c + 2 } }), 12));
            }
        } catch (Exception e) {
            Logger.log("Couldn't load player images");
            e.printStackTrace();
        }

        shadow = new Rectangle(24, 24);
        setPos(100, 150);

        for (Direction d : Direction.arrowDirs()) {
            arrowsPressed.put(d, false);
            Main.addKeyBinding(Main.GAME_SCREEN, "pressed " + d, "PlayerPressedArrow" + d, (ActionEvent e) -> {
                if (!arrowsPressed.get(d)) {
                    arrowsPressed.put(d, true);
                    setDirection();
                }
            });
            Main.addKeyBinding(Main.GAME_SCREEN, "released " + d, "PlayerReleasedArrow " + d, (ActionEvent e) -> {
                arrowsPressed.put(d, false);
                setDirection();
            });
        }
    }

    /**
     * Sets currentDir based on the booleans in arrowsPressed
     */
    private void setDirection() {
        Direction newDir = null;
        if (arrowsPressed.get(Direction.N)) {
            if (arrowsPressed.get(Direction.E)) {
                newDir = Direction.NE;
            } else if (arrowsPressed.get(Direction.W)) {
                newDir = Direction.NW;
            } else {
                newDir = Direction.N;
            }
        } else if (arrowsPressed.get(Direction.S)) {
            if (arrowsPressed.get(Direction.E)) {
                newDir = Direction.SE;
            } else if (arrowsPressed.get(Direction.W)) {
                newDir = Direction.SW;
            } else {
                newDir = Direction.S;
            }
        } else {
            if (arrowsPressed.get(Direction.E)) {
                newDir = Direction.E;
            } else if (arrowsPressed.get(Direction.W)) {
                newDir = Direction.W;
            } else {
                moving = false;
                vx = vy = 0;
                directionSprites.get(currentDir).restart();
                return;
            }
        }

        if (newDir != currentDir && !moving) {
            accelDelay = 8;
        }

        currentDir = newDir;
        moving = true;
    }

    @Override
    public void draw(Graphics2D g2, int originX, int originY) {
        TileMap map = gameContext.getTileMap();
        g2.setColor(new Color(1f, 0f, 0f, 0.5f));
        for (int r = shadow.y / Tile.HEIGHT; r <= (shadow.y + shadow.height) / Tile.HEIGHT; r++) {
            for (int c = shadow.x / Tile.WIDTH; c <= (shadow.x + shadow.width) / Tile.WIDTH; c++) {
                g2.fillRect(c * Tile.WIDTH + originX, r * Tile.HEIGHT + originY, Tile.WIDTH, Tile.HEIGHT);
            }
        }
        BufferedImage sprite = directionSprites.get(currentDir).getImage();
        g2.drawImage(sprite, (int) (x - sprite.getWidth()) + originX,
                (int) (y - sprite.getHeight() * 2 + shadow.height / 2) + originY, sprite.getWidth() * 2,
                sprite.getHeight() * 2, null);
        g2.setColor(new Color(0f, 1f, 0f, 0.5f));
        g2.fillRect(shadow.x + originX, shadow.y + originY, shadow.width, shadow.height);
        g2.setColor(new Color(0f, 0f, 1f));
        g2.setFont(new Font("Arial", 0, 20));
        g2.drawString(currentDir.toString() + " " + x + " " + y, 100, 500);
    }

    @Override
    public void update() {
        if (moving) {
            if (accelDelay > 0) {
                accelDelay--;
            } else {
                vx = 4 * currentDir.getX();
                vy = 4 * currentDir.getY();
                directionSprites.get(currentDir).update();
            }
        }
        double oldX = x, oldY = y;
        setPos(x + vx, y + vy);
        if (!collisionCheck()) {
            setPos(oldX, oldY);
        }
    }

    private boolean collisionCheck() {
        TileMap map = gameContext.getTileMap();
        for (int r = (int) Math.floor((double) shadow.y / Tile.HEIGHT); r <= (int) Math.floor((double) (shadow.y + shadow.height) / Tile.HEIGHT); r++) {
            for (int c = (int) Math.floor((double) shadow.x / Tile.WIDTH); c <= (int) Math.floor((double) (shadow.x + shadow.width) / Tile.WIDTH); c++) {
                if (r < 0 || c < 0 || r >= map.getRows() || c >= map.getColumns() || !map.getTile(r, c).passable) {
                    return false;
                }
                if (map.getTile(r, c).id == 'P') {
                    gameContext.loadNewMap("res/test_cave.map");
                    setPos(400, 460);
                    return true;
                }
                if (map.getTile(r, c).id == 'Q') {
                    gameContext.loadNewMap("res/test.map");
                    setPos(650, 200);
                    return true;
                }
            }
        }
        return true;
    }

    private void setPos(double x, double y) {
        this.x = x;
        this.y = y;
        shadow.x = (int) (x - shadow.width / 2);
        shadow.y = (int) (y - shadow.height / 2);
    }

}
