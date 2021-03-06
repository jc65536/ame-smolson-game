import java.awt.image.*;

public class Tile {
    
    static final int WIDTH = 32;
    static final int HEIGHT = 32;
    int id;
    BufferedImage image;
    boolean passable;

    public int getId() {
        return id;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public boolean isPassable() {
        return passable;
    }

}
