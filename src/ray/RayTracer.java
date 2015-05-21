package ray;

import java.awt.Dimension;
import java.io.File;
import ray.gui.Image;
import ray.gui.Parser;
import ray.math.Color;
import ray.math.Ray;
import ray.surface.Surface;


public class RayTracer {
    public static final Color BACKGROUND_COLOR = new Color();

    /** The scene being rendered */
    private Scene scene = null;
    /** The image to write to */
    private Image image;
    /** Name of scene being parsed */
    private String name;
    /** Only need one parser for all files */
    private Parser parser;

    /**
     * Create a basic ray tracer.
     */
    public RayTracer () {
        image = new Image();
        parser = new Parser();
    }

    /**
     * Read a scene from an XML data file.
     */
    public void read (File file) {
        try {
            scene = (Scene)parser.parse(file, Scene.class);
            name = file.getName();
            reset();
        } catch (Exception e) {
            System.err.println("While reading input file '" + file + "':");
            System.err.println("    " + e);
            e.printStackTrace();
        }
    }

    /**
     * Write rendered scene to the given file.
     */
    public void write (String filename) {
        try {
            image.write(filename);
        } catch (Exception e) {
            System.err.println("While reading input file '" + filename + "':");
            System.err.println("    " + e);
            e.printStackTrace();
        }
    }

    /**
     * @return name of file being rendered.
     */
    public String getName () {
        return name;
    }

    /**
     * @return image containing rendered scene.
     */
    public Image getImage () {
        return image;
    }

    /**
     * @return size of image being rendered
     */
    public Dimension getSize () {
        return image.getSize();
    }

    /**
     * Render the image directly to a file, bypassing the GUI.
     */
    public void renderToFile (File file) {
        read(file);
        render(1);
        write("output/" + file.getName().split("\\.")[0] + ".jpg");
    }

    /**
     * Sets size of the image to render.
     */
    public void setSize (Dimension size) {
        image.setSize(size.width, size.height);
        reset();
    }

    /**
     * Reset the size of the viewport window.
     */
    public void resizeViewport (double x, double y, double width, double height) {
        if (scene == null) {
            System.out.println("No scene to zoom in on.");
            return;
        }
        scene.getCamera().resizeViewPlane(x, y, width, height);
        reset();
        render(1);
    }

    /**
     * Clears image for rendering.
     */
    public void reset () {
        image = new Image(getSize());
    }

    /**
     * Render the entire scene in pixels of size stride.
     */
    public void render (int stride) {
        if (scene == null) {
            System.out.println("No scene to render.");
            return;
        }
        scene.initialize();
        Dimension size = getSize();
        int halfStride = Math.max(1, stride / 2);
        for (int y = size.height - halfStride - 1; y >= halfStride; y -= stride) {
            for (int x = halfStride; x < size.width - halfStride; x += stride) {
                Color c = renderPixel(x, y).clamp(0, 1);
                for (int py = -halfStride; py < halfStride; py++) {
                    for (int px = -halfStride; px < halfStride; px++) {
                        image.setPixelColor(c, px + x, py + y);
                    }
                }
            }
        }
    }

    /**
     * Render the given pixel.
     */
    public Color renderPixel (int x, int y) {
        Ray ray = scene.getCamera().getRay((double)x / getSize().width,
                                           (double)y / getSize().height);
        return trace(ray);
    }

    /**
     * Trace the given ray's path through the scene.
     */
    private Color trace (Ray ray) {
        // TODO: complete this method, you may want to add parameters to it if you implement recursion
        Surface closest = null;
        double tmin = Double.MAX_VALUE;
        for(int i = 0; i < scene.getSurfaces().size(); i++) {
        	double[] intersections = scene.getSurfaces().get(i).getIntersection(ray);
        	if(intersections != null && intersections.length != 0) {
        		for(int j = 0; j < intersections.length; j++){
        			double check = ray.getOrigin().distance(ray.evaluate(intersections[j]));
        			if(tmin > check){
        				tmin = check;
        				closest = scene.getSurfaces().get(i);
        			}
        		}
        	}
        }
        if (closest == null) {
            return BACKGROUND_COLOR;
        } else {
            return closest.getShader().shade(ray.evaluate(tmin), closest, scene);
        }
    }
}
