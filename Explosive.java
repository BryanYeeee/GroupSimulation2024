import greenfoot.*;  

public class Explosive extends Actor {
    private GreenfootImage initialImage;
    private GreenfootImage[] images;  
    private int imageIndex;           
    private int explosionActs;        
    private int primeActs;
    private int size;
    private int updateFrequency;
    public Explosive() {
        initialImage =new GreenfootImage("images/items/bomb.png");
        images = new GreenfootImage[10];
        for (int i = 0; i < images.length; i++) {
            images[i] = new GreenfootImage("images/explosion/"+i + ".png"); 
        }
        setImage(initialImage);  
        imageIndex = 0;
        explosionActs = 200;
        primeActs=300;
                updateFrequency = explosionActs / images.length;  // Calculate update frequency based on total duration and number of images

    }

    public void act() {
        if(primeActs > 0){
            primeActs--;

        }
        else{
            if (explosionActs > 0) {
                if(explosionActs % updateFrequency == 0){
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
