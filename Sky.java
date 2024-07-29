package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * The class that creates the sky.
 */
public class Sky {

    /* constants */

    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");
    private static final String SKY = "sky";

    /* methods */

    /**
     * Creates a sky object.
     *
     * @param windowDimensions the dimensions of the window
     * @return the sky object
     */
    public static GameObject create(Vector2 windowDimensions) {
        GameObject sky = new GameObject(
                Vector2.ZERO,
                windowDimensions,
                new RectangleRenderable(BASIC_SKY_COLOR)
        );
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag(SKY);
        return sky;
    }
}
