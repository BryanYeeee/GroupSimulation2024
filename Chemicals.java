/**
 * Write a description of class Chemicals here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Chemicals extends Item 
{
    /**
     * Constructor for objects of class Metal
     */
    public Chemicals()
    {
        super("chemicals");
        isMaterial = true;
    }

    
    public void useItem(MyWorld w, MC p) {
        if(p.getIntel() < 40) {
            // potion?
        } else if(p.getIntel() >= 40 ) {
            p.giveItem(new Bomb());
        } 
    }
}
