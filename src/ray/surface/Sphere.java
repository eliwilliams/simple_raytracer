package ray.surface;

import ray.math.Point;
import ray.math.Ray;
import ray.math.Vector;

/**
 * Represents a sphere as a center and a radius.
 *
 * @author Eli Williams (erw19)
 */

public class Sphere extends Surface {
    // These fields are read in from the input file.
    /** The center of the sphere. */
    protected Point center = new Point();
    /** The radius of the sphere. */
    protected double radius = 1.0;

    // PARSER METHODS
    public void setCenter (Point center) {
        this.center.set(center);
    }

    public void setRadius (double radius) {
        this.radius = radius;
    }


    @Override
    public double[] getIntersection (Ray ray) {
        // TODO: return t values at which this ray intersects this surface
    	/** Formula: 
    	 *	
    	 *  (d.d)t^2 + ((2d).(e-c))t + (e-c).(e-c) - R^2 = 0
    	 *  then, hitpoints = e + dt
    	 *
    	 */
    	Vector diff = ray.getOrigin().sub(this.center);
    	Vector dir_copy = new Vector(ray.getDirection());
    	double a = ray.direction.dot(ray.getDirection());
    	double b = diff.dot(dir_copy.scale(2));
    	double c = diff.dot(diff) - Math.pow(radius, 2);
    	
    	double discriminant = Math.pow(b, 2) - (4.0 * a * c);
    	
    	if(discriminant < 0){
    		return new double[0];
    	}
    	else if(discriminant < 0.000001){
    		double[] one_return = {((-1.0 * b) / (2.0 * a))};
    		return one_return;
    	}
    	else{
    		double root_dis = Math.sqrt(discriminant);
    		double first = (-1.0 * b) + root_dis;
    		double second = (-1.0 * b) - root_dis;
    		double[] two_return = {(first/(2.0 * a)), (second/(2.0 * a))};
    		return two_return;
    	}
    }

    @Override
    public Vector getNormal (Point pt) {
        // TODO: return vector representing this surface's normal at this point
        return pt.sub(center).normalize();
    }
}
