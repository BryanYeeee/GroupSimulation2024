import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TempBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TempBox extends Actor
{
    public TempBox(int width, int height){
        GreenfootImage box = new GreenfootImage(width, height);
        box.setColor(Color.BLACK);
        box.fill();
        setImage(box);
    }
}
