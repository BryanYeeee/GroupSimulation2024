import greenfoot.*;

/**
 * This is used for fading in and out of a screen.
 * 
 * @author Ainson Z
 * @version April 2024
 */
public class Fade extends Actor
{
    private int fadeDuration;
    private int fade;
    private boolean direction; // true for fade in , false for fade out
    
    private GreenfootImage image;
    /**
     * Constructor for Fade
     * 
     * @param fade              Duration of the fade
     * @param direction         Boolean to determine fading in or fading out
     */
    public Fade(int fade, boolean direction) {
        this.fade = fade;
        this.direction = direction;
        fadeDuration = direction ? fade: 0;
        image = new GreenfootImage(AllWorld.WORLD_WIDTH, AllWorld.WORLD_HEIGHT);
        image.fill();
        if(direction) {
            image.setTransparency(0);
        } else {
            image.setTransparency(255);
        }
        setImage(image);
    }
    
    /**
     * Act method for fade, sets transparency of the image.
     */
    public void act() {
        if(direction) {
            fadeDuration += -1;
        } else {
            fadeDuration += 1;
        }
        
        
        if(fadeDuration > fade || fadeDuration < 0) {
            getWorld().removeObject(this);
            return;
        }
        getImage().setTransparency(255 - (int)(((double)fadeDuration / fade) * 255));
    }
    
    /**
     * Method to get fade duration
     * 
     * @return fade     The duration of the fading effect.
     */
    public int getFade() {
        return fade;
    }
}
