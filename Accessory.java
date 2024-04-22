import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;
import java.util.Map;
/**
 * Write a description of class Hair here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Accessory extends Entity
{
    /**
     * Act - do whatever the Hair wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private Person origin;
    private int xOffset=0;
    private int originalYOffset = -11;
    private int yOffset=-11;
    private int accessoryIndex=0;
    private static final Map<String, Integer> offsetValues = initializeOffsetValues();
    
    private static Map<String, Integer> initializeOffsetValues() {
        Map<String, Integer> values = new HashMap<>();
        values.put("idleD0", 0);
        values.put("idleD1", 1);
        values.put("idleD2", 1);
        values.put("idleL0", -1);
        values.put("idleL1", 1);
        values.put("idleL2", 1);
        values.put("idleU0", 1);
        values.put("idleU1", 2);
        values.put("idleU2", 2);
        values.put("walkD0", 0);
        values.put("walkD1", 0);
        values.put("walkD2", -1);
        values.put("walkD3", -2);
        values.put("walkD4", -2);
        values.put("walkD5", -1);
        values.put("walkD6", 0);
        values.put("walkD7", 0);
        values.put("walkD8", -1);
        values.put("walkD9", -2);
        values.put("walkD10", -2);
        values.put("walkD11", -1);
        values.put("walkL0", 0);
        values.put("walkL1", -1);
        values.put("walkL2", -1);
        values.put("walkL3", 0);
        values.put("walkL4", 1);
        values.put("walkL5", 0);
        values.put("walkL6", 0);
        values.put("walkL7", -1);
        values.put("walkL8", -1);
        values.put("walkL9", 0);
        values.put("walkL10", 2);
        values.put("walkL11", 0);
        values.put("walkU0", -1);
        values.put("walkU1", 0);
        values.put("walkU2", -1);
        values.put("walkU3", -1);
        values.put("walkU4", 0);
        values.put("walkU5", -1);
        values.put("walkU6", -1);
        values.put("walkU7", 0);
        values.put("walkU8", -1);
        values.put("walkU9", -1);
        values.put("walkU10", -1);
        values.put("walkU11", 1);
        return values;
    }

    public Accessory(Person origin, int accessoryIndex){
        this.origin = origin;
        this.accessoryIndex = accessoryIndex;
    }

    public void act()
    {
        if(origin.getDirChar()=='R'){
            yOffset=originalYOffset+(int)offsetValues.get(origin.getAction()+'L'+origin.getImageIndex());
        }
        else{
            yOffset=originalYOffset+(int)offsetValues.get(origin.getAction()+origin.getDirChar()+origin.getImageIndex());
        }
        if(origin.getDirChar()=='L'){
             xOffset=1;
        }
        else if(origin.getDirChar()=='R'){
             xOffset=-1;
        }
        else{
            xOffset=0;
        }
        
        GreenfootImage currentImage = new GreenfootImage("images/inmate-accessories/"+accessoryIndex+origin.getDirChar()+".png");
        currentImage.scale(32, 32);
        setImage(currentImage);
        setLocation(origin.getX()+xOffset,origin.getY()+yOffset);

    }

}
