import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Alarm extends Actor
{
    private GreenfootImage image;
    private int transparency = 0;
    private boolean increasing = true;
    private int maxTransparency = 0;
    private int minTransparency = 0;
    private int flashSpeed = 10;  // Change in transparency per act cycle

    public Alarm() {
         this(80,0,2);
    }
    
    public Alarm(int maxTransparency, int minTransparency, int flashSpeed) {
        this.maxTransparency = maxTransparency;
        this.minTransparency = minTransparency;
        this.flashSpeed = flashSpeed;
        
        // Initialize a small placeholder image initially
        image = new GreenfootImage(1, 1);
        image.setColor(Color.RED);
        image.fill();
        transparency = minTransparency;
        image.setTransparency(transparency);
        setImage(image);

        
    }

    // This method is called when the actor is added to the world
    protected void addedToWorld(World world) {
        // Resize the image to cover the whole world
        image.scale(world.getWidth(), world.getHeight());
        setImage(image);
    }

    public void act() 
    {
        // Adjust the transparency to create a flashing effect
        if (increasing) {
            transparency += flashSpeed;
            if (transparency >= maxTransparency) {
                transparency = maxTransparency;
                increasing = false;
            }
        } else {
            transparency -= flashSpeed;
            if (transparency <= minTransparency) {
                transparency = minTransparency;
                increasing = true;
            }
        }
        image.setTransparency(transparency);
    }
}
