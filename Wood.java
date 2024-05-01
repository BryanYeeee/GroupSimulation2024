/**
 * The wood item class
 * 
 * @author Bryan Y.
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

    /**
     * Activate the item's effects, craft a shovel.
     * 
     * @param w     The simulation world the MC exists in.
     * @param p     The MC using the item.
     */
    public void useItem(MyWorld w, MC p) {
        p.giveItem(new Shovel());
    }
}
