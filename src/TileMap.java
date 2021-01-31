import java.io.*;
import java.util.*;
import java.awt.image.*;
import javax.imageio.*;

public class TileMap {

    public static final int TILE_SIZE = 32;

    static Map<Character, BufferedImage> charDict;

    static {
        try {
            charDict = Map.of('w', ImageIO.read(new File("res/tiles/gra.png")),
                              '~', ImageIO.read(new File("res/tiles/wat.png")),
                              '.', ImageIO.read(new File("res/tiles/dir.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<List<Character>> map;
    int rows, cols;

    public TileMap(String file) throws IOException {
        load(file);
    }

    public void load(String file) throws IOException {
        map = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;
        while ((line = in.readLine()) != null) {
            List<Character> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                row.add(c);
            }
            map.add(row);
        }
        rows = map.size();
        cols = map.get(0).size();
        in.close();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public BufferedImage getImage(int row, int col) {
        return charDict.get(map.get(row).get(col));
    }
}
