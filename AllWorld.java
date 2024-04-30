import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Abstract class of all worlds containing world size, sounds, and fade transitions.
 * 
 * @author Ainson Z.
 * @version April 2024
 */
public abstract class AllWorld extends World
{   
    // Height and width of all worlds
    public static final int WORLD_HEIGHT = 850;
    public static final int WORLD_WIDTH = 1200;

    protected SoundManager sm;
    protected boolean worldTransition;
    protected Fade fadeIn, fadeOut;
    protected int acts;
    private World world;
    /**
     * Constructor for AllWorld, simliar to a normal world constructor but without the boolean bounded. 
     * This constructor will initalize sounds and values for fading between worlds.
     * 
     * @param width     The width of the world
     * @param height    The height of the world
     * @param cellSize  The size of each cell of the world, typically 1
     */
    public AllWorld(int width, int height, int cellSize)
    {    
        super(width, height, cellSize);
        SoundManager.initSounds();

        acts = 0;

        worldTransition = false;

        fadeIn = new Fade(120, false);
        fadeOut = new Fade(120, true);

        addObject(fadeIn, AllWorld.WORLD_WIDTH/2, AllWorld.WORLD_HEIGHT/2);
    }

    /**
     * Play music using the sound manager and set the speed of the world.
     */
    public void started() {
        Greenfoot.setSpeed(50);
        sm.resumeSounds();
    }

    /**
     * Stop music using the sound manager.
     */
    public void stopped() {
        sm.pauseSounds();
    }

    /**
     * Gets an instance of SoundManager.
     * 
     * @return SoundManager The instance of SoundManager.
     */
    public SoundManager getSM() {
        return sm;
    }

    /**
     * Fades out of the current world into another world.
     * 
     * @param w   The world to go to.
     */
    public void goToWorld(World w) {
        worldTransition = true;
        addObject(fadeOut, AllWorld.WORLD_WIDTH/2, AllWorld.WORLD_HEIGHT/2);
        world = w;
    }
}
