package ray.gui;

import ray.RayTracer;

/**
 * Renders image to the screen.
 *
 * Run as a separate thread so that it is updated while the algorithm is run.
 *
 * @author rcd
 */
public class Render extends Command {
    private static final int DELAY = 200;

    private Canvas canvas;
    private RayTracer tracer;
    private boolean isDone;
    private Thread painter;
    private Thread renderer;

    public Render (Canvas viewer) {
        super("Render");
        canvas = viewer;
        isDone = true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void execute (RayTracer target) {
        if (isDone) {
            isDone = false;
            tracer = target;
            renderer = new Thread(new Renderer());
            painter = new Thread(new Painter());
            renderer.start();
            painter.start();
        } else {
            renderer.stop();
            painter.stop();
            isDone = true;
        }
    }

    // repaint the canvas every so often to show progress
    class Painter implements Runnable {
        @Override
        public void run () {
            try {
                while (!isDone) {
                    Thread.sleep(DELAY);
                    canvas.repaint();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // render the image repeatedly as fast as possible
    class Renderer implements Runnable {
        static final int MAX_PIXEL_SIZE = 64;
        static final int STRIDE_SIZE = 4;
        static final int NUM_PASSES = MAX_PIXEL_SIZE / (STRIDE_SIZE * STRIDE_SIZE);        
        @Override
        public void run () {
            tracer.reset();
            System.out.println("Render started:");
            long start = System.currentTimeMillis();
            for (int k = MAX_PIXEL_SIZE, pass = 1; k > 0; k /= STRIDE_SIZE, pass++) {
                tracer.render(k);
                System.out.println("  Finished pass " + pass + " of " + NUM_PASSES);
            }
            long end = System.currentTimeMillis();
            System.out.println("Completed in " + ((end - start) / 1000) + " seconds.");
            isDone = true;
        }
    }
}
