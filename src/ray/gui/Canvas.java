package ray.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ray.RayTracer;

/**
 * Represents the traced image on the screen.
 *
 * @author rcd
 */
@SuppressWarnings("serial")
public class Canvas extends JPanel {
    private JFrame container;
    private RayTracer tracer;
    private Point zoomStart;
    private Point zoomEnd;

    /**
     * Create a canvas in the given frame for the given ray tracer.
     */
    public Canvas (JFrame frame, RayTracer rayTracer) {
        setBorder(BorderFactory.createLoweredBevelBorder());
        container = frame;
        tracer = rayTracer;
        refresh();

        Zoomer z = new Zoomer();
        addMouseListener(z);
        addMouseMotionListener(z);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized (ComponentEvent e) {
                tracer.setSize(getSize());
            }
        });
    }

    /**
     * Redraw the image if necessary.
     */
    public void refresh () {
        container.setTitle(tracer.getName());
        if (tracer.getSize().width != getSize().width ||
                tracer.getSize().height != getSize().height) {
            setSize(tracer.getSize());
            container.pack();
        }
        repaint();
    }

    /**
     * Redraw the image.
     */
    @Override
    public void paintComponent (Graphics pen) {
        super.paintComponent(pen);
        tracer.getImage().paint(pen);
        if (zoomStart != null) {
            int startX = Math.min(zoomStart.x, zoomEnd.x);
            int startY = Math.min(zoomStart.y, zoomEnd.y);
            int endX = Math.max(zoomStart.x, zoomEnd.x);
            int endY = Math.max(zoomStart.y, zoomEnd.y);
            pen.setColor(Color.WHITE);
            pen.drawRect(startX, startY, endX - startX, endY - startY);
        }
    }

    @Override
    public void setSize (Dimension size) {
        setPreferredSize(size);
        setMinimumSize(size);
        super.setSize(size);
    }


    private class Zoomer extends MouseAdapter implements MouseMotionListener {
        @Override
        public void mousePressed (MouseEvent e) {
            zoomStart = new Point(e.getX(), e.getY());
            zoomEnd = new Point(e.getX(), e.getY());
        }

        @Override
        public void mouseDragged (MouseEvent e) {
            zoomEnd.setLocation(zoomStart);
            int dx = e.getX() - zoomStart.x;
            int dy = e.getY() - zoomStart.y;
            // constrain to be a square so we do not need to worry about resizing the window
            if (Math.abs(dx) > Math.abs(dy)) {
                zoomEnd.translate(dx, dx);
            } else {
                zoomEnd.translate(dy, dy);
            }
            Canvas.this.repaint();
        }

        @Override
        public void mouseReleased (MouseEvent e) {
            mouseDragged(e);
            if (zoomStart.x != zoomEnd.x && zoomStart.y != zoomEnd.y) {
                tracer.resizeViewport((double)zoomStart.x / getSize().width,
                                      (double)(getSize().height - zoomStart.y) / getSize().height,
                                      (double)Math.abs(zoomEnd.x - zoomStart.x) / getSize().width,
                                      (double)Math.abs(zoomEnd.y - zoomStart.y) / getSize().height);
            }
            zoomStart = zoomEnd = null;
            Canvas.this.repaint();
        }

        @Override
        public void mouseClicked (MouseEvent e) {
            // take into account that the y-axis is reversed in Swing
            System.out.println(tracer.renderPixel(e.getX(), getSize().height - e.getY()));
        }
    }
}
