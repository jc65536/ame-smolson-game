package main;

import javax.swing.*;

import util.*;
import game.*;

/**
 * A driver class to separate the threading stuff from class Game
 */
public class GameDriver {

    private static final int MS = 1000000;
    private static final int TICK_DURATION = 16 * MS;
    private volatile boolean running = true; // volatile because we're reading/writing it from different threads

    private SwingWorker<Void, Void> loader;
    private Thread gameThread;

    private JPanel canvas;
    private Game game;

    private Runnable driver = new Runnable() {

        @Override
        public void run() {
            Logger.log("Starting driver loop");
            long lastTime = System.nanoTime();
            long timePassed = 0;
            while (running) {
                long now = System.nanoTime();
                timePassed += now - lastTime;
                lastTime = now;
                while (timePassed >= TICK_DURATION) {
                    game.update();
                    canvas.repaint();
                    timePassed -= TICK_DURATION;
                }
            }
            Logger.log("Driver loop ended");
        }

    };

    public GameDriver(JPanel canvas) {
        this.canvas = canvas;
    }

    /**
     * Initializes the game in the background and starts the game thread
     * 
     * @return the new Game (which may not be loaded)
     */
    public Game start() {
        game = new Game();

        loader = new SwingWorker<>() {

            boolean doneLoading = false;

            @Override
            protected Void doInBackground() {
                Logger.log("Starting to load game");
                game.load();
                Logger.log("Done loading game");
                doneLoading = true;
                return null;
            }

            @Override
            protected void done() {
                if (doneLoading) {
                    running = true;
                    gameThread = new Thread(driver);
                    gameThread.setName("GameThread");
                    gameThread.start();
                    canvas.setVisible(true);
                    Logger.log("Game thread started");
                } else {
                    Logger.log("Game loading cancelled");
                }
            }

        };

        loader.execute();

        return game;
    }

    public void close() {
        canvas.setVisible(false);
        loader.cancel(true);
        running = false;
        game = null;
        System.gc();
    }

}
