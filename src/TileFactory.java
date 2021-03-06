import java.io.*;
import java.util.*;

public class TileFactory {
    static SpriteSheet tileSheet;
    static Map<Integer, int[]> tileDict;

    static {
        try {
            tileSheet = new SpriteSheet("res/tiles.png", 4, 4);
            tileDict = Map.of((int) 'w', new int[] {0, 0},
                              (int) '.', new int[] {0, 1},
                              (int) '~', new int[] {0, 2});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Tile newTile(int id) {
        Tile t = new Tile();
        t.setId(id);
        int[] coords = tileDict.get(id);
        t.setImage(tileSheet.getImage(coords[0], coords[1]));
        if (id == '~') {            // TESTING
            t.setPassable(false);
        } else {
            t.setPassable(true);
        }
        return t;
    }
}
