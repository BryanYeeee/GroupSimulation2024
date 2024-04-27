import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Vehicle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Vehicle extends Actor
{
    private MC linkedMC;
    
    public Vehicle(String img) {
        setImage(img);
    }
    
    public void act()
    {
        if(linkedMC != null) {
            setLocation(linkedMC.getX()-4, linkedMC.getY()-Person.SPRITE_OFFSET-21);
            return;
        }
        MC mc = (MC)getOneObjectAtOffset(4,21,MC.class);
        if(mc != null && !mc.isMoving()) {
            linkedMC = mc;
        }
    }
}
