import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The breakable class are objects that the prisoners can remove from the world such as breakable walls or doors.
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class Breakable extends Actor
{
    private boolean isBreaking;
    private SuperStatBar healthBar;
    
    private int maxHp;
    private int curHp;
    
    private int actCount;
    /**
     * Constructor for Breakable, sets image and values.
     * 
     * @param img   The file name of the image.
     * @param maxHP The max health points of the breakable object.
     * @param curHp The current health points of the breakable object.
     */
    public Breakable(String img, int maxHp, int curHp)
    {
        setImage(new GreenfootImage(img));
        this.maxHp = maxHp;
        this.curHp = curHp;
    }
    
    /**
     * Once added to the world, create a healthbar.
     * 
     * @param w     The world being added to.
     */
    public void addedToWorld(World w) {
        healthBar = new SuperStatBar(maxHp, curHp, this, 40, 6, -36, Color.GREEN, Color.RED, false, Color.BLACK, 2);
    }

    /**
     * The act method of Breakable, updates the health when a Breakable object is being broken.
     * Remove healthbar if currentHP is 0.
     */
    public void act()
    {
        if (isBreaking && actCount % 5 == 0) {
            curHp--;
            healthBar.update(curHp);
            if(curHp<=0) {
                getWorld().removeObject(healthBar);
                getWorld().removeObject(this);
            }
        }
        actCount++;
    }
    
    /**
     * Return whether the Breakable object is broken.
     * 
     * @return boolean  True if broken (out of world), false if not (still in world).
     */
    public boolean isBroken() {
        return getWorld() == null;
    }
    
    /**
     * Return whether the Breakable object is being broken.
     * 
     * @return boolean  True is being broken, false if not.
     */
    public boolean isBreaking() {
        return isBreaking;
    }
    
    /**
     * Start the breaking of the Breakable object, add a healthbar.
     */
    public void beginBreak() {
        isBreaking = true;
        actCount = 0;
        getWorld().addObject(healthBar, 0, 0);
    }
}
