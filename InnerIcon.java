import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class InnerIcon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InnerIcon extends Actor
{
    ArrayList <GreenfootImage> innerImages = new ArrayList<GreenfootImage>();
    GreenfootImage exclamationMark = new GreenfootImage("images/icons/exclamation-mark.png");
    GreenfootImage questionMark = new GreenfootImage("images/icons/question-mark.png");
    GreenfootImage hpIcon = new GreenfootImage("images/icons/hp-icon.png");
    GreenfootImage intIcon = new GreenfootImage("images/icons/intelligence-icon.png");
    GreenfootImage strIcon = new GreenfootImage("images/icons/strength-icon.png");

    public InnerIcon(int index, int width, int height){
        fillImages();
        GreenfootImage tempImage = innerImages.get(index);
        tempImage.scale(width, height);
        setImage(tempImage);
    }
    
    private void fillImages(){
        innerImages.add(exclamationMark); //index 0
        innerImages.add(questionMark); //index 1
        innerImages.add(hpIcon); //index 2
        innerImages.add(intIcon); //index 3
        innerImages.add(strIcon); //index 4
    }
    
}
