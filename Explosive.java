import greenfoot.*;
import java.util.Random;  // Import the Random class for generating random numbers
/**
 * An explosion effect for the bomb in the exploding a wall escape.
 * 
 * @author Jeff G
 * @version April 2024
 */
public class Explosive extends Actor {
    private GreenfootImage initialImage;
    private GreenfootImage[] images;  
    private int imageIndex;           
    private int explosionActs;        
    private int primeActs;
    private int size = 100;
    private int updateFrequency;
    private Random random;  // Add a Random object for generating shake

    /**
     * Constructor for Explosive, set the image and values for animation and duration.
     */
    public Explosive() {
        initialImage = new GreenfootImage("images/items/bomb.png");
        initialImage.scale(50,50);
        images = new GreenfootImage[10];

        for (int i = 0; i < images.length; i++) {
            images[i] = new GreenfootImage("images/explosion/" + i + ".png");
            images[i].scale((int) Math.round(size * 8 / 5), size);
        }
        setImage(initialImage);
        imageIndex = 0;
        explosionActs = 60;
        primeActs = 300;
        updateFrequency = explosionActs / images.length;  // Calculate update frequency based on total duration and number of images
        random = new Random();  // Initialize the Random object
    }

    /**
     * The act method of Explosive, sets location and frame of animation.
     */
    public void act() {
        if (primeActs > 0) {
            primeActs--;
            if(primeActs < 120){
                if (primeActs % 4 == 0) {  // Even acts
                setLocation(getX() - 2, getY());  // Move left
            } else if (primeActs % 4 == 2){  // Odd acts
                setLocation(getX() + 2, getY());  // Move right
            }
            }
            

        } else {
            if(explosionActs==60){
                setLocation(getX(),getY()-37);
            }
            if (explosionActs > 0) {
                if (explosionActs % updateFrequency == 0) {
                    setImage(images[imageIndex]);  // Update to the next image
                    imageIndex = (imageIndex + 1) % images.length; // Cycle through images
                }
                explosionActs--;  // Decrease the acts remaining
            } else {
                getWorld().removeObject(this); // Remove the explosion from the world when done
            }
        }
    }
}
