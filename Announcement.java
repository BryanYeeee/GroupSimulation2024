import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Announcement here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Announcement extends Banner
{
    /**
     * Act - do whatever the Announcement wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    

    private int actsLeft;
    private static Color announcementBackgroundColor = new Color (84,84,84);

    public Announcement(String text,double seconds){
        super(text, seconds, "announcement", announcementBackgroundColor, Color.WHITE); 
        secondsLeft = seconds;
    }
    public void act()
    {
        super.act();
    }
}
