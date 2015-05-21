package ray.shader;

import ray.Scene;
import ray.math.Color;
import ray.math.Point;
import ray.surface.Surface;

/**
 * This interface specifies what is necessary for an object to be a material.
 *
 * @author ags
 */
public interface Shader {
    // The material given to all surfaces unless another is specified.
    public static final Shader DEFAULT_MATERIAL = new Lambertian();
    // the amount of ambient light
    public static final double AMBIENT_LIGHT = 0.05;
    // the color of the ambient light
    public static final Color AMBIENT_LIGHT_COLOR = new Color(1, 1, 1);

    /**
     * Returns color of the given point on the given surface in the scene.
     *
     * @param intersectPt point on the surface where a ray intersected
     * @param surface surface for which the color is being calculated 
     * @param scene contains all the lights and other surfaces available
     */
    public Color shade (Point intersectPt, Surface surface, Scene scene);
}
