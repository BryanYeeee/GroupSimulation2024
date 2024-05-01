import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The event display is the textbox at the top left of the main simulation that shows the current event based on the schedule
 * 
 * @author Jeff G 
 * @version April 2024
 */
public class EventDisplay extends SuperTextBox
{
    /**
     * Constructor of EventDisplay.
     * 
     * @param event     The event to be displayed.
     */
    public EventDisplay(String event){
        super(event,new Color (84,84,84), Color.WHITE,new Font("Calibri",true,false,14),true,100,8,Color.BLACK);
    }

    /**
     * Update the display with an event.
     * 
     * @param event     The updated event.
     */
    public void updateEventDisplay(String event){
        update(event);
    }
    
}
