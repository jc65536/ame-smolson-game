import javax.swing.*;

import java.awt.*;
import java.io.*;

public class Background {

    TileMap tm; // temporary testing tilemap
    Scene scene;

    public Background(Scene scene) {
        this.scene = scene;
        try {
            tm = new TileMap("res/test.map");
        } catch (IOException e) {
            System.out.println("[ERROR] Background failed to initialize tilemaps");
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return tm.getCols() * TileMap.TILE_SIZE;
    }

    public int getHeight() {
        return tm.getRows() * TileMap.TILE_SIZE;
    }

    public void update(Graphics2D g2) {
        for (int i = 0; i < tm.getRows(); i++) {
            for (int j = 0; j < tm.getCols(); j++) {
                g2.drawImage(tm.getImage(i, j), scene.translateX(j * TileMap.TILE_SIZE), scene.translateY(i * TileMap.TILE_SIZE), null);
            }
        }
    }

}
