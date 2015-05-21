package ray.gui;

import java.io.File;

import javax.swing.JFileChooser;

import ray.RayTracer;

/**
 * Loads a data file to be rendered.
 *
 * @author rcd
 */
public class Reader extends Command {
    private static final JFileChooser ourChooser = 
        new JFileChooser(System.getProperties().getProperty("user.dir") + File.separator + "data");

    public Reader () {
        super("Open Scene");
    }

    @Override
    public void execute (RayTracer tracer) {
        int response = ourChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            tracer.read(ourChooser.getSelectedFile());
        }
    }
}
