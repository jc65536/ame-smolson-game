package tiles;

import java.awt.image.*;

public class Tile {

    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;

    public int id;
    public Animation image;
    public boolean passable = true;
    public String destination;

}
