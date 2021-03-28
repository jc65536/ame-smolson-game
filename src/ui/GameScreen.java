package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import main.*;

public class GameScreen extends Screen {

    private JLayeredPane layeredPane = new JLayeredPane();

    private JPanel canvas = new JPanel() {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            game.draw((Graphics2D) g);
        }

    };

    private JPanel uiContainer = new JPanel();
    private JLabel title = new JLabel("Game Screen");
    private JButton testButton = new JButton("testButton"); // TESTING

    private GameDriver gameDriver;
    private Game game;

    /**
     * Builds the basic component structure of the screen. Does not actually start the game.
     */
    public GameScreen() {
        setLayout(new BorderLayout());
        add(layeredPane);
        canvas.setBackground(new Color(0xccffcc)); // TESTING
        layeredPane.add(canvas, Integer.valueOf(0));
        uiContainer.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        uiContainer.add(title, c);
        c.gridx = 2;
        uiContainer.add(testButton, c);
        c.weightx = 0.8;
        c.gridx = 1;
        uiContainer.add(Box.createGlue(), c);
        uiContainer.setOpaque(false);
        layeredPane.add(uiContainer, Integer.valueOf(1));

        // Keeps canvas and uiContainer always maximized
        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                Dimension newSize = e.getComponent().getSize();
                canvas.setSize(newSize);
                uiContainer.setSize(newSize);
                revalidate();
                repaint();
            }

        });

        gameDriver = new GameDriver(this, canvas);
        testButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Main.switchScreen(Main.titleScreen);
            }

        });
    }

    @Override
    public void load() {
        game = gameDriver.start();
    }

    @Override
    public void close() {
        gameDriver.close();
    }

}
