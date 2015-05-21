package ray.surface;

import ray.math.Point;
import ray.math.Ray;
import ray.math.Vector;

/**
 *
 * @author Eli Williams (erw19)
 */
public class Triangle extends Surface {
    // These fields are read in from the input file.
    /* These points are the vertices of the triangle */
    protected final Point a = new Point();
    protected final Point b = new Point();
    protected final Point c = new Point();

    // PARSER METHODS
    public void setA (Point pt) {
        a.set(pt);
    }

    public void setB (Point pt) {
        b.set(pt);
    }

    public void setC (Point pt) {
        c.set(pt);
    }


    /**
     * @see Surface#getIntersection()
     */
    @Override
    public double[] getIntersection (Ray ray) {
        // TODO: return t values at which this ray intersects this surface
    	Vector u = c.sub(a);
    	Vector v = b.sub(a);
    	Vector g = ray.getOrigin().sub(a);
    	double y = -1.0 * ((u.cross(v).dot(g)));
    	double z = (u.cross(v).dot(ray.getDirection()));
    	Vector ray_dir = new Vector(ray.getDirection());
    	double i = y / z;
    	Point plane_intersect = ray.getOrigin().add(ray_dir.scale(i));
    	Vector w = plane_intersect.sub(a);
    	double uu = u.dot(u);
    	double uv = u.dot(v);
    	double vv = v.dot(v);
    	double d = uv * uv - uu * vv;
    	double wu = w.dot(u);
    	double wv = w.dot(v);
    	
    	double beta = (uv * wv - vv * wu) / d;
    	double gamma = (uv * wu - uu * wv) / d;
    	
    	if(i < 0.0){
    		return new double[0];
    	}
    	else if(beta < 0.0 || beta > 1.0){
    		return new double[0];
    	}
    	else if(gamma < 0.0 || (beta + gamma) > 1.0){
    		return new double[0];
    	}
    	else{
    		double[] ret = {i};
    		return ret;
    	}
    }

    /**
     * @see Surface#getNormal()
     */
    @Override
    public Vector getNormal (Point point) {
        // TODO: return vector representing this surface's normal at this point
    	// ignore @param point and calculate normal using triangle's vertices
    	Vector u = c.sub(a);
    	Vector v = b.sub(a);
        return u.cross(v).normalize();
    }
}
