package ray.shader;

import ray.Scene;
import ray.math.Color;
import ray.math.Point;
import ray.math.Ray;
import ray.math.Vector;
import ray.surface.Surface;


/**
 * A Lambertian material scatters light equally in all directions.
 *
 * @author Eli Williams (erw19)
 */

public class Lambertian implements Shader {
    // These fields are read in from the input file.
    /** The color of the surface. */
    protected final Color diffuseColor = new Color(1, 1, 1);

    // PARSER METHODS
    public void setDiffuseColor (Color inDiffuseColor) {
        diffuseColor.set(inDiffuseColor);
    }


    @Override
	public Color shade (Point intersectPt, Surface surface, Scene scene) {
		// TODO: calculate the intensity of the light along this ray
        Color currentColor = new Color(0, 0, 0);
		for(int i = 0; i < scene.getLights().size(); i++){
            boolean blocked = false;
            Color diffuse = new Color(diffuseColor);
			Vector l = scene.getLights().get(i).position.sub(intersectPt);
			Color intensity = new Color (scene.getLights().get(i).color);
			Vector n = surface.getNormal(intersectPt);
			double max = Math.max(0.0, (l.normalize()).dot(n));
			Ray r = new Ray(scene.getLights().get(i).position, l);
			double check = r.getOrigin().distance(intersectPt);
			for(Surface s : scene.getSurfaces()){
				if(!s.equals(surface) && s.intersects(r)){
					double[] intersections = s.getIntersection(r);
					if(intersections != null && intersections.length != 0) {
						for(int j = 0; j < intersections.length; j++){
							double dist = r.getOrigin().distance(r.evaluate(intersections[j]));
							if(dist <= check){
                                blocked = true;
							}
						}
					}
				}
			}
            if(!blocked) {
                Color temp = diffuse.scale(intensity.scale(max));
                currentColor.add(temp);
            }
		}
		return currentColor;
	}
}