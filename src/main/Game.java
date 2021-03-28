package main;

import java.util.*;
import java.awt.*;

/**
 * A simple state machine to handle everything in the game
 */
public class Game {

    public int testingInt = -1;

    public Game() {
        
    }

    public void increment() {
        testingInt++;
    }

    /**
     * Called by the driver loop in GameDriver to update the state machine and drive the game logic
     */
    public void update() {
        
    }

    /**
     * Called by paintComponent() in GameScreen to update the graphics
     * @param g
     */
    public void draw(Graphics2D g) {
        g.drawRect(10, 10, testingInt, testingInt);
    }

}
