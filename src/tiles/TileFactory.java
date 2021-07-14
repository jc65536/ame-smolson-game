package tiles;

import java.util.*;
import java.awt.image.*;
// test comment
import graphics.*;
import util.*;

public class TileFactory {
    private static SpriteSheet tileImages;
    private static Map<Integer, int[][]> imageMap = new HashMap<>();

    static {
        try {
            tileImages = new SpriteSheet("res/tiles.png", 6, 3);
            imageMap.put((int) '.', new int[][] {{0, 0}, {0, 1}, {0, 2}});
            imageMap.put((int) '~', new int[][] {{1, 0}, {1, 1}, {1, 2}});
            imageMap.put((int) 'w', new int[][] {{2, 0}, {2, 1}, {2, 2}});
            imageMap.put((int) 'P', new int[][] {{3, 0}, {3, 1}, {3, 2}});
        } catch (Exception e) {
            Logger.log("Couldn't load tile images");
            e.printStackTrace();
        }
    }

    public static Tile newTile(int tileCode) {
        Tile t = new Tile();
        t.id = tileCode;
        t.image = new Animation(tileImages.getImages(imageMap.get(tileCode)), 30);
        if (tileCode == '~') {
            t.passable = false;
        }
        return t;
    }

}
