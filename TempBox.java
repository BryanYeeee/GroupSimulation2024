import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class that draws a background box with an optional border
 * 
 * @author Jamison H
 * @version April 2024
 */
public class TempBox extends Actor
{
    public TempBox(int width, int height, Color color){
        GreenfootImage box = new GreenfootImage(width, height);
        box.setColor(color);
        box.fill();
        setImage(box);
    }
    
    public TempBox(int width, int height, Color bgColor, Color borderColor, int borderThickness){
        GreenfootImage box  = new GreenfootImage(width, height);
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
