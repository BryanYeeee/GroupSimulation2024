import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EventDisplay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EventDisplay extends SuperTextBox
{
    /**
     * Act - do whatever the EventDisplay wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public EventDisplay(String event){
        super(event,new Color (84,84,84), Color.WHITE,new Font("Calibri",true,false,14),true,100,8,Color.BLACK);
    }

    public void act()
    {
        // Add your action code here.
    }

    public void updateEventDisplay(String event){
        update(event);
    }
    
}
