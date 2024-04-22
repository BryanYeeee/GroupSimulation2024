import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Underglow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
