import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The next button is used for swapping between the statworld and the selectworld
 * 
 * @author Ainson Z
 * @version April 2024
 */
public class NextButton extends Button
{
    private GreenfootImage image;
    private static boolean switchWorld = false;
    
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
