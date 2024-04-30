/**
 * Write a description of class Wood here.
 * 
 * @author (your name) 
 * @version April 2024
 */
public class Wood extends Item 
{
    /**
     * Constructor for objects of class Wood
     */
    public Wood()
    {
        super("wood");
        isMaterial = true;
    }

    public void useItem(MyWorld w, MC p) {
        p.giveItem(new Shovel());
    }
}
