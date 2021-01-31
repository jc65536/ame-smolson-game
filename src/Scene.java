import java.awt.*;

import javax.swing.*;
import java.util.*;

public class Scene extends JComponent {

    final static int SCENE_WIDTH = 800;
    final static int SCENE_HEIGHT = 512;

    Background background = new Background(this);
    java.util.List<Entity> entityList = new ArrayList<>();
    KeyboardManager k;

    Entity trackingEntity;
    int offsetX, offsetY;

    public Scene(KeyboardManager k) {
        this.k = k;
    }

    public void addEntity(Entity entity) {
        entityList.add(entity);     // add to list for logic calculations
    }

    public void setTrackingEntity(Entity trackingEntity) {
        this.trackingEntity = trackingEntity;
    }

    public KeyboardManager getKeyboardManager() {
        return k;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SCENE_WIDTH, SCENE_HEIGHT);
    }

    public int translateX(double x) {
        return (int) x - offsetX;
    }

    public int translateY(double y) {
        return (int) y - offsetY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        offsetX = (int) Math.min(background.getWidth() - SCENE_WIDTH, Math.max(0, trackingEntity.getX() - SCENE_WIDTH / 2));
        offsetY = (int) Math.min(background.getHeight() - SCENE_HEIGHT, Math.max(0, trackingEntity.getY() - SCENE_HEIGHT / 2));

        Graphics2D g2 = (Graphics2D) g;
        background.update(g2);
        for (Entity e : entityList) {
            e.update(g2);
        }
    }
}
