import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The underglow class follows the main prisoners to differentiate them from the other prisoners
 * 
 * @author Jeff G
 * @version April 2024
 */
public class Underglow extends SuperSmoothMover
{
    private Actor origin;
    /**
     * Constructor for Underglow
     * 
     * @param origin    The actor that uses this underglow
     * @param color     The color of the underglow
     */
    public Underglow(Actor origin, Color color) {
        GreenfootImage image = new GreenfootImage(40, 20);
        image.setColor(color);
        image.setTransparency(200);
        image.fillOval(0, 0, image.getWidth(), image.getHeight());
        setImage(image);
        this.origin = origin;
    }
    
    /**
     * The act method of Underglow, set my location based on my associated actor.
     */
    public void act(){
        setLocation(origin.getX(),origin.getY()+20);
    }
}
