import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The introbutton is responsible for navigating the character intros in the beginning of the simulation.
 * 
 * @author Jamison H
 * @version April 2024
 */
public class IntroButton extends Button
{
    private GreenfootImage button = new GreenfootImage("button.png");
    /**
     * Constructor for IntroButton, set and optionally mirror the button image.
     * 
     * @param mirror    True to mirror the image from left to right, false to keep the current orientation.
     */
    public IntroButton(boolean mirror){
        button.scale(100, 100);
        if(mirror){
            button.mirrorHorizontally();
        }
        setImage(button);
    }
}
