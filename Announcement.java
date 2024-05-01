import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Announcements class is a class at the top of the screen that displays the prison announcements during the main simulation
 * 
 * @author Jeff G
 * @version April 2024
 */
public class Announcement extends Banner
{
    private int actsLeft;
    private static Color announcementBackgroundColor = new Color (84,84,84);

    /**
     * Constructor for Annoucement
     * 
     * @param text      The displayed text.
     * @param seconds   The duration of display.
     */
    public Announcement(String text,double seconds){
        super(text, seconds, "announcement", announcementBackgroundColor, Color.WHITE); 
        secondsLeft = seconds;
    }
    /**
     * The act method of Annoucement
     */
    public void act()
    {
        super.act();
    }
}
