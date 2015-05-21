package ray.surface;

import ray.math.Point;
import ray.math.Ray;
import ray.math.Vector;
import ray.shader.Shader;


/**
 * Abstract base class for all surfaces.
 *
 * @author ags
 */
public abstract class Surface {
    /** Shader to be used to shade this surface. */
    protected Shader shader = Shader.DEFAULT_MATERIAL;

    // PARSER METHODS
    public void setShader (Shader material) {
        shader = material;
    }

    public Shader getShader () {
        return shader;
    }


    /**
     * Calculates true if the given ray intersects this surface.
     */ 
    public boolean intersects (Ray ray) {
        return getIntersection(ray).length > 0;
    }

    /**
     * Calculates t values for which ray intersects this surface, 
     * returns an array with 0, 1, or 2 elements.
     *
     * @param ray the ray being projected
     * @return an array of t values for which an intersection of this surface with ray occurs
     */
    public abstract double[] getIntersection (Ray ray);

    /**
     * Returns the normal vector to this surface at point
     *
     * @param point the point for which we're calculating the normal vector
     * @return the normal vector to surface at point
     */
    public abstract Vector getNormal (Point point);
}
