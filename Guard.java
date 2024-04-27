import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Write a description of class Guard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Guard extends Person
{
    // For cutscene/intro
    public Guard(int i, boolean inIntro){
        super(i, inIntro);
        GreenfootImage cutsceneImage = new GreenfootImage("images/guard/male_white/idle/D0.png");
        cutsceneImage.scale(120, 165);
        setImage(cutsceneImage);
        personType = "guard";
    }
    
    public Guard(int i) {
        super(i);
        personType = "guard";
    }
    
    public Accessory getAccessory(){
        return (Accessory) getOneIntersectingObject(Accessory.class);
    }

    public ArrayList<Integer> getAccessories(){
        ArrayList<Integer> result = new ArrayList<>();
        result.add(6);
        return result; 
    }
}
