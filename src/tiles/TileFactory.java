package tiles;

import java.util.*;
import java.awt.image.*;

import graphics.*;
import util.*;

public class TileFactory {
    private static SpriteSheet tileImages;
    private static Map<Integer, BufferedImage> imageMap = new HashMap<>();

    static {
        try {
            tileImages = new SpriteSheet("res/tiles.png", 3, 4);
            imageMap.put((int) '.', tileImages.getImage(0, 0));
            imageMap.put((int) 'w', tileImages.getImage(0, 1));
            imageMap.put((int) '~', tileImages.getImage(0, 2));
            imageMap.put((int) 'P', tileImages.getImage(1, 1));
        } catch (Exception e) {
            Logger.log("Couldn't load tile images");
            e.printStackTrace();
        }
    }

    public static Tile newTile(int tileCode) {
        Tile t = new Tile();
        t.id = tileCode;
        t.image = imageMap.get(tileCode);
        if (tileCode == '~') {
            t.passable = false;
        }
        return t;
    }

}
