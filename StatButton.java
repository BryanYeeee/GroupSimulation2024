import greenfoot.*;

/**
 * StatButton class that is used to generate arrow images
 * that can edit a stat by decreasing or increasing
 * 
 * @author Ainson
 * @version April 2024
 */
public class StatButton extends Button {
    private StatSetter statSetter;
    private boolean increase; // Indicates whether the button increases or decreases the value
    private GreenfootImage image;
    private static boolean isStat;
    private static boolean switchWorld;

    /**
     * Constructer of the button
     * 
     * @param statSetter            specifies which stats should be modified
     * @param increase              boolean determines whether the button is used to increase or decrease
     */
    public StatButton(StatSetter statSetter, boolean increase) {
        this.statSetter = statSetter;
        this.increase = increase;

        image = new GreenfootImage("gameArrow.png");
        image.scale(50, 50);
        if(!increase) {
            image.mirrorHorizontally();
        }
        setImage(image);
    }

    /**
     * Act method
     * On mouse click increase or decrease stat
     */
    public void act() {
        if(Greenfoot.mouseClicked(this)) {
            if(increase) {
                statSetter.increaseValue();
            } else {
                statSetter.decreaseValue();
            }
        }
    }
}
