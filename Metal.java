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
        p.giveItem(new Sword());
        p.addStrength(100);
        StatusBar.setUpdate(true);
    }
}
