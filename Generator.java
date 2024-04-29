import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Generator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Generator extends Actor
{
    private final String offImg = "generatorOff.png";
    public void turnOff() {
        setImage(new GreenfootImage(offImg));
        ElectricFence.setOnOff(false);
    }
}
