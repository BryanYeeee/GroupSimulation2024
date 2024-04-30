import greenfoot.*;

/**
 * StatButton class that is used to generate arrow images
 * that can edit a stat by decreasing or increasing
 * 
 * @author Bryan Y and Ainson Z
 * @version April 2024
 */
public class StatButton extends Button {
    private SavedPrisoner sp;
    private boolean increase; // Indicates whether the button increases or decreases the value
    private GreenfootImage image;
    private String statName;

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
     * Act method
     * On mouse click increase or decrease stat
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
