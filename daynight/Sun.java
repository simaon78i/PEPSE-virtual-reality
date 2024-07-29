package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.world.Terrain;

import java.awt.*;

/**
 * this class creates a sun halo and moves it around the sun
 * it also creates a sun and moves it around the screen
 * it also creates a night object and changes its opacity
 *
 * @author shimon ifrach and avi wolf
 */
public class Sun {
    private static final String SUN = "sun";
    private static final float WINDOW_MULTIPLY_FACTOR = 0.5f;
    private static final float GROUND_MULTIPLY_FACTOR = 0.2f;
    private static final float INITIAL_VALUE_OF_ANGLE = 0f;
    private static final float END_VALUE_OF_ANGLE = 360f;
    private static final int DIVIDE_FACTOR = 2;
    private static final int SUNS_DIMENSIONS = 100;

    /**
     * Creates a sun object.
     *
     * @param windowDimensions the dimensions of the window
     * @param cycleLength      the length of the day-night cycle
     * @return the sun object
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        GameObject sun = new GameObject(new Vector2(windowDimensions.x() * WINDOW_MULTIPLY_FACTOR,
        windowDimensions.y() * Terrain.START_POINT_FACTOR * GROUND_MULTIPLY_FACTOR), Vector2.ONES.
                mult(SUNS_DIMENSIONS), new OvalRenderable(Color.YELLOW));
        sun.setTag(SUN);
        sun.setCoordinateSpace(danogl.components.CoordinateSpace.CAMERA_COORDINATES);
        Vector2 initialSunCenter = sun.getCenter();
        Vector2 cycleCenter = new Vector2(windowDimensions.x() * WINDOW_MULTIPLY_FACTOR, windowDimensions
                .y() * Terrain.START_POINT_FACTOR);
        new Transition<>(
                sun,
                (Float angle) -> sun.setCenter(initialSunCenter.subtract(cycleCenter).
                        rotated(angle).add(cycleCenter)),
                INITIAL_VALUE_OF_ANGLE,
                END_VALUE_OF_ANGLE,
                Transition.
                        LINEAR_INTERPOLATOR_FLOAT,
                cycleLength / DIVIDE_FACTOR,
                Transition.TransitionType.TRANSITION_LOOP,
                null);
        return sun;
    }
}
