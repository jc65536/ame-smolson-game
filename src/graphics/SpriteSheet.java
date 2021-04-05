package graphics;

import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

public class SpriteSheet {

    private BufferedImage sheet;
    private int spriteWidth;
    private int spriteHeight;
    private int rows, cols;

    public SpriteSheet(String file, int rows, int cols) throws IOException {
        sheet = ImageIO.read(new File(file));
        this.rows = rows;
        this.cols = cols;
        spriteHeight = sheet.getHeight() / rows;
        spriteWidth = sheet.getWidth() / cols;
    }

    public BufferedImage getImage(int row, int col) {
        return sheet.getSubimage(col * spriteWidth, row * spriteHeight, spriteWidth, spriteHeight);
    }

    /**
     * Gets an array of images from the spritesheet. Useful for creating animations.
     * 
     * @param coordinates a 2d array where each row is in the form of {row, col}
     * @return an array of BufferedImages
     * @throws InputMismatchException if the 2d array does not have exactly 2
     *                                columns
     */
    public BufferedImage[] getImages(int[][] coordinates) throws InputMismatchException {
        if (coordinates[0].length != 2)
            throw new InputMismatchException();
        BufferedImage[] r = new BufferedImage[coordinates.length];
        for (int i = 0; i < coordinates.length; i++) {
            r[i] = getImage(coordinates[i][0], coordinates[i][1]);
        }
        return r;
    }

    public BufferedImage[] getAllImages() {
        BufferedImage[] r = new BufferedImage[rows * cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                r[i * cols + j] = getImage(i, j);
            }
        }
        return r;
    }

}
