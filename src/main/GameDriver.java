package main;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.util.*;

import ui.*;
import util.*;

/**
 * A driver class to separate the threading stuff from class Game
 */
public class GameDriver {

    private static final int MS = 1000000;
    private static final int TICK_DURATION = 16 * MS;
    private volatile boolean running = true; // volatile bc we're reading/writing it from different threads

    private SwingWorker loadingWorker;
    private Thread gameThread;

    private GameScreen screen;
    private JPanel canvas;
    private Game game;

    private Runnable driverLoop = new Runnable() {

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
        }

    };

    public GameDriver(GameScreen screen, JPanel canvas) {
        this.screen = screen;
        this.canvas = canvas;

        InputMap im = screen.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = screen.getActionMap();
        im.put(KeyStroke.getKeyStroke("pressed UP"), "inc");
        am.put("inc", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                game.increment();
            }

        });
    }

    /**
     * Initializes the game in the background and starts the game thread
     * @return the new game state (which may not be fully loaded)
     */
    public Game start() {
        game = new Game();

        loadingWorker = new SwingWorker<Void, Void>() {

            int r = (int) (Math.random() * 1000);
            boolean doneLoading = false;

            @Override
            protected Void doInBackground() {
                Logger.log("Starting to load game " + r);
                // TODO: load everything into game
                Scanner s = new Scanner(System.in);
                game.testingInt = s.nextInt();
                Logger.log("Done loading game " + r);
                doneLoading = true;
                return null;
            }

            @Override
            protected void done() {
                if (doneLoading) {
                    running = true;
                    gameThread = new Thread(driverLoop);
                    gameThread.start();
                    Logger.log("Game thread started " + r);
                } else {
                    Logger.log("Game load cancelled " + r);
                }
            }

        };

        loadingWorker.execute();

        return game;
    }

    public void close() {
        // TODO: release all resources and close the thread
        loadingWorker.cancel(true);
        running = false;
        game = null;
    }

}
