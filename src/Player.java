import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Player extends Entity {

    Animation leftStepAnimation;
    Animation rightStepAnimation;
    Animation standAnimation;
    boolean facingLeft = true;
    int row, col;

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
        x = y = 100;
        width = 38;
        height = 60;
    }

    void calculateGridPos() {
        row = (int) (y / TileMap.TILE_SIZE);
        col = (int) (x / TileMap.TILE_SIZE);
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
        x += vx;
        y += vy;

        if (k.keyDown(KeyEvent.VK_SPACE)) {
            scene.background.tm.map.get(row).set(col, '.');         // YOU'D BETTER CHANGE THIS
        }

        calculateGridPos();
        g2.setColor(Color.RED);
        g2.fillRect(scene.translateX(col * TileMap.TILE_SIZE), scene.translateY(row * TileMap.TILE_SIZE), TileMap.TILE_SIZE, TileMap.TILE_SIZE);

        if (moving) {
            if (!animation.playing()) {
                if (animation == leftStepAnimation && !animation.playing()) {
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
            g2.drawImage(animation.getImage(), scene.translateX(x - width / 2), scene.translateY(y - height), width, height, null);
        } else {
            g2.drawImage(animation.getImage(), scene.translateX(x - width / 2) + width, scene.translateY(y - height), -width, height, null);
        }
    }

}
