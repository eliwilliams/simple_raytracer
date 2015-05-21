package ray.math;

/**
 * Base class for 3d tuples (i.e., points, vectors, and colors).
 *
 * @author arbree
 */
public class Tuple {
    /** The x coordinate of the Tuple3. */
    public double x;
    /** The y coordinate of the Tuple3. */
    public double y;
    /** The z coordinate of the Tuple3. */
    public double z;

    /**
     * Default constructor.
     */
    public Tuple () {
        this(0, 0, 0);
    }

    /**
     * Copy constructor.
     */
    public Tuple (Tuple newTuple) {
        this(newTuple.x, newTuple.y, newTuple.z);
    }

    /**
     * Explicit constructor.
     */
    public Tuple (double newX, double newY, double newZ) {
        x = newX;
        y = newY;
        z = newZ;
    }

    /**
     * Sets this tuple to have the contents of another tuple.
     */
    public void set (Tuple inTuple) {
        x = inTuple.x;
        y = inTuple.y;
        z = inTuple.z;
    }

    /**
     * Set the value of this Tuple3 to the three input values
     */
    public void set (double inX, double inY, double inZ) {
        x = inX;
        y = inY;
        z = inZ;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString () {
        return "[" + x + "," + y + "," + z + "]";
    }
}
