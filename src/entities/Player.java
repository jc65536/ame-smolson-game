package entities;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import graphics.*;
import main.Main;
import util.*;

public class Player extends Entity {

    // Displayable assets
    BufferedImage east, nEast, north, nWest, west, sWest, south, sEast;
    BufferedImage currentDirection;

    public Player() {
        SpriteSheet directions;
        try {
            directions = new SpriteSheet("res/directions.png", 2, 4);
            east = directions.getImage(0, 0);
            nEast = directions.getImage(0, 1);
            north = directions.getImage(0, 2);
            nWest = directions.getImage(0, 3);
            west = directions.getImage(1, 0);
            sWest = directions.getImage(1, 1);
            south = directions.getImage(1, 2);
            sEast = directions.getImage(1, 3);
        } catch (Exception e) {
            Logger.log("Couldn't load player images");
            e.printStackTrace();
        }
        currentDirection = south;

        shadow = new Rectangle(36, 36);
        setPos(100, 100);

        Main.addKeyBinding(Main.GAME_SCREEN, "pressed UP", "PlayerMoveUp", (ActionEvent e) -> {
            vy = -4;
        });
        Main.addKeyBinding(Main.GAME_SCREEN, "pressed DOWN", "PlayerMoveDown", (ActionEvent e) -> {
            vy = 4;
        });
        Main.addKeyBinding(Main.GAME_SCREEN, "pressed LEFT", "PlayerMoveLeft", (ActionEvent e) -> {
            vx = -4;
        });
        Main.addKeyBinding(Main.GAME_SCREEN, "pressed RIGHT", "PlayerMoveRight", (ActionEvent e) -> {
            vx = 4;
        });
        Main.addKeyInput(Main.GAME_SCREEN, "released UP", "PlayerStopY");
        Main.addKeyInput(Main.GAME_SCREEN, "released DOWN", "PlayerStopY");
        Main.addKeyInput(Main.GAME_SCREEN, "released LEFT", "PlayerStopX");
        Main.addKeyInput(Main.GAME_SCREEN, "released RIGHT", "PlayerStopX");
        Main.addKeyAction(Main.GAME_SCREEN, "PlayerStopY", (ActionEvent e) -> {
            vy = 0;
        });
        Main.addKeyAction(Main.GAME_SCREEN, "PlayerStopX", (ActionEvent e) -> {
            vx = 0;
        });
    }

    @Override
    public void draw(Graphics2D g2, int originX, int originY) {
        g2.drawImage(currentDirection, (int) (x - currentDirection.getWidth()) + originX,
                (int) (y - currentDirection.getHeight() * 2 + shadow.height / 2) + originY,
                currentDirection.getWidth() * 2, currentDirection.getHeight() * 2, null);
        g2.setColor(new Color(1f, 0f, 0f, 0.3f));
        g2.fillRect(shadow.x + originX, shadow.y + originY, shadow.width, shadow.height);
    }

    @Override
    public void update() {
        setPos(x + vx, y + vy);
    }

    private void setPos(double x, double y) {
        this.x = x;
        this.y = y;
        shadow.x = (int) (x - shadow.width / 2);
        shadow.y = (int) (y - shadow.height / 2);
    }

}
