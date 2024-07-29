package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;


/**
 * the class that creates the terrain
 *
 * @author shimon ifrach and avi wolf
 */
public class Terrain {

    /* constants */
    /**
     * The factor of the window height where the terrain starts.
     */
    public static final float START_POINT_FACTOR = 0.75f;
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    private static final int SMOOTH_NOISE_FACTOR = 7;
    private static final String GROUND = "ground";

    /* fields */
    private final NoiseGenerator noiseGenerator;
    private final float groundHeightAtX0;

    /* constructors */

    /**
     * Constructs a new Terrain object.
     *
     * @param windowDimensions the dimensions of the window
     * @param seed             the seed for the terrain generation
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        groundHeightAtX0 = windowDimensions.y() * START_POINT_FACTOR;
        noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);
    }

    /* methods */

    /**
     * Returns the height of the ground at the given x-coordinate.
     *
     * @param x the x-coordinate
     * @return the height of the ground at the given x-coordinate
     */
    public float getGroundHeightAt(float x) {
        return groundHeightAtX0 + (float) noiseGenerator.noise(x, Block.SIZE * SMOOTH_NOISE_FACTOR);
    }

    /**
     * Creates the terrain in the given range.
     *
     * @param minX the minimum x-coordinate
     * @param maxX the maximum x-coordinate
     * @return the blocks that make up the terrain
     */
    public ArrayList<Block> createInRange(int minX, int maxX) {
        ArrayList<Block> blocks = new ArrayList<>();
        int currentX = (int) (double) (minX / Block.SIZE) * Block.SIZE;
        int currentY;
        while (currentX <= maxX) {
            currentY = (int) (double) (getGroundHeightAt(currentX) / Block.SIZE) * Block.SIZE;
            for (int i = 0; i < TERRAIN_DEPTH; i++) {
                Block block = new Block(new Vector2(currentX, currentY + (i * Block.SIZE)),
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR)));
                block.setTag(GROUND);
                blocks.add(block);
            }
            currentX += Block.SIZE;
        }
        return blocks;
    }
}
