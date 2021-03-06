package tiles;

import java.io.*;
import java.util.*;
import java.awt.*;

import util.*;

public class TileMap {

    private java.util.List<java.util.List<Tile>> tiles;
    private int mapWidth, mapHeight, rows, columns;

    public TileMap(String mapFile) {
        tiles = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(mapFile))) {
            String line;
            java.util.List<Tile> row = new ArrayList<>();
            java.util.List<Tile> portals = new ArrayList<>();
            int portalIt = 0;
            int section = 0;
            while ((line = r.readLine()) != null) {
                if (line.length() == 0) {
                    section++;
                    continue;
                }
                switch (section) {
                case 0:
                    for (char c : line.toCharArray()) {
                        Tile t = TileFactory.newTile(c);
                        if (t.id == 'P')
                            portals.add(t);
                        row.add(t);
                    }
                    tiles.add(row);
                    row = new ArrayList<>();
                    break;
                case 1:
                    if (portalIt < portals.size()) {
                        portals.get(portalIt).destination = line;
                        portalIt++;
                    }
                    break;
                }
            }
            rows = tiles.size();
            columns = tiles.get(0).size();
            mapHeight = Tile.HEIGHT * rows;
            mapWidth = Tile.WIDTH * columns;
        } catch (Exception e) {
            Logger.log("Failed to open create map from file " + mapFile);
            e.printStackTrace();
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return columns;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    /**
     * Draws the tile map.
     * 
     * @param g            Graphics2D object to draw with
     * @param originX      the x coordinate of the top left corner of the map WRT
     *                     the top left corner of the screen
     * @param originY      the y coordinate of the top left corner of the map WRT
     *                     the top left corner of the screen
     * @param screenWidth
     * @param screenHeight
     */
    public void draw(Graphics2D g, int originX, int originY, int screenWidth, int screenHeight) {
        int firstRow = Math.max(-originY / Tile.HEIGHT, 0);
        int lastRow = firstRow + (int) Math.ceil((double) screenHeight / Tile.HEIGHT);
        int firstCol = Math.max(-originX / Tile.HEIGHT, 0);
        int lastCol = firstCol + (int) Math.ceil((double) screenWidth / Tile.WIDTH);
        for (int i = firstRow; i < tiles.size() && i <= lastRow; i++) {
            for (int j = firstCol; j < tiles.get(i).size() && j <= lastCol; j++) {
                tiles.get(i).get(j).image.update();
                g.drawImage(tiles.get(i).get(j).image.getImage(), j * Tile.WIDTH + originX, i * Tile.HEIGHT + originY, Tile.WIDTH,
                        Tile.HEIGHT, null);
            }
        }
    }

    // TODO: TESTING
    public Tile getTile(int r, int c) {
        return tiles.get(r).get(c);
    }

}
