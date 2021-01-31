import java.awt.*;

import javax.swing.*;

public abstract class Entity {
    protected Animation animation;
    String name;
    protected Scene scene;              // keep a reference to the scene in which I exist in order to draw myself
    protected double x, y;
    protected int width, height;
    
    public Entity(String name, Scene scene) {
        this.name = name;
        this.scene = scene;
        scene.addEntity(this);
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract void update(Graphics2D g2);      // logic and graphical updates
}
