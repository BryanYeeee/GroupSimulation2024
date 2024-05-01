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

    /**
     * Activate the item's effects, craft a bomb if the MC has sufficient intelligence.
     * 
     * @param w     The simulation world the MC exists in.
     * @param p     The MC using the item.
     */
    public void useItem(MyWorld w, MC p) {
        if(p.getIntel() < 40) {
            // potion?
        } else if(p.getIntel() >= 40 ) {
            p.giveItem(new Bomb());
        } 
    }
}
