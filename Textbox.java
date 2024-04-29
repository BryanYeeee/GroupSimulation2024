import greenfoot.*;

/**
 * 
 * 
 * @author Ainson
 * @version April 2024
 */
public class Textbox extends Actor {
    private SavedPrisoner sp;
    private String statName;

    public Textbox(SavedPrisoner sp, String statName, int width) {
        this.statName = statName;
        this.sp = sp;

        GreenfootImage image = new GreenfootImage(width, 40);
        image.setColor(Color.WHITE);
        image.fill();
        image.setColor(Color.BLACK);
        image.drawRect(0, 0, width - 1, 49);
        updateText(image);

        setImage(image);
    }

    private void updateText(GreenfootImage image) {
        switch(statName) {
            case "str":
                image.drawString(statName + ": " + sp.getStrength(), 10, 30);
                break;
            case "intel":
                image.drawString(statName + ": " + sp.getIntel(), 10, 30);
                break;
            case "spd":
                image.drawString(statName + ": " + sp.getSpeed(), 10, 30);
                break;
        }
            
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
