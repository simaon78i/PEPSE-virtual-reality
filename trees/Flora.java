package pepse.world.trees;

import danogl.GameObject;
import danogl.util.Vector2;
import pepse.world.Avatar;
import pepse.world.Block;

import java.util.*;
import java.util.function.Function;

/**
 * this class creates flora in the game world
 * @author shimon ifrach and avi wolf
 */
public class Flora {
    private final Function<Float, Float> getHeightAtX;
    private final int seed;
    private final Avatar avatar;

    public Flora(Function<Float, Float> getHeightAtX, int seed, Avatar avatar) {
        this.getHeightAtX = getHeightAtX;
        this.seed = seed;
        this.avatar = avatar;


    }
    /**
     * Creates flora in the given range.
     * @param minX the minimum x value
     * @param maxX the maximum x value
     * @return the flora
     */
    public  ArrayList<GameObject> createInRange(int minX, int maxX){
        ArrayList<GameObject> flora = new ArrayList<>();

        int currentX =  (int) (double) (minX / Block.SIZE) * Block.SIZE;
        int currentY;

        while (currentX <= maxX) {
            currentY = (int) (double) (getHeightAtX.apply((float)currentX) / Block.SIZE) * Block.SIZE;

            if (new Random(Objects.hash(currentX, seed)).nextInt(10) == 0) {
                for (ArrayList<GameObject> l: Tree.create(new Vector2(currentX, currentY), avatar, seed)){
                    flora.addAll(l);
                }
            }

            currentX += Block.SIZE;
        }
        return flora;
    }
}
