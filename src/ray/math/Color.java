package ray.math;

/**
 * This class represents a simple RGB color.
 *
 * There is also a Color class in the java.awt package, but we wanted simplicity and full access to
 * the code.
 *
 * @author ags
 */
public class Color extends Tuple {
    /**
     * Default constructor. Produces black.
     */
    public Color () {
        super(0, 0, 0);
    }

    /**
     * Copy constructor.
     *
     * @param newColor The color to copy.
     */
    public Color (Color newColor) {
        super(newColor.x, newColor.y, newColor.z);
    }

    /**
     * The explicit constructor.
     *
     * @param newR The new red value.
     * @param newG The new green value.
     * @param newB The new blue value.
     */
    public Color (double newR, double newG, double newB) {
        super(newR, newG, newB);
    }


    /**
     * Sets this color as the product of the pairwise product of this color and the other.
     */
    public Color scale (Color other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        return this;
    }

    /**
     * Add a scalar multiple of the other vector to this Vector
     */
    public Color scale (double factor) {
        x *= factor;
        y *= factor;
        z *= factor;
        return this;
    }

    /**
     * Sets this color to the pairwise sum of this color and ths.
     */
    public Color add (Color other) {
        x += other.x;
        y += other.y;
        z += other.z;
        return this;
    }

    /**
     * Add a scalar multiple of the other color to this Color
     */
    public Color scaleAdd (double scale, Color other) {
        x += scale * other.x;
        y += scale * other.y;
        z += scale * other.z;
        return this;
    }

    /**
     * Clamps the each component of this color to between [min,max]
     *
     * @param min the minimum value
     * @param max the maximum value
     */
    public Color clamp (double min, double max) {
        x = Math.max(Math.min(x, max), min);
        y = Math.max(Math.min(y, max), min);
        z = Math.max(Math.min(z, max), min);
        return this;
    }

    /**
     * This function returns an int which represents this color. The standard RGB style bit packing
     * is used and is compatible with java.awt.BufferedImage.TYPE_INT_RGB. (i.e., - the low 8 bits,
     * 0-7 are for the blue channel, the next 8 are for the green channel, and the next 8 are for
     * the red channel). The highest 8 bits are left untouched.
     *
     * @return An integer representing this color.
     */
    public int toInt () {
        clamp(0, 1);
        // Here we do the dumb thing and simply clamp then scale.
        // The "+ 0.5" is to achieve a "round to nearest" effect
        // since Java float to int casting simply truncates.
        int r = (int) (255.0 * x + 0.5);
        int g = (int) (255.0 * y + 0.5);
        int b = (int) (255.0 * z + 0.5);
        // Bit packing at its finest
        return (r << 16) | (g << 8) | (b << 0);
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString () {
        return "[" + x + "," + y + "," + z + "]";
    }
}
