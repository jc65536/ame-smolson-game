package ui;

import javax.swing.*;

public abstract class Screen extends JPanel {

    /**
     * Called upon switching to the screen.
     */
    public void load() {
    }

    /**
     * Called upon switching from the screen.
     */
    public void close() {
    }

}
