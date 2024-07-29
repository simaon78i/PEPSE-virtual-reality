package pepse.world.trees;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Avatar;
import pepse.world.Block;
import java.awt.*;

/**
 * this class creates a trunk
 * @author shimon ifrach and avi wolf
 */
public class Trunk {

    /* constants */
    private static final Color BASIC_TRUNK_COLOR = new Color(100, 50, 20);
    private static final String TRUNK = "trunk";
    private static final int INITIAL_VALUE = 0;

    /**
     * Creates a trunk. The trunk is a block with a fixed height and width.
     * The trunk is colored with a basic trunk color.
     * @param position the position of the trunk
     * @return the trunk
     */
    public static Block create(Vector2 position, Avatar avatar) {
        Block trunk = new Block(position, new RectangleRenderable(BASIC_TRUNK_COLOR));
        trunk.setDimensions(new Vector2(Block.SIZE, Tree.TREE_HEIGHT));
        trunk.setTopLeftCorner(position.subtract(new Vector2(INITIAL_VALUE, Tree.TREE_HEIGHT)));
        trunk.setTag( TRUNK);
        avatar.addJumpObserver(() -> trunk.renderer().setRenderable(new RectangleRenderable(ColorSupplier.
                approximateColor(BASIC_TRUNK_COLOR))));
        return trunk;
    }
}
