import greenfoot.*;

/**
 * 
 * 
 * @author Ainson
 * @version April 2024
 */
public class Textbox extends Actor {
    private String statName;
    private StatSetter statSetter;

    public Textbox(String statName, int width, StatSetter statSetter) {
        this.statName = statName;
        this.statSetter = statSetter;

        GreenfootImage image = new GreenfootImage(width, 40);
        image.setColor(Color.WHITE);
        image.fill();
        image.setColor(Color.BLACK);
        image.drawRect(0, 0, width - 1, 49);
        updateText(image);

        setImage(image);
    }

    private void updateText(GreenfootImage image) {
        image.drawString(statName + ": " + statSetter.getValue(), 10, 30);
    }

    public void act() {
        GreenfootImage image = getImage();
        image.clear(); // Clear the image before updating
        image.setColor(Color.WHITE);
        image.fill();
        image.setColor(Color.BLACK);
        image.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);
        updateText(image); // Update the text
    }
}
