import greenfoot.*;

/**
 * This is used for fading in and out of a screen
 * 
 * @author Ainson
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
     * @param direction         Boolean to determine fading in oir fading out
     */
    public Fade(int fade, boolean direction) {
        this.fade = fade;
        this.direction = direction;
        fadeDuration = direction ? fade: 0;
        image = new GreenfootImage(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        image.fill();
        if(direction) {
            image.setTransparency(0);
        } else {
            image.setTransparency(255);
        }
        setImage(image);
    }
    
    /**
     * act method for fade 
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
     * @return fade
     */
    public int getFade() {
        return fade;
    }
}
