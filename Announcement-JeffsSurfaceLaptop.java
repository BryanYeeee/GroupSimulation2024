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
    public Announcement(String t, int acts){
        super(t,acts);
        bannerBackgroundColor = new Color (84,84,84);
        bannerFont = new Font ("Arial", false, false, 26);
    }
    public void act()
    {
        super.act();
    }
}
