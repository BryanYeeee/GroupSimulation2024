import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The wall cover is used to hide the person actors behind certain walls and objects.
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class WallCover extends Actor
{
    /**
     * Constructor of WallCover, set an image.
     * 
     * @param img   The image file name.
     */
    public WallCover(String img)
    {
        setImage(new GreenfootImage(img));
    }
}
