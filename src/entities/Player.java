package entities;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

import graphics.*;
import main.*;
import tiles.*;
import util.*;
import game.*;

public class Player extends Entity {

    // Shorthand constants
    private static final int PX = Constants.PX;
    private static final int SPRITE_WIDTH = 16 * PX;
    private static final int SPRITE_HEIGHT = 32 * PX;

    // Displayable assets
    private Map<Direction, Animation> directionSprites = new HashMap<>();
    private Map<Direction, Boolean> arrowsPressed = new HashMap<>();
    private Direction direction = Direction.S;
    private boolean controllable = true;
    private boolean moving = false;
    private int accelDelay = 0;
    private int stunDelay = 0;
    private double vx, vy;

    public Player(Game gameContext) {
        super(gameContext);

        try {
            SpriteSheet spriteSheet = new SpriteSheet("res/ame_walk16.png", 4, 6);
            Direction[] enumDirs = Direction.enumDirs();
            for (int i = 0; i < enumDirs.length; i++) {
                int r = i / 2;
                int c = (i % 2) * 3;
                directionSprites.put(enumDirs[i], new Animation(
                        spriteSheet.getImages(new int[][] { { r, c }, { r, c + 1 }, { r, c }, { r, c + 2 } }), 12));
            }
        } catch (Exception e) {
            Logger.log("Couldn't load player images");
            e.printStackTrace();
        }

        shadow = new Rectangle(12 * PX, 12 * PX);
        setPos(100, 150);

        for (Direction d : Direction.arrowDirs()) {
            arrowsPressed.put(d, false);
            Main.addKeyBinding(Main.GAME_SCREEN, "pressed " + d, "PlayerPressedArrow" + d, (ActionEvent e) -> {
                if (!arrowsPressed.get(d)) {
                    arrowsPressed.put(d, true);
                    if (controllable)
                        setDirection();
                }
            });
            Main.addKeyBinding(Main.GAME_SCREEN, "released " + d, "PlayerReleasedArrow " + d, (ActionEvent e) -> {
                arrowsPressed.put(d, false);
                if (controllable) {
                    setDirection();
                }
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
                directionSprites.get(direction).restart();
                return;
            }
        }

        if (newDir != direction && !moving)
            accelDelay = 6;
        direction = newDir;
        moving = true;
    }

    @Override
    public void draw(Graphics2D g, int originX, int originY) {
        g.setColor(new Color(1f, 0f, 0f, 0.5f));
        for (int r = shadow.y / Tile.HEIGHT; r <= (shadow.y + shadow.height) / Tile.HEIGHT; r++) {
            for (int c = shadow.x / Tile.WIDTH; c <= (shadow.x + shadow.width) / Tile.WIDTH; c++) {
                g.fillRect(c * Tile.WIDTH + originX, r * Tile.HEIGHT + originY, Tile.WIDTH, Tile.HEIGHT);
            }
        }
        BufferedImage sprite = directionSprites.get(direction).getImage();
        g.drawImage(sprite, (int) (x - SPRITE_WIDTH / 2) + originX,
                (int) (y - SPRITE_HEIGHT + shadow.height / 2) + originY, SPRITE_WIDTH, SPRITE_HEIGHT, null);
        g.setColor(new Color(0f, 1f, 0f, 0.5f));
        g.fillRect(shadow.x + originX, shadow.y + originY, shadow.width, shadow.height);
        g.setColor(new Color(0f, 0f, 1f));
        g.setFont(new Font("Arial", 0, 20));
        g.drawString(direction.toString() + " " + x + " " + y, 100, 500);
    }

    @Override
    public void update() {
        if (controllable) {
            if (moving) {
                if (accelDelay == 0) {
                    vx = 4 * direction.getX();
                    vy = 4 * direction.getY();
                } else {
                    accelDelay--;
                }
                directionSprites.get(direction).update();
            }
        } else if (stunDelay > 0) {
            stunDelay--;
        } else {
            controllable = true;
            setDirection();
        }

        double oldX = x, oldY = y;

        // Collision check
        TileMap map = gameContext.getTileMap();
        setPos(x + vx, y);
        int firstRow = (int) Math.floor((double) shadow.y / Tile.HEIGHT);
        int lastRow = (int) Math.floor((double) (shadow.y + shadow.height) / Tile.HEIGHT);
        int firstCol = (int) Math.floor((double) shadow.x / Tile.WIDTH);
        int lastCol = (int) Math.floor((double) (shadow.x + shadow.width) / Tile.WIDTH);
        if (firstCol < 0 || lastCol >= map.getCols()) {
            setPos(oldX, y);
            firstCol = (int) Math.floor((double) shadow.x / Tile.WIDTH);
            lastCol = (int) Math.floor((double) (shadow.x + shadow.width) / Tile.WIDTH);
        }
        for (int r = firstRow; r <= lastRow; r++) {
            if (!map.getTile(r, firstCol).passable || !map.getTile(r, lastCol).passable) {
                setPos(oldX, y);
            }
        }
        setPos(x, y + vy);
        firstRow = (int) Math.floor((double) shadow.y / Tile.HEIGHT);
        lastRow = (int) Math.floor((double) (shadow.y + shadow.height) / Tile.HEIGHT);
        firstCol = (int) Math.floor((double) shadow.x / Tile.WIDTH);
        lastCol = (int) Math.floor((double) (shadow.x + shadow.width) / Tile.WIDTH);

        if (firstRow < 0 || lastRow >= map.getRows()) {
            setPos(x, oldY);
            firstRow = (int) Math.floor((double) shadow.y / Tile.HEIGHT);
            lastRow = (int) Math.floor((double) (shadow.y + shadow.height) / Tile.HEIGHT);
        }
        for (int c = firstCol; c <= lastCol; c++) {
            if (!map.getTile(firstRow, c).passable || !map.getTile(lastRow, c).passable) {
                setPos(x, oldY);
            }
        }

        firstRow = (int) Math.floor((double) shadow.y / Tile.HEIGHT);
        lastRow = (int) Math.floor((double) (shadow.y + shadow.height) / Tile.HEIGHT);
        firstCol = (int) Math.floor((double) shadow.x / Tile.WIDTH);
        lastCol = (int) Math.floor((double) (shadow.x + shadow.width) / Tile.WIDTH);
        for (int r = firstRow; r <= lastRow; r++) {
            for (int c = firstCol; c <= lastCol; c++) {
                Tile t = map.getTile(r, c);
                switch (t.id) {
                case 'P':
                    gameContext.loadNewMap("res/" + t.destination);
                    setPos(300, 150);
                    break;
                }
            }
        }
    }

    private void setPos(double x, double y) {
        this.x = x;
        this.y = y;
        shadow.x = (int) (x - shadow.width / 2);
        shadow.y = (int) (y - shadow.height / 2);
    }

    // TODO: TESTING
    public void bounce() {
        vx = -vx;
        vy = -vy;
        stunDelay = 8;
        controllable = false;
    }

}
