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
    GreenfootImage exclamationMark = new GreenfootImage("exclamation-mark.png");
    GreenfootImage questionMark = new GreenfootImage("question-mark.jpg");
    public InnerIcon(int index, int width, int height){
        fillImages();
        GreenfootImage tempImage = innerImages.get(index);
        tempImage.scale(width, height);
        setImage(tempImage);
    }
    
    private void fillImages(){
        innerImages.add(exclamationMark);
        innerImages.add(questionMark);
    }
    
}
