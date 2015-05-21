package ray;

import ray.math.Color;
import ray.math.Point;

/**
 * A simplistic point light which is infinitely small and emits constant power in all directions. 
 * 
 * This is a useful idealization of a small light emitter.
 *
 * @author ags
 */
public class Light {
    // These fields are read in from the input file.
    /** Where the light is located in space. */
    public final Point position = new Point();
    /** Color of the light. */
    public final Color color = new Color(1, 1, 1);

    // PARSER METHODS
    public void setPosition (Point position) {
        this.position.set(position);
    }

    public void setColor (Color intensity) {
        color.set(intensity);
    }
}
