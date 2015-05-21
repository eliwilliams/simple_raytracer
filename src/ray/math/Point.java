package ray.math;


/**
 * Class for 3d points.
 *
 * @author arbree
 */
public class Point extends Tuple {
    /**
     * Default constructor.
     */
    public Point () {
        super(0, 0, 0);
    }

    /**
     * Copy constructor.
     */
    public Point (Point newTuple) {
        super(newTuple.x, newTuple.y, newTuple.z);
    }

    /**
     * Explicit constructor.
     */
    public Point (double newX, double newY, double newZ) {
        super(newX, newY, newZ);
    }

    /**
     * Returns the squared distance from this Point to other
     */
    public double distanceSquared (Point other) {
        double dx = (x - other.x);
        double dy = (y - other.y);
        double dz = (z - other.z);
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Returns the distance from this Point to other
     */
    public double distance (Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * Return the vector between this Point and the other
     */
    public Vector sub (Point other) {
        return new Vector(x - other.x, y - other.y, z - other.z);
    }

    /**
     * Returns a new point that is the result of this point translated by the given vector
     */
    public Point add (Vector vector) {
        return new Point().add(this, vector);
    }

    /**
     * Add a Vector to a Point and store the result in this Point
     */
    public Point add (Point point, Vector vector) {
        x = vector.x + point.x;
        y = vector.y + point.y;
        z = vector.z + point.z;
        return this;
    }

    /**
     * Returns a new point that is the result of this point translated by the given vector
     */
    public Point sub (Vector vector) {
        return new Point().sub(this, vector);
    }

    /**
     * Subtract a Vector from a Point and store the result in this Point
     */
    public Point sub (Point point, Vector vector) {
        x = point.x - vector.x;
        y = point.y - vector.y;
        z = point.z - vector.z;
        return this;
    }

    /**
     * Move the point by the given vector scaled by the given factor
     */
    public Point scaleAdd (double scale, Vector vector) {
        x += scale * vector.x;
        y += scale * vector.y;
        z += scale * vector.z;
        return this;
    }
}
