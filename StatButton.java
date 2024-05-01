import greenfoot.*;

/**
 * StatButton class that is used to generate arrow images.
 * that can edit a stat by decreasing or increasing.
 * 
 * @author Bryan Y and Ainson Z
 * @version April 2024
 */
public class StatButton extends Button {
    private SavedPrisoner sp;
    private boolean increase; // Indicates whether the button increases or decreases the value
    private GreenfootImage image;
    private String statName;

    /**
     * Constructor for StatButton, sets and scales my image.
     * 
     * @param sp        The associated prisoner with the button.
     * @param statName  The name of the stat I am for.
     * @param increase  The value to increase the stat by.
     */
    public StatButton(SavedPrisoner sp, String statName, boolean increase) {
        this.sp = sp;
        this.statName = statName;
        this.increase = increase;

        image = new GreenfootImage("gameArrow.png");
        image.scale(50, 50);
        if(!increase) {
            image.mirrorHorizontally();
        }
        setImage(image);
    }

    /**
     * The act method of StatButton, on mouse click increase or decrease stat.
     */
    public void act() {
        if(Greenfoot.mouseClicked(this)) {
            switch(statName) {
                case "str":
                    sp.addStrength(increase);
                    break;
                case "intel":
                    sp.addIntel(increase);
                    break;
                case "spd":
                    sp.addSpeed(increase);
                    break;
            }
        }
    }
}
