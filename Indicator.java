import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The indicator class is a background bubble to show various actions done by the main prisoners.
 * 
 * @author Jamison H
 * @version April 2024
 */
public class Indicator extends Actor
{
    GreenfootImage indicator = new GreenfootImage("indicator.png");
    /**
     * Constructor for Indicator, set and scale my image.
     * 
     * @param width     The width of the image.
     * @param height    The height of the image.
     */
    public Indicator(int width, int height){
        indicator.scale(width, height);
        setImage(indicator);
    }

}
