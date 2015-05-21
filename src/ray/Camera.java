package ray;

import ray.math.Point;
import ray.math.Ray;
import ray.math.Vector;

/**
 * Represents a simple camera.
 * 
 * The camera view is determined by its position and a view window of size viewWidth by viewHeight. 
 * The view window corresponds to a rectangle on a plane perpendicular to viewDir but at distance 
 * projDistance from viewPoint in the direction of viewDir. The window's center is positioned along
 * the viewDir at projDistance from the viewPoint.
 *
 * @author ags
 */
public class Camera {
    // These fields are read in from the input file.
    /** Position of the camera */
    public final Point viewPoint = new Point();
    /** Direction camera is facing */
    public final Vector viewDir = new Vector(0, 0, -1);
    /** Up direction of the camera */
    public final Vector viewUp = new Vector(0, 1, 0);
    /** Normal of the projection plane */
    public final Vector projNormal = new Vector(0, 0, 1);
    /** Width of the projection plane */
    public double viewWidth = 1.0;
    /** Height of the projection plane */
    public double viewHeight = 1.0;
    /** Distance to the projection plane */
    public double projDistance = 1.0;
    /*
     * Derived values that are computed before ray generation. basisU, basisV, and basisW form an
     * orthonormal basis. basisW is parallel to projNormal.
     */
    private Vector basisU;
    private Vector basisV;
    private Vector basisW;
    private Vector centerDir;

    // PARSER METHODS
    public void setPosition (Point viewPoint) {
        this.viewPoint.set(viewPoint);
    }

    public void setViewDir (Vector viewDir) {
        this.viewDir.set(viewDir);
        this.viewDir.normalize();
    }

    public void setViewUp (Vector viewUp) {
        this.viewUp.set(viewUp);
        this.viewUp.normalize();
    }

    public void setProjNormal (Vector projNormal) {
        this.projNormal.set(projNormal);
        this.projNormal.normalize();
    }

    public void setViewWidth (double viewWidth) {
        this.viewWidth = viewWidth;
    }

    public void setViewHeight (double viewHeight) {
        this.viewHeight = viewHeight;
    }

    public void setprojDistance (double projDistance) {
        this.projDistance = projDistance;
    }

    // PUBLIC METHODS
    /**
     * Initialize the derived view variables to prepare for using the camera.
     */
    public void initialize () {
        basisW = new Vector(viewDir).negate().normalize();
        basisU = new Vector(viewUp).cross(basisW).normalize();
        basisV = new Vector(basisW).cross(basisU).normalize();
        centerDir = new Vector(viewDir).normalize().scale(projDistance);
    }

    /**
     * Returns ray that points out into the scene for the given (u,v) coordinate.
     * 
     * This coordinate corresponds to a point on the viewing window, where (0,0) is the lower left
     * corner and (1,1) is the upper right.
     * 
     * @param u The horizontal coordinate (0 is left, 1 is right)
     * @param v The vertical coordinate (0 is bottom, 1 is top)
     */
    public Ray getRay (double u, double v) {
        return new Ray(viewPoint, offsetFromCenter(u, v));
    }

    public void resizeViewPlane (double u, double v, double width, double height) {
        // BUGBUG: this still needs to be fixed
        centerDir = offsetFromCenter(u, v).scale(projDistance);
        viewWidth *= width;
        viewHeight *= height;
    }

    private Vector offsetFromCenter (double u, double v) {
        // normalize (u,v) coordinates to [0..1] and center
        u = u * 2 - 1;
        v = v * 2 - 1;
        return new Vector(centerDir).scaleAdd(u * viewWidth / 2, basisU)
                                    .scaleAdd(v * viewHeight / 2, basisV)
                                    .normalize();
    }
}
