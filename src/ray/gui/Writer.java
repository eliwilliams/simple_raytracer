package ray.gui;

import javax.swing.JFileChooser;

import ray.RayTracer;

/**
 * Saves rendered image to a file.
 *
 * @author rcd
 */
public class Writer extends Command {
    private static final JFileChooser ourChooser = 
        new JFileChooser(System.getProperties().getProperty("user.dir"));

    public Writer () {
        super("Save Image");
    }

    @Override
    public void execute (RayTracer tracer) {
        int response = ourChooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            tracer.write(ourChooser.getSelectedFile().getPath());
        }
    }
}
