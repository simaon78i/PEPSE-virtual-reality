package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Avatar;
import java.awt.*;

/**
 * This class creates a leaf.
 * The leaf is a rectangle with a fixed height and width.
 * The leaf is colored with a basic leaf color.
 * The leaf has a wind effect and a jump effect.
 * The leaf is created with a specific time.
 * The leaf is created with a specific avatar.
 * @author shimon ifrach and avi wolf
 */
public class Leaf {
    /* constants */
    private static final Color BASE_LEAF_COLOR = new Color(50, 200, 30);
    private static final String LEAF = "leaf";
    private static final Vector2 LEAF_DIMENSIONS = new Vector2(20, 20);
    private static final float MAX_VALUE = 1.001f;
    private static final float MIN_VALUE = 0.999f;
    private static final float TRANSITION_TIME = 0.9f;
    private static final float START_ANGLE = 0f;
    private static final float END_ANGLE = 90f;
    private static final float TRANSITION_TIME_OF_THE_ANGLE_MOVEMENT = 1f;
    private static final int WAIT_TIME = 0;

    /**
     * Creates a leaf. The leaf is a rectangle with a fixed height and width.
     * The leaf is colored with a basic leaf color.
     * @param position the position of the leaf
     * @param time the time of the leaf
     * @param avatar the avatar
     * @return the leaf
     */
    public static GameObject create(Vector2 position, float time, Avatar avatar) {
        GameObject leaf = new GameObject(position, LEAF_DIMENSIONS, new RectangleRenderable(ColorSupplier.
                approximateColor(BASE_LEAF_COLOR)));
        leaf.setTag(LEAF);
        applyWindEffect(time, leaf);
        avatar.addJumpObserver(() -> applyJumpEffect(leaf));
        return leaf;
    }

    private static void applyWindEffect(float time, GameObject leaf) {
        Runnable runnable = () -> new Transition<>(leaf, (Float factor) -> leaf.setDimensions(leaf.
                getDimensions().mult(factor)), MAX_VALUE, MIN_VALUE, Transition.CUBIC_INTERPOLATOR_FLOAT,
                TRANSITION_TIME, Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
        new ScheduledTask(leaf, time, false, runnable);
    }

    private static void applyJumpEffect(GameObject leaf) {
        Runnable runnable = () -> new Transition<>(leaf, (Float angle) -> leaf.renderer().
                setRenderableAngle(angle), START_ANGLE, END_ANGLE, Transition.LINEAR_INTERPOLATOR_FLOAT,
                TRANSITION_TIME_OF_THE_ANGLE_MOVEMENT, Transition.TransitionType.TRANSITION_ONCE,
                null);
        new ScheduledTask(leaf, WAIT_TIME, false, runnable);
    }
}
