import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The nighttime class is the colour filter that makes the world appear to be nighttime
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class NightTime extends Actor
{
    public void addedToWorld(World w) {
        setLocation(w.getWidth()/2,w.getHeight()/2);
    }
}
