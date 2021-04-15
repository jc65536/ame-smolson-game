package util;

public class Direction {

    // Enumerated directions
    public static final Direction E = new Direction(0, "RIGHT");
    public static final Direction N = new Direction(Math.PI / 2, "UP");
    public static final Direction W = new Direction(Math.PI, "LEFT");
    public static final Direction S = new Direction(3 * Math.PI / 2, "DOWN");
    public static final Direction NE = new Direction(Math.PI / 4);
    public static final Direction NW = new Direction(3 * Math.PI / 4);
    public static final Direction SW = new Direction(5 * Math.PI / 4);
    public static final Direction SE = new Direction(7 * Math.PI / 4);
    public static final Direction NULL = new Direction();

    private double angle;
    private double x;
    private double y;
    private String name;

    /**
     * Initializes a new Direction
     * 
     * @param angle the angle in radians
     */
    public Direction(double angle) {
        this.angle = angle;
        x = Math.cos(angle);
        y = Math.sin(angle);
        this.name = String.format("Direction[a=%f, x=%f, y=%f]", angle, x, y);
    }

    /**
     * Initializes a new Direction. The new Direction represents a unit vector in
     * the direction of (x, y).
     * 
     * @param x x component
     * @param y y component
     */
    public Direction(double x, double y) {
        this(Math.atan2(y, x));
    }

    private Direction(double angle, String name) {
        this(angle);
        this.name = name;
    }

    private Direction() {
        this.name = "NULL";
    }

    public double getAngle() {
        return angle;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return -y; // negative because of screen coordinates
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Combines the directions of two Directions
     * 
     * @param dirs any amount of Directions
     * @return a new Direction representing a unit vector in the direction of the
     *         sum of all Direction vectors in {@code dirs}
     */
    public static Direction add(Direction... dirs) {
        double x = 0;
        double y = 0;
        for (Direction d : dirs) {
            x += d.x;
            y += d.y;
        }
        return new Direction(Math.atan2(y, x));
    }

    public static Direction[] enumDirs() {
        return new Direction[] { E, NE, N, NW, W, SW, S, SE };
    }

    public static Direction[] arrowDirs() {
        return new Direction[] { E, N, W, S };
    }

}
