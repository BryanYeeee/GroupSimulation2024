import greenfoot.*;
/**
 * The metal item class
 * 
 * @author Bryan Y 
 * @version April 2024
 */
public class Metal extends Item 
{
    /**
     * Constructor for objects of class Metal
     */
    public Metal()
    {
        super("metal");
        isMaterial = true;
    }
    
    /**
     * Activate the item's effects, craft a weapon depending on the intelligence of the MC using the item.
     * 
     * @param w     The simulation world the MC exists in.
     * @param p     The MC using the item.
     */
    public void useItem(MyWorld w, MC p) {
        int itemChoice = Greenfoot.getRandomNumber((int)p.getIntel());
        if(itemChoice < 50) {
            p.giveItem(new Knife());
            p.addStrength(6);
        } else if (itemChoice < 60) {
            p.giveItem(new Weapon("crowbar"));
            p.addStrength(13);
        } else {
            p.giveItem(new Weapon("axe"));
            p.addStrength(20);
        }

        StatusBar.setUpdate(true);
    }
}
