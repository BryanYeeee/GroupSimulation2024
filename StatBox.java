import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The background display of the stats in StatWorld.
 * 
 * @author Ainson Z.
 * @version April 2024
 */
public class StatBox extends Actor
{
    private GreenfootImage image;
    /**
     * Constructor for StatBox, set and scale an image.
     */
    public StatBox () {
        image = new GreenfootImage("StatsMenu2.png");
        image.scale(800, 700);
        setImage(image);
    }
}
