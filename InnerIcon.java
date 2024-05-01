import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * The inner icon is the icon that appears within the Indicator class bubble.
 * 
 * @author Jamison H
 * @version April 2024
 */
public class InnerIcon extends Actor
{
    ArrayList <GreenfootImage> innerImages = new ArrayList<GreenfootImage>();
    GreenfootImage exclamationMark = new GreenfootImage("images/icons/exclamation-mark.png");
    GreenfootImage questionMark = new GreenfootImage("images/icons/question-mark.png");
    GreenfootImage hpIcon = new GreenfootImage("images/icons/hp-icon.png");
    GreenfootImage intIcon = new GreenfootImage("images/icons/intelligence-icon.png");
    GreenfootImage strIcon = new GreenfootImage("images/icons/strength-icon.png");

    /**
     * Constructor for InnerIcon, fill possible icon images and select a certain image to be displayed.
     * 
     * @param index     The index of the displayed image.
     * @param width     The width of the image.
     * @param height    The height of the image.
     */
    public InnerIcon(int index, int width, int height){
        fillImages();
        GreenfootImage tempImage = innerImages.get(index);
        tempImage.scale(width, height);
        setImage(tempImage);
    }
    
    /**
     * Fill an array with possible icon images.
     */
    private void fillImages(){
        innerImages.add(exclamationMark); //index 0
        innerImages.add(questionMark); //index 1
        innerImages.add(hpIcon); //index 2
        innerImages.add(intIcon); //index 3
        innerImages.add(strIcon); //index 4
    }
    
}
