import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The vehicle class is used for the drive away escape method.
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class Vehicle extends Actor
{
    private MC linkedMC;
    
    /**
     * Constructor for Vehicle
     * 
     * @param img   The image file name.
     */
    public Vehicle(String img) {
        setImage(img);
    }
    
    /**
     * The act method of Vehicle, store and set the location of MCs that touch the Vehicle.
     */
    public void act()
    {
        if(linkedMC != null) {
            // Follow the linked MC position
            setLocation(linkedMC.getX()-4, linkedMC.getY()-Person.SPRITE_OFFSET-21);
            return;
        }
        // When intersecting an MC, store the mc
        MC mc = (MC)getOneObjectAtOffset(4,21,MC.class);
        if(mc != null && !mc.isMoving()) {
            linkedMC = mc;
        }
    }
}
