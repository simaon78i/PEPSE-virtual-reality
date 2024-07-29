package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;


/**
 * this class creates a sun halo
 * @author shimon ifrach and avi wolf
 */
public class SunHalo {

    private static final String SUN_HALO = "sun halo";
    private static final int SUN_HALO_DIMENSIONS = 200;
    private static final int R = 255;
    private static final int G = 255;
    private static final int B = 0;
    private static final int A = 30;

    /**
     * Creates a sun halo object.
     *
     * @param sun the sun object
     * @return the sun halo object
     */
    public static GameObject create(GameObject sun){
        GameObject sunHalo = new GameObject(
                Vector2.ZERO,
                Vector2.ONES.mult(SUN_HALO_DIMENSIONS),
                new OvalRenderable(new Color(R, G, B, A)));
        sunHalo.setCenter(sun.getCenter());
        sunHalo.setTag(SUN_HALO);
        sunHalo.addComponent((float deltaTime) -> sunHalo.setCenter(sun.getCenter()));
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        return sunHalo;
    }
}
