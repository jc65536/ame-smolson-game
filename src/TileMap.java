import javax.swing.*;

import java.awt.*;
import java.io.*;
import java.util.*;

public class TileMap {

    Scene scene;
    java.util.List<java.util.List<Tile>> map;
    int rows, cols;

    public TileMap(Scene scene) {
        this.scene = scene;
        try {
            load("res/test.map"); // testing
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(String file) throws IOException {
        map = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;
        while ((line = in.readLine()) != null) {
            java.util.List<Tile> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                row.add(TileFactory.newTile(c));
            }
            map.add(row);
        }
        rows = map.size();
        cols = map.get(0).size();
        in.close();
    }

    public int getWidth() {
        return cols * Tile.WIDTH;
    }

    public int getHeight() {
        return rows * Tile.HEIGHT;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Tile getTile(int row, int col) {
        return map.get(row).get(col);
    }

    public void update(Graphics2D g2) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                g2.drawImage(map.get(i).get(j).getImage(), scene.translateX(j * Tile.WIDTH),
                        scene.translateY(i * Tile.HEIGHT), null);
            }
        }
    }

}
