package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * The avatar is the main character of the game
 * The avatar can move left, right, and jump
 * The avatar can only jump if it is on the ground
 * The avatar can only move left or right if it has enough energy
 * The avatar can only jump if it has enough energy
 * The avatar has an energy bar
 * @author shimon ifrah and avi wolf
 */
public class Avatar extends GameObject {
    /*constants*/
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 100;
    private static final String ENERGY = "Energy: ";
    private static final String PERCENT = " %";
    private static final String FONT_TYPE = "ALGERIAN";
    private static final int BAR_COORDINATES = 10;
    private static final int BAR_DIMENSIONS = 30;
    private static final float ENERGY_COST_FOR_IDLE = 1f;
    private static final float ENERGY_COST_ON_FALLING = 0f;
    private static final float ENERGY_COST_OF_RUNNING=-0.5f;
    private static final float ENERGY_COST_OF_JUMPING= -10f;
    private static final int AVATARS_HEIGHT = 58;
    private static final int START_POSITION = 0;
    private static final int AVATARS_WIDTH = 40;
    private static final String[] ASSETS_ARRAY_FOR_IDLE = {"assets/idle_0.png", "assets/idle_1.png",
            "assets/idle_2.png", "assets/idle_3.png"};
    private static final String[] ASSETS_ARRAY_FOR_RUNNING = {"assets/run_0.png", "assets/run_1.png",
            "assets/run_2.png", "assets/run_3.png", "assets/run_4.png", "assets/run_5.png"};
    private static final String[] ASSETS_ARRAY_FOR_JUMPING = {"assets/jump_0.png", "assets/jump_1.png",
            "assets/jump_2.png", "assets/jump_3.png"};
    private static final double TIME_BETWEEN_CLIPS = 0.2;

    /*fields*/
    private final UserInputListener inputListener;
    private float energy = 100;
    private final AnimationRenderable idleRenderable;
    private final AnimationRenderable runRenderable;
    private final AnimationRenderable jumpRenderable;
    private final ArrayList<Runnable> jumpObservers = new ArrayList<>();

    /**
     * Create the avatar
     * he can move left, right, and jump
     * he can only jump if it is on the ground
     * he also can only move left or right if it has enough energy
     * he also can only jump if it has enough energy
     * he also has an energy bar
     * @param windowDimensions the dimensions of the window
     * @param imageReader the image reader
     * @param inputListener the input listener
     */
    public Avatar(Vector2 windowDimensions, ImageReader imageReader, UserInputListener inputListener) {
        super(new Vector2(START_POSITION, (windowDimensions.y() * Terrain.START_POINT_FACTOR) -
                       AVATARS_HEIGHT),
                new Vector2(AVATARS_WIDTH, AVATARS_HEIGHT),
                null);
        idleRenderable = new AnimationRenderable(ASSETS_ARRAY_FOR_IDLE, imageReader, true,
                TIME_BETWEEN_CLIPS);
        runRenderable = new AnimationRenderable(ASSETS_ARRAY_FOR_RUNNING, imageReader, true,
                1);
        jumpRenderable = new AnimationRenderable(ASSETS_ARRAY_FOR_JUMPING, imageReader, true,
                TIME_BETWEEN_CLIPS);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
    }

    /**
     * The possible states of the avatar
     */
    public enum State {
        IDLE(ENERGY_COST_FOR_IDLE),
        RUN(ENERGY_COST_OF_RUNNING),
        JUMP(ENERGY_COST_OF_JUMPING),
        ON_MOVEMENT(ENERGY_COST_ON_FALLING);
        private final float energyCost;
        State(float energyCost) {
            this.energyCost = energyCost;
        }
    }

    /**
     * Update the avatar
     * he can move left, right, and jump
     * he can only jump if it is on the ground
     * he also can only move left or right if it has enough energy
     * he also can only jump if it has enough energy
     * @param deltaTime the time passed since the last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        State state = State.ON_MOVEMENT;
        float xVel = MIN_VALUE;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) && energy + State.RUN.energyCost >= MIN_VALUE) {
            xVel -= VELOCITY_X;
            state = State.RUN;
            renderer().setIsFlippedHorizontally(true);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && energy + State.RUN.energyCost >= MIN_VALUE) {
            xVel += VELOCITY_X;
            state = State.RUN;
            renderer().setRenderable(runRenderable);
        }
        transform().setVelocityX(xVel);
        if ((inputListener.isKeyPressed(KeyEvent.VK_SPACE) || inputListener.isKeyPressed(KeyEvent.VK_UP))
                && getVelocity().y() == MIN_VALUE && energy + State.JUMP.energyCost >= MIN_VALUE) {
            transform().setVelocityY(VELOCITY_Y);
            for (Runnable observer : jumpObservers) {
                observer.run();
            }
            state = State.JUMP;
            if (xVel == MIN_VALUE) {
                renderer().setRenderable(jumpRenderable);
            }
        }
        if (getVelocity().y() == MIN_VALUE && getVelocity().x() == MIN_VALUE) {
            state = State.IDLE;
            renderer().setRenderable(idleRenderable);
        }
        energy += state.energyCost;
        setEnergy(MIN_VALUE);
    }

    /**
     * Add an observer to the avatar
     * The observer will be called when the avatar jumps
     * The observer should match the BooleanSupplier interface
     * @param observer the observer to be added
     */
    public void addJumpObserver(Runnable observer) {
        jumpObservers.add(observer);
    }

    /**
     * Set the energy of the avatar
     *
     * @param energy the new energy added to the current energy
     */
    public void setEnergy(float energy) {
        this.energy += energy;
        if (this.energy > MAX_VALUE) {
            this.energy = MAX_VALUE;
        }
        if (this.energy < MIN_VALUE) {
            this.energy = MIN_VALUE;
        }
    }
    /**
     * Create the energy bar of the avatar
     * The energy bar is a text renderable
     * that shows the energy of the avatar
     * The energy bar is created in the top
     * left corner of the screen
     * @return the energy bar
     */
    public GameObject createEnergyBar() {
        TextRenderable textRenderable = new TextRenderable(ENERGY + (int) energy + PERCENT, FONT_TYPE);
        GameObject energyBar = new GameObject(new Vector2(BAR_COORDINATES, BAR_COORDINATES),
                new Vector2(BAR_DIMENSIONS, BAR_DIMENSIONS), textRenderable);
        energyBar.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        energyBar.addComponent((float deltaTime) -> textRenderable.setString(ENERGY +
                (int) energy + PERCENT));
        return energyBar;
    }
}
