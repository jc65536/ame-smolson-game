import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Scene extends JComponent {

    final static int WIDTH = 800;
    final static int HEIGHT = 512;

    TileMap map = new TileMap(this);
    java.util.List<Entity> entities = new ArrayList<>();
    KeyboardManager k;

    Entity trackingEntity;          // the entity that the camera will follow (mostly the player)
    int offsetX, offsetY;           // camera offset in pixels from the top left corner of the map

    public Scene(KeyboardManager k) {
        this.k = k;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);     // add to list for logic calculations
    }

    public void setTrackingEntity(Entity trackingEntity) {
        this.trackingEntity = trackingEntity;
    }

    public KeyboardManager getKeyboardManager() {
        return k;
    }
    
    public TileMap getMap() {
        return map;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    public int translateX(int x) {
        return x - offsetX;
    }

    public int translateX(double x) {
        return translateX((int) x);
    }

    public int translateY(int y) {
        return y - offsetY;
    }

    public int translateY(double y) {
        return translateY((int) y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        offsetX = (int) Math.min(map.getWidth() - WIDTH, Math.max(0, trackingEntity.getX() - WIDTH / 2));
        offsetY = (int) Math.min(map.getHeight() - HEIGHT, Math.max(0, trackingEntity.getY() - HEIGHT / 2));

        Graphics2D g2 = (Graphics2D) g;
        map.update(g2);
        for (Entity e : entities) {
            e.update(g2);
        }
    }
}
