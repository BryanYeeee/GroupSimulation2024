/**
 * Write a description of class Wood here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
        p.giveItem(new Ladder());
    }
}
