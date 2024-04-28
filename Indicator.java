import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Indicator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Indicator extends Actor
{
    GreenfootImage indicator = new GreenfootImage("indicator.png");
    public Indicator(int width, int height){
        indicator.scale(width, height);
        setImage(indicator);
    }

}
