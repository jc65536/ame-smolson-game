package ui;

import javax.swing.*;
import java.awt.*;

public abstract class Screen extends JPanel {

    /**
     * Called by Main upon switching to screen
     */
    public void load() {}

    /**
     * Called by Main upon closing the screen
     */
    public void close() {}
    
}
