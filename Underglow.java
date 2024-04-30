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
    public Underglow(Actor origin) {
        GreenfootImage image = new GreenfootImage(40, 20);
        image.setColor(new Color(255, 255, 255, 128));
        image.fillOval(0, 0, image.getWidth(), image.getHeight());
        setImage(image);
        this.origin = origin;
    }
    public void act(){
        setLocation(origin.getX(),origin.getY()+20);
    }
}
