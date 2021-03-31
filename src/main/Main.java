package main;

import javax.swing.*;
import java.awt.event.*;
import java.util.function.*;

import ui.*;
import util.*;

/**
 * Main handles the UI stuff (opening windows, switching between screens,
 * opening dialogs, etc.)
 */
public class Main {

    public static final int TITLE_SCREEN = 0;
    public static final int GAME_SCREEN = 1;

    private static final int DEFAULT_WINDOW_WIDTH = 800;
    private static final int DEFAULT_WINDOW_HEIGHT = 600;

    private static JFrame window = new JFrame();
    private static Screen[] screens = { new TitleScreen(), new GameScreen() };
    private static int currentScreen = TITLE_SCREEN;

    /**
     * Calls the close method of the current screen, replaces the screen, then calls
     * the load method of the new screen.
     * 
     * @param nextScreen {@code Main.TITLE_SCREEN}, for example
     */
    public static void switchScreen(int nextScreen) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                screens[currentScreen].close();
                window.remove(screens[currentScreen]);
                Logger.log("Closed " + screens[currentScreen].getClass().getName());
                currentScreen = nextScreen;
                window.add(screens[currentScreen]);
                screens[currentScreen].load();
                window.revalidate();
                window.repaint();
                Logger.log("Opened " + screens[currentScreen].getClass().getName());
            }

        });
    }

    /**
     * Allows other classes to add their own key inputs since they don't have access
     * to a JComponent's InputMap.
     * 
     * @param screen    the screen on which the input will happen
     * @param keyStroke string describing the key stroke. See <a href=
     *                  "https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/javax/swing/KeyStroke.html#getKeyStroke(java.lang.String)">KeyStroke</a>
     *                  for more info.
     * @param key       a key object identifying the action
     */
    public static void addKeyInput(int screen, String keyStroke, Object key) {
        screens[screen].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyStroke), key);
    }

    /**
     * Allows other classes to add their own key inputs since they don't have access
     * to a JComponent's ActionMap.
     * 
     * @param screen the screen on which the input will happen
     * @param key    usually a string describing the action
     * @param c      implements the action
     */
    public static void addKeyAction(int screen, Object key, Consumer<ActionEvent> c) {
        screens[screen].getActionMap().put(key, new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                c.accept(e);
            }

        });
    }

    /**
     * Shorthand method combining {@link main.Main#addKeyInput(int, String, Object)
     * addKeyInput} and {@link main.Main#addKeyAction(int, Object, Consumer)
     * addKeyAction}.
     * 
     * @param screen    the screen on which the input will happen
     * @param keyStroke string describing the key stroke. See <a href=
     *                  "https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/javax/swing/KeyStroke.html#getKeyStroke(java.lang.String)">KeyStroke</a>
     *                  for more info.
     * @param key       usually a string describing the action
     * @param c         implements the action
     */
    public static void addKeyBinding(int screen, String keyStroke, Object key, Consumer<ActionEvent> c) {
        addKeyInput(screen, keyStroke, key);
        addKeyAction(screen, key, c);
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
        window.add(screens[currentScreen]);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        window.setVisible(true);
    }

}