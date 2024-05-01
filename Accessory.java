import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;
import java.util.Map;
/**
 * Accessories are cosmetics that are added ontop of the prisoners and guards.
 * Some examples include hair and police hats
 * 
 * @author Jeff G
 * @version April 2024
 */
public class Accessory extends Entity
{
    GreenfootImage currentImage;
    private Person origin;
    private int xOffset=0;
    private int originalYOffset = -11;
    private int yOffset=-11;
    private int accessoryIndex=0;
    private int actsLeft;   
    private static final Map<String, Integer> offsetValues = initializeOffsetValues();
    
    /**
     * Used to set yOffset for diiferent animation frames
     * 
     * @return values                   Animation frame yOffsets
     */
    private static Map<String, Integer> initializeOffsetValues() {
        //These values correspond to the yOffset of different frames in animation
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
        values.put("attackL0", -2);
        values.put("attackL1", -2);
        values.put("attackL2", -1);
        values.put("attackL3", -1);
        values.put("attackD0", -2);
        values.put("attackD1", 10);
        values.put("attackD2", 12);
        values.put("attackD3", 10);
        values.put("attackU0", 3);
        values.put("attackU1", 2);
        values.put("attackU2", 1);
        values.put("attackU3", 2);
        return values;
    }

    /**
     * Constructor for Accessory
     * 
     * @param origin                    Prisoner that accessory is attached to
     * @param accessoryIndex            Index of the accessory
     */
    public Accessory(Person origin, int accessoryIndex){
        this.origin = origin;
        this.accessoryIndex = accessoryIndex;
    }

    /**
     * Act method for Accessory
     */
    public void act()
    {
        try {
            if (origin.getDirChar() == 'R') {
                yOffset = originalYOffset + (int)offsetValues.get(origin.getAction() + 'L' + origin.getImageIndex());
            } else {
                yOffset = originalYOffset + (int)offsetValues.get(origin.getAction() + origin.getDirChar() + origin.getImageIndex());
            }
        } catch (NullPointerException | ClassCastException e) {
            yOffset = originalYOffset; 
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

        currentImage = new GreenfootImage("images/accessories/"+accessoryIndex+origin.getDirChar()+".png");
        if(Person.inIntro){
            currentImage.scale(120, 120);
            yOffset = -40;
        } else {
            currentImage.scale(32, 32);
        }
        
        if(actsLeft <= 60 && actsLeft > 0){
            fade(actsLeft, 60);
        } 
        
        if(origin.getAction().equals("sleep")){
            yOffset=2;
            if(origin.getDirChar() == 'R'){
                setRotation(-90);
                xOffset=-15;
            }
            else {
                setRotation(90);
                xOffset=15;
            }
        }
        else{
            setRotation(0);
        }
        setImage(currentImage);
        setLocation(origin.getX()+xOffset,origin.getY()+yOffset);
    }
    
    /**
     * Sets acts left
     * 
     * @param acts                  Sets to current acts
     */
    public void setActsLeft(int acts){
        actsLeft = acts;
    }
    
    /**
     * Fades the object in or out of the world
     * 
     * @param timeLeft              Time left for fade to complete
     * @param totalFadeTime         Total time for fade duration
     */
    public void fade (int timeLeft, int totalFadeTime){
        double percent = timeLeft / (double)totalFadeTime;
        if (percent > 1.00) return;
        int newTranparency = (int)(percent * 255);
        currentImage.setTransparency (newTranparency);
    }

}
