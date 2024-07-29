package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.world.Avatar;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;

import java.util.ArrayList;


/**
 * The game manager for the Pepse game.
 * This class is responsible for initializing the game and its objects.
 * It should be used to create the initial game objects and add them to the game.
 * It should also be used to set the target framerate of the game.
 *
 * @author shimon ifrach and avi wolf
 */
public class PepseGameManager extends GameManager {
    /*constants*/
    private static final int CYCLE_TIME_VALUE = 30;
    private static final int SEED_VALUE = new java.util.Random().nextInt();
    private static final int SUN_LAYER = -101;
    private static final int SUN_HALO_LAYER = -102;
    private static final int FRAME_CHANGE_PACE = 50;
    private static final int FACTOR_TO_MULTIPLY = 2;

    /*fields*/
    private Avatar avatar;
    private Terrain terrain;
    private Flora flora;
    private float worldBlockSize = 0;
    private final ArrayList<GameObject>[] blocks = new ArrayList[4];
    private float centerX = 0;


    /**
     * Initializes the game. This method is called once, when the game is started.
     * It should be used to create the initial game objects and add them to the game.
     *
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from
     *                         disk. See its documentation for help.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether
     *                         a given key is currently pressed by the user or not. See its
     *                         documentation.
     * @param windowController Contains an array of helpful, methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Vector2 windowDimensions = windowController.getWindowDimensions();
        worldBlockSize = windowController.getWindowDimensions().x();
        gameObjects().addGameObject(Sky.create(windowDimensions), Layer.BACKGROUND);
        terrain = new Terrain(windowDimensions, SEED_VALUE);
        avatar = new Avatar(windowDimensions, imageReader, inputListener);
        flora = new Flora(terrain::getGroundHeightAt, SEED_VALUE, avatar);
        gameObjects().addGameObject(avatar, Layer.DEFAULT);
        gameObjects().addGameObject(avatar.createEnergyBar(), Layer.FOREGROUND);
        centerX = avatar.getCenter().x();
        for (int i = 0; i < 2; i++) {
            createBlock((int) (centerX + i * worldBlockSize), (int) (centerX + (i + 1) * worldBlockSize),
                    i + 2);
        }
        for (int i = 0; i < 2; i++) {
            createBlock((int) (centerX + -(i + 1) * worldBlockSize), (int) (centerX + -i * worldBlockSize),
                    1 - i);
        }
        gameObjects().addGameObject(Night.create(windowDimensions, CYCLE_TIME_VALUE), Layer.FOREGROUND);
        float windowX = windowDimensions.x();
        float windowY = windowDimensions.y();
        Vector2 windowDimensionsStart = new Vector2(windowX, windowY);
        /*GameObject*/
        GameObject sun = Sun.create(windowDimensionsStart, CYCLE_TIME_VALUE *
                FACTOR_TO_MULTIPLY);
        gameObjects().addGameObject(sun, SUN_LAYER);
        gameObjects().addGameObject(SunHalo.create(sun), SUN_HALO_LAYER);
        windowController.setTargetFramerate(FRAME_CHANGE_PACE);
        setCamera(new Camera(avatar,
                Vector2.ZERO, windowDimensions, windowDimensions));
    }

    /**
     * Updates the game. This method is called once per frame.
     * It should be used to update the game's logic, such as moving game objects,
     * checking for collisions, gathering input, and playing sounds.
     *
     * @param deltaTime The time passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        updateWorldAccordingToAvatarPosition();
    }

    private void updateWorldAccordingToAvatarPosition() {
        float avatarPosition = avatar.getCenter().x();
        float distanceFromCenter = avatarPosition - centerX;
        if (distanceFromCenter > worldBlockSize) {
            centerX += worldBlockSize;
            blocks[0].forEach(block -> gameObjects().removeGameObject(block));
            blocks[0].clear();
            blocks[0] = blocks[1];
            blocks[1] = blocks[2];
            blocks[2] = blocks[3];
            blocks[3].clear();
            createBlock((int) (centerX + worldBlockSize), (int) (centerX + 2 * worldBlockSize), 3);
        } else if (distanceFromCenter < -worldBlockSize) {
            centerX -= worldBlockSize;
            blocks[3].forEach(block -> gameObjects().removeGameObject(block));
            blocks[3].clear();
            blocks[3] = blocks[2];
            blocks[2] = blocks[1];
            blocks[1] = blocks[0];
            blocks[0].clear();
            createBlock((int) (centerX - 2 * worldBlockSize), (int) (centerX - worldBlockSize), 0);
        }
    }

    private void createBlock(int start, int end, int index) {
        blocks[index] = new ArrayList<>();
        blocks[index].addAll(terrain.createInRange(start, end));
        blocks[index].forEach(block -> gameObjects().addGameObject(block, Layer.STATIC_OBJECTS));
        blocks[index].addAll(flora.createInRange(start, end));
        blocks[index].forEach(block -> gameObjects().addGameObject(block, Layer.STATIC_OBJECTS));
    }

    /**
     * main function that responsible for running the program
     * @param args the arguments
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
