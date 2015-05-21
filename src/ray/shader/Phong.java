package ray.shader;

import ray.Scene;
import ray.math.Color;
import ray.math.Point;
import ray.math.Vector;
import ray.surface.Surface;


/**
 * A Phong material uses the modified Blinn-Phong model which is energy preserving and reciprocal.
 * 
 * Note, all specular materials also contain some diffuse reflection, so extend that shader
 * to include it in the calculation.
 *
 * @author Eli Williams (erw19)
 */
public class Phong extends Lambertian {
    // These fields are read in from the input file.
    /** The color of the specular reflection. */
    protected final Color specularColor = new Color(1, 1, 1);
    /** The exponent controlling the sharpness of the specular reflection. */
    protected double exponent = 1.0;

    // PARSER METHODS
    public void setSpecularColor (Color specularColor) {
        this.specularColor.set(specularColor);
    }

    public void setExponent (double exponent) {
        this.exponent = exponent;
    }

    /**
     * @see Shader#shade()
     * 
     * Don't forget to call super.shade() to include that color component as well.
     */
    @Override
    public Color shade (Point intersectPt, Surface surface, Scene scene) {
        // TODO: calculate the intensity of the light along this ray
    	Color ambient = new Color(AMBIENT_LIGHT_COLOR.scale(AMBIENT_LIGHT));
    	Color specular = new Color(specularColor);
    	for(int i = 0; i < scene.getLights().size(); i++) {
    		Vector l = new Vector(scene.getLights().get(i).position.sub(intersectPt).normalize());
    		Vector n = new Vector(surface.getNormal(intersectPt));
    		Vector v = new Vector(scene.getCamera().viewPoint.sub(intersectPt).normalize());
    		Vector h = l.add(v);
    		Vector half = h.scale(1.0 / h.length());
    		double max = Math.max(0.0, Math.pow(half.dot(n), exponent));
    		Color intensity = new Color (scene.getLights().get(i).color);
    		specular.scale(intensity.scale(max));
    	}
        return specular.add(super.shade(intersectPt, surface, scene)).add(ambient);
    }
}