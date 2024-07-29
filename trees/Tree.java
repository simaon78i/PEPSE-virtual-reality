package pepse.world.trees;
import danogl.GameObject;
import danogl.util.Vector2;
import pepse.world.Avatar;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * this class creates a tree with leaves and fruits on it
 * @author shimon ifrach and avi wolf
 */
public class Tree {

    /**
     * The height of the tree.
     */
    public static final int TREE_HEIGHT = 200;
    /* constants */
    private static final int LEAF_SQUARE_FACTOR_SIZE = 10;
    private static final int FRUIT_SQUARE_FACTOR_SIZE = 8;

    /**
     * Creates a tree.
     * @param position the position of the tree
     * @param avatar the avatar
     * @return the tree
     */
    public static ArrayList<ArrayList<GameObject>> create(Vector2 position, Avatar avatar, int seed) {
        ArrayList<ArrayList<GameObject>> tree = new ArrayList<>();
        Random random = new Random(Objects.hash(position.x(), seed));
        ArrayList<GameObject> trunk = new ArrayList<>();
        trunk.add(Trunk.create(position,avatar));
        tree.add(trunk);
        float time = 0f;
        ArrayList<GameObject> leaves = new ArrayList<>();
        for (int i = -10; i < 10; i++) {
            for (int j = -10; j < 10; j++) {
                if (random.nextInt(10) == 0) {
                    time += 0.05f;
                    leaves.add(Leaf.create(new Vector2(position.x() + j * LEAF_SQUARE_FACTOR_SIZE,
                            (position.y() + i * LEAF_SQUARE_FACTOR_SIZE) - TREE_HEIGHT), time, avatar));
                }
            }
            tree.add(leaves);
        }
        ArrayList<GameObject> fruits = new ArrayList<>();
        for (int i = -8; i < 8; i++) {
            for (int j = -8; j < 8; j++) {
                if (random.nextInt(10) == 0)
                    fruits.add(CreateFruit.create(new Vector2(position.x() + j * FRUIT_SQUARE_FACTOR_SIZE,
                            (position.y() + i * FRUIT_SQUARE_FACTOR_SIZE) - TREE_HEIGHT),avatar));
            }
            tree.add(fruits);
        }
       return tree;
    }
}
