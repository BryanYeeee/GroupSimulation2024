import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Abstract class of all worlds
 * 
 * @author Ainson 
 * @version April 2024
 */
public abstract class AllWorld extends World
{
    protected SoundManager sm;
    protected boolean worldTransition;
    protected Fade fadeIn, fadeOut;
    protected int acts;
    private World world;
    /**
     * Constructor for AllWorld
     */
    public AllWorld(int width, int height, int cellSize)
    {    
        super(width, height, cellSize);
        SoundManager.initSounds();
        
        acts = 0;
        
        worldTransition = false;
        
        fadeIn = new Fade(120, false);
        fadeOut = new Fade(120, true);
        
        addObject(fadeIn, Constants.WORLD_WIDTH/2, Constants.WORLD_HEIGHT/2);
    }
    
    /**
     * Fade when going to another world
     */
    public void act() {
        
        /*
        if(worldTransition) {
            acts++;
            if(acts >= fadeOut.getFade()) {
                Greenfoot.setWorld(world);
            }
        }
        */
    }
    
    /**
     * Play music
     */
    public void started() {
        sm.resumeSounds();
    }
    
    /**
     * Stop muisc
     */
    public void stopped() {
        sm.pauseSounds();
    }
    
    /**
     * gets SoundManager
     * 
     * @return Sound Manager
     */
    public SoundManager getSM() {
        return sm;
    }
    
    /**
     * Fades out of the current world into another world
     */
    public void goToWorld(World w) {
        worldTransition = true;
        addObject(fadeOut, Constants.WORLD_WIDTH/2, Constants.WORLD_HEIGHT/2);
        world = w;
    }
}
