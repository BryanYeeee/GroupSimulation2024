import greenfoot.*;
/**
 * Write a description of class Metal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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

    
    public void useItem(MyWorld w, MC p) {
        int itemChoice = Greenfoot.getRandomNumber((int)p.getIntel());
        if(itemChoice < 20) {
            p.giveItem(new Weapon("hammer"));
            p.addStrength(5);
        } else if (itemChoice < 40) {
            p.giveItem(new Knife());
            p.addStrength(6);
        } else if (itemChoice < 60) {
            p.giveItem(new Weapon("crowbar"));
            p.addStrength(10);
        } else {
            p.giveItem(new Weapon("axe"));
            p.addStrength(18);
        }

        StatusBar.setUpdate(true);
    }
}
