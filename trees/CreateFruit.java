package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Avatar;

import java.awt.*;

/**
 * This class is responsible for creating and managing the behavior of fruit objects within the game.
 * Fruits can change color when certain events occur, such as the avatar jumping.
 */
public class CreateFruit {

    private static final String RED_FRUIT = "Red Fruit"; // Tag for red fruit.
    private static final String YELLOW_FRUIT = "Yellow Fruit"; // Tag for yellow fruit.
    private static final Renderable RED_RENDERABLE = new OvalRenderable(Color.RED); // Renderable for red fruit.
    private static final Renderable YELLOW_RENDERABLE = new OvalRenderable(Color.YELLOW); // Renderable for yellow fruit.
    private static final int FRUIT_SIZE = 10;

    /**
     * Creates a fruit GameObject at a specified position with an initial color of red.
     * The fruit's color changes between red and yellow when the avatar jumps.
     *
     * @param position The position to place the fruit in the game world.
     * @param avatar   The avatar, whose actions can trigger color changes in the fruit.
     * @return A GameObject representing the fruit.
     */
    public static GameObject create(Vector2 position, Avatar avatar) {
        GameObject fruit = new Fruit(position, new Vector2(FRUIT_SIZE, FRUIT_SIZE), new OvalRenderable
                (Color.RED), avatar);
        fruit.setTag(RED_FRUIT);

        Runnable runnable = getRunnableForUpdateFruitColor(fruit);
        avatar.addJumpObserver(runnable);

        return fruit;
    }

    /**
     * Generates a Runnable that toggles the fruit's color between red and yellow upon execution.
     * This method is intended for internal use to handle color change logic.
     *
     * @param fruit The fruit GameObject whose color is to be toggled.
     * @return A Runnable that changes the fruit's color when run.
     */
    private static Runnable getRunnableForUpdateFruitColor(GameObject fruit) {
        return () -> {
            if (fruit.getTag().equals(RED_FRUIT)) {
                fruit.renderer().setRenderable(YELLOW_RENDERABLE);
                fruit.setTag(YELLOW_FRUIT);
            } else if (fruit.getTag().equals(YELLOW_FRUIT)) {
                fruit.renderer().setRenderable(RED_RENDERABLE);
                fruit.setTag(RED_FRUIT);
            }
        };
    }

}
