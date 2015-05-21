import java.awt.Dimension;
import java.io.File;

import ray.gui.RayFrame;

/**
 * A basic Ray Tracer.
 *
 * This file sets up the GUI, nothing really to see here.
 *
 * @author rcd
 */
public class Main {
    public static final Dimension SIZE = new Dimension(600, 600);
    public static final String TITLE = "Ray Tracer";

    public static void main (String[] args) {
        RayFrame viewer = new RayFrame(TITLE, SIZE);
        if (args.length == 0) {
            viewer.setVisible(true);
        } else {
            viewer.renderToFile(new File(args[0]));
        }
    }
}
