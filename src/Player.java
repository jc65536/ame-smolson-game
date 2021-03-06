import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Player extends Entity {

    Animation leftStepAnimation;
    Animation rightStepAnimation;
    Animation standAnimation;
    boolean facingLeft = true;
    int row, col;
    Rectangle hitBox;
    final int hitBoxWidth = 30, hitBoxHeight = 54;

    public Player(Scene scene) {
        super("Player", scene);

        scene.setTrackingEntity(this);

        SpriteSheet sheet;
        int[][] leftStepFrames = { { 0, 1 }, { 0, 2 } };
        int[][] rightStepFrames = { { 0, 3 }, { 0, 0 } };
        int[][] standFrames = { { 0, 0 } };
        try {
            sheet = new SpriteSheet("res/walk.png", 1, 4);
            leftStepAnimation = new Animation("left step", sheet.getImages(leftStepFrames), 20);
            rightStepAnimation = new Animation("right step", sheet.getImages(rightStepFrames), 20);
            standAnimation = new Animation("stand", sheet.getImages(standFrames), 1);
        } catch (IOException e) {
            System.out.println("[ERROR] Player failed to initialize images");
            e.printStackTrace();
        }

        animation = standAnimation;
        x = y = 50;
        calculateGridPos();
        calculateHitBox();
        width = 38;
        height = 60;
    }

    void calculateGridPos() {
        row = (int) (y / Tile.WIDTH);
        col = (int) (x / Tile.HEIGHT);
    }

    void calculateHitBox() {
        hitBox = new Rectangle((int) (x - hitBoxWidth / 2), (int) (y - hitBoxHeight), hitBoxWidth, hitBoxHeight);
    }

    public void update(Graphics2D g2) {
        KeyboardManager k = scene.getKeyboardManager();
        boolean moving = false;
        double vx = 0;
        double vy = 0;
        if (k.keyDown(KeyEvent.VK_LEFT)) {
            vx = -2;
            moving = true;
            facingLeft = true;
        }
        if (k.keyDown(KeyEvent.VK_UP)) {
            vy = -2;
            moving = true;
        }
        if (k.keyDown(KeyEvent.VK_RIGHT)) {
            vx = 2;
            moving = true;
            facingLeft = false;
        }
        if (k.keyDown(KeyEvent.VK_DOWN)) {
            vy = 2;
            moving = true;
        }
        if (vx * vx + vy * vy == 8) {
            vx = (vx < 0 ? -1 : 1) * Math.sqrt(Math.abs(vx));
            vy = (vy < 0 ? -1 : 1) * Math.sqrt(Math.abs(vy));
        }

        // Tile collision check
        double newX = x + vx;
        double newY = y + vy;
        int x1 = (int) (newX + (vx > 0 ? 1 : -1) * hitBoxWidth / 2);
        try {
            Tile t1 = scene.getMap().getTile((int) (newY / Tile.HEIGHT), (int) (x / Tile.WIDTH));
            Tile t2 = scene.getMap().getTile((int) (y / Tile.HEIGHT), x1 / Tile.WIDTH);
            
            if (t1.isPassable()) {
                y = newY;
            }
            if (t2.isPassable()) {
                x = newX;
            }
        } catch (IndexOutOfBoundsException e) { // ran out of the map
        }

        if (k.keyDown(KeyEvent.VK_SPACE)) {
            scene.map.map.get(row).set(col, TileFactory.newTile('.')); // YOU BETTER CHANGE THIS
        }

        calculateGridPos();
        calculateHitBox();
        g2.setColor(Color.RED);
        g2.fillRect(scene.translateX(col * Tile.WIDTH), scene.translateY(row * Tile.HEIGHT), Tile.WIDTH, Tile.HEIGHT);

        if (moving) {
            if (!animation.playing()) {
                if (animation == leftStepAnimation) {
                    animation = rightStepAnimation;
                    animation.restart();
                    animation.start();
                } else {
                    animation = leftStepAnimation;
                    animation.restart();
                    animation.start();
                }
            }
        } else {
            animation = standAnimation;
        }

        animation.update();
        if (facingLeft) {
            g2.drawImage(animation.getImage(), scene.translateX(x - width / 2), scene.translateY(y - height), width,
                    height, null);
        } else {
            g2.drawImage(animation.getImage(), scene.translateX(x - width / 2) + width, scene.translateY(y - height),
                    -width, height, null);
        }

        g2.setColor(new Color(1f, 0f, 0f, 0.5f));
        g2.fillRect(scene.translateX(hitBox.x), scene.translateY(hitBox.y), hitBox.width, hitBox.height);
    }

}
