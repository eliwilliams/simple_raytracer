package ray.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ray.RayTracer;

/**
 * The primary GUI for the Ray Tracer
 *
 * @author rcd
 */
@SuppressWarnings("serial")
public class RayFrame extends JFrame {
    private RayTracer tracer;
    private Canvas canvas;

    /**
     * Create frame with the given title and whose canvas is the given size.
     */
    public RayFrame (String title, Dimension size) {
        setTitle(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        tracer = new RayTracer();
        // create GUI components
        canvas = new Canvas(this, tracer);
        canvas.setSize(size);
        // add commands to test here
        JPanel commands = makeCommands(new Reader(), new Render(canvas), new Writer());
        // add our container to Frame and show it
        getContentPane().add(commands, BorderLayout.NORTH);
        getContentPane().add(canvas, BorderLayout.CENTER);
        pack();
    }

    /**
     * Bypass the GUI to render an image directly to a file.
     */
    public void renderToFile (File file) {
        tracer.renderToFile(file);
    }

    // create a panel of buttons corresponding to the given commands
    private JPanel makeCommands (Command... cmds) {
        JPanel result = new JPanel();
        for (final Command c : cmds) {
            JButton button = new JButton(c.getName());
            // perform command when button is clicked
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed (ActionEvent e) {
                    c.execute(tracer);
                    canvas.refresh();
                }
            });
            result.add(button);
        }
        return result;
    }
}
