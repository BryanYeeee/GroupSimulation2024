import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The indicator class will show various actions done by the main prisoners
 * 
 * @author Jamison H
 * @version April 2024
 */
public class Indicator extends Actor
{
    GreenfootImage indicator = new GreenfootImage("indicator.png");
    public Indicator(int width, int height){
        indicator.scale(width, height);
        setImage(indicator);
    }

}
