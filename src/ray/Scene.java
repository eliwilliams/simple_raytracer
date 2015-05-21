package ray;

import java.util.ArrayList;
import java.util.List;

import ray.shader.Shader;
import ray.surface.Surface;

/**
 * The scene is just a collection of objects that compose a scene.
 *
 * The camera, the list of lights, and the list of surfaces.
 *
 * @author ags
 */
public class Scene {
    /** The camera for this scene. */
    private Camera camera;
    /** The list of lights for the scene. */
    private List<Light> lights;
    /** The list of surfaces for the scene. */
    private List<Surface> surfaces;
    /** The list of materials in the scene . */
    private List<Shader> shaders;

    /**
     * Create an empty scene.
     */
    public Scene () {
        lights = new ArrayList<>();
        surfaces = new ArrayList<>();
        shaders = new ArrayList<>();
    }

    // PARSER METHODS
    /**
     * Set the scene's camera.
     */
    public void setCamera (Camera camera) {
        this.camera = camera;
    }

    /**
     * Add the light to this scene.
     */
    public void addLight (Light toAdd) {
        lights.add(toAdd);
    }

    /**
     * Get the scene's lights.
     */
    public List<Light> getLights () {
        return lights;
    }

    /**
     * Add the surface to this scene.
     */
    public void addSurface (Surface toAdd) {
        surfaces.add(toAdd);
    }

    /**
     * Add the shader to this scene.
     */
    public void addShader (Shader toAdd) {
        shaders.add(toAdd);
    }

    /**
     * Get the scene's camera.
     */
    public Camera getCamera () {
        return camera;
    }

    /**
     * Get the scene's surfaces.
     */
    public List<Surface> getSurfaces () {
        return surfaces;
    }

    /**
     * Set up scene for rendering.
     */
    public void initialize () {
        camera.initialize();
    }
}
