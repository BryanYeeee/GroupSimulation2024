import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class NextButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NextButton extends Button
{
    private GreenfootImage image;
    private static boolean switchWorld;
    
    public NextButton(String theImage) {
        image = new GreenfootImage(theImage);
        image.scale(200, 75);
        setImage(image);
    }
    
    public void act()
    {
        if(Greenfoot.mouseClicked(this)) {
            switchWorld = true;
        }
    }
    
    public static boolean getSwitchWorld() {
        return switchWorld;
    }
    
    public static void resetSwitchWorld() {
        switchWorld = false;
    }
}
