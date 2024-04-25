import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class IntroButton extends Actor
{
    private GreenfootImage button = new GreenfootImage("button.png");
    public IntroButton(boolean mirror){
        button.scale(100, 100);
        if(mirror){
            button.mirrorHorizontally();
        }
        setImage(button);
    }
}
