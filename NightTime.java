import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The nighttime class is the colour filter that makes the world appear to be nighttime.
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class NightTime extends Actor
{
    /**
     * Add the color filter to the center of the simulation world.
     * 
     * @param w     The world to be added to.
     */
    public void addedToWorld(World w) {
        setImage("images/Nighttime.png");
        setLocation(w.getWidth()/2,w.getHeight()/2);
    }
}
