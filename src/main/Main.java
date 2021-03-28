package main;

import javax.swing.*;
import java.awt.*;

import ui.*;
import util.*;

/**
 * Main handles the UI stuff (opening windows, switching between screens, opening dialogs, etc.)
 */
public class Main {

    static final int DEFAULT_WINDOW_WIDTH = 800;
    static final int DEFAULT_WINDOW_HEIGHT = 600;

    private static JFrame window = new JFrame();
    
    public static TitleScreen titleScreen;
    public static GameScreen gameScreen;

    private static Screen currentScreen;

    public static void switchScreen(Screen nextScreen) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                currentScreen.close();
                window.remove(currentScreen);
                Logger.log("Closed screen " + currentScreen.getClass().getName());
                currentScreen = nextScreen;
                currentScreen.load();
                window.add(currentScreen);
                window.revalidate();
                window.repaint();
                Logger.log("Opened screen " + currentScreen.getClass().getName());
            }

        });
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                createAndShowGUI();
            }

        });
    }

    private static void createAndShowGUI() {
        titleScreen = new TitleScreen();
        gameScreen = new GameScreen();

        currentScreen = titleScreen;
        window.add(currentScreen);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        window.setVisible(true);
    }

}