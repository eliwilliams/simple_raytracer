package ray.math;

/**
 * The Vector class represents a 3D vector of doubles.
 *
 * @author ags
 */
public class Vector extends Tuple {
    /**
     * Default constructor. Uses explicit constructor to create a zero vector.
     */
    public Vector () {
        super(0, 0, 0);
    }

    /**
     * Copy constructor.
     */
    public Vector (Tuple newTuple) {
        super(newTuple.x, newTuple.y, newTuple.z);
    }

    /**
     * The explicit constructor.
     */
    public Vector (double newX, double newY, double newZ) {
        super(newX, newY, newZ);
    }

    /**
     * Sets this vector to the cross product of a and b
     */
    public Vector cross (Vector a, Vector b) {
        x = a.y * b.z - a.z * b.y;
        y = a.z * b.x - a.x * b.z;
        z = a.x * b.y - a.y * b.x;
        return this;
    }

    /**
     * Returns a vector that is the cross product of this vector and the given vector
     */
    public Vector cross (Vector other) {
        return new Vector(y * other.z - z * other.y,
                          z * other.x - x * other.z,
                          x * other.y - y * other.x);
    }

    /**
     * Returns the dot product of this Vector and the other.
     */
    public double dot (Vector other) {
        return x * other.x + y * other.y + z * other.z;
    }

    /**
     * Returns the length squared of this vector.
     */
    public double lengthSquared () {
        return x * x + y * y + z * z;
    }

    /**
     * Returns length of this vector.
     */
    public double length () {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalize this Vector so that its length is 1.0.
     *
     * If the length of the Vector is 0, no action is taken.
     */
    public Vector normalize () {
        double dist = length();
        if (dist != 0) {
            x /= dist;
            y /= dist;
            z /= dist;
        }
        return this;
    }

    /**
     * Negate this vector.
     */
    public Vector negate () {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    /**
     * Add the values of v1 and v2 and store the sum in this Vector.
     */
    public Vector add (Vector v1, Vector v2) {
        x = v1.x + v2.x;
        y = v1.y + v2.y;
        z = v1.z + v2.z;
        return this;
    }

    /**
     * Return a new vector that is the sum of this Vector to the other
     */
    public Vector add (Vector other) {
        return new Vector().add(this, other);
    }

    /**
     * Subtract the values of v1 and v2 and store the sum in this Vector.
     */
    public Vector sub (Vector v1, Vector v2) {
        x = v1.x - v2.x;
        y = v1.y - v2.y;
        z = v1.z - v2.z;
        return this;
    }

    /**
     * Return a new vector that is the difference between this Vector to the other
     */
    public Vector sub (Vector other) {
        return new Vector().sub(this, other);
    }

    /**
     * Add a scalar multiple of the other vector to this Vector
     */
    public Vector scaleAdd (double scale, Vector vector) {
        x += scale * vector.x;
        y += scale * vector.y;
        z += scale * vector.z;
        return this;
    }

    /**
     * Add a scalar multiple of the other vector to this Vector
     */
    public Vector scale (double factor) {
        x *= factor;
        y *= factor;
        z *= factor;
        return this;
    }
}
