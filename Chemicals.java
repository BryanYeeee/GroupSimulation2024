/**
 * The chemicals item class
 * 
 * @author Bryan Y 
 * @version April 2024
 */
public class Chemicals extends Item 
{
    /**
     * Constructor for objects of class Chemicals
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
