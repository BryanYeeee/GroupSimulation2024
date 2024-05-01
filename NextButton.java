import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The next button is used for swapping between the statworld and the selectworld.
 * 
 * @author Ainson Z
 * @version April 2024
 */
public class NextButton extends Button
{
    private GreenfootImage image;
    private static boolean switchWorld = false;
    
    /**
     * Constructor for NextButton, sets the image of the button.
     * 
     * @param theImage  The file name of the image.
     */
    public NextButton(String theImage) {
        image = new GreenfootImage(theImage);
        image.scale(200, 75);
        setImage(image);
    }
    
    /**
     * The act method of NextButton, switch worlds if I am clicked.
     */
    public void act()
    {
        if(Greenfoot.mouseClicked(this)) {
            switchWorld = true;
        }
    }
    
    /**
     * Return whether the world is being switched.
     * 
     * @return switchWorld  True if being switched, false if not.
     */
    public static boolean getSwitchWorld() {
        return switchWorld;
    }
    
    /**
     * Reset the value of switchWorld to false.
     */
    public static void resetSwitchWorld() {
        switchWorld = false;
    }
}
