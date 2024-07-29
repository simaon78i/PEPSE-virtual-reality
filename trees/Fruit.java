package pepse.world.trees;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Avatar;

/**
 * this class Represents a fruit object in the game.
 * When the avatar collides with the fruit, the avatar's energy
 * is increased by a fixed amount.
 * The fruit fades out when the avatar collides with it,
 * and fades in after a fixed amount of time.
 * @author shimon ifrach and avi wolf
 */
public class Fruit extends GameObject {
    private static final float EATING_FRUIT_ENERGY = 10f;
    private static final float FADE_OUT_OR_IN_TIME = 0.5f;
    private static final float WAIT_TIME = 3f;
    private final Avatar avatar;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Fruit(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Avatar avatar){
        super(topLeftCorner, dimensions, renderable);
        this.avatar = avatar;
    }

    /**
     * Called when a collision is detected with another GameObject.
     * This method is called once per frame, for each GameObject that
     * this GameObject is colliding with.
     * @param other the GameObject that this GameObject collided with
     * @param collision the Collision object representing the collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        avatar.setEnergy(EATING_FRUIT_ENERGY);
        renderer().fadeOut(FADE_OUT_OR_IN_TIME);
        new ScheduledTask(this, WAIT_TIME, false, () ->
                renderer().fadeIn(FADE_OUT_OR_IN_TIME));
    }
}
