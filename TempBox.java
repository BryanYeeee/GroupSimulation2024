import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class that draws a background box with an optional border.
 * 
 * @author Jamison H
 * @version April 2024
 */
public class TempBox extends Actor
{
    /**
     * Constructor of TempBox without a border.
     * 
     * @param width     The box's width.
     * @param height    The box's height.
     * @param color     The box's background color.
     */
    public TempBox(int width, int height, Color color){
        GreenfootImage box = new GreenfootImage(width, height);
        box.setColor(color);
        box.fill();
        setImage(box);
    }
    
    /**
     * A detailed constructor of TempBox with a border
     * 
     * @param width             The box's width.
     * @param height            The box's height.
     * @param bgColor           The box's background color.
     * @param borderColor       The box's border color.
     * @param borderThickness   The box's border thickness.
     */
    public TempBox(int width, int height, Color bgColor, Color borderColor, int borderThickness){
        GreenfootImage box = new GreenfootImage(width, height);
        // Draw Background
        box.setColor(bgColor);
        box.fill();
        
        // Draw Border
        box.setColor(borderColor);
        for (int i = 0; i < borderThickness; i++){
            box.drawRect (0 + i, 0 + i, width - 1 - (i * 2), height - 1 - (i*2));
        }
        setImage(box);
    }
}
