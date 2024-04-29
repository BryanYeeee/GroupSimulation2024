/**
 * Write a description of class Food here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Food extends Item 
{
    /**
     * Constructor for objects of class Ladder
     */
    public Food()
    {
        super("food");
        isMaterial = true;
    }

    
    public void useItem(MyWorld w, MC p) {
        p.addStrength(10);
        p.setSpeed(p.getSpeed()+0.5);
        isMaterial = false;
    }
}
