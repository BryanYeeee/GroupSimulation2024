/**
 * The food item class
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class Food extends Item 
{
    /**
     * Constructor for objects of class Food
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
