package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * this class creates a night object and changes its opacity
 * it also creates a night object and changes its opacity
 * @author shimon ifrach and avi wolf
 */
public class Night {

    /* constants */
    private static final String NIGHT = "night";
    private static final float OFF = 0.0f;
    private static final float MIDNIGHT_OPACITY = 0.5f;
    private static final int DIVIDE_FACTOR = 2;


    /* methods */

    /**
     * Creates a night object.
     * @param windowDimensions the dimensions of the window
     * @param cycleLength the length of the day-night cycle
     * @return the night object
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Color.BLACK));
        night.setTag(NIGHT);
        night.setCoordinateSpace(danogl.components.CoordinateSpace.CAMERA_COORDINATES);
        new Transition<>(night, night.renderer()::setOpaqueness, OFF, MIDNIGHT_OPACITY, Transition.
                CUBIC_INTERPOLATOR_FLOAT, cycleLength / DIVIDE_FACTOR, Transition.TransitionType.
                TRANSITION_BACK_AND_FORTH, null);
        return night;
    }
}
