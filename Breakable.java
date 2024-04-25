import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Breakable here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Breakable extends Actor
{
    private boolean isBreaking;
    private SuperStatBar healthBar;
    
    private int maxHp;
    private int curHp;
    
    private int actCount;
    
    public Breakable(String img, int maxHp, int curHp)
    {
        setImage(new GreenfootImage(img));
        this.maxHp = maxHp;
        this.curHp = curHp;
    }
    
    public void addedToWorld(World w) {
        healthBar = new SuperStatBar(maxHp, curHp, this, 40, 6, -36, Color.GREEN, Color.RED, false, Color.BLACK, 2);
    }
    
    /**
     * Act - do whatever the Breakable wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
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
    
    public boolean isBroken() {
        return getWorld() == null;
    }
    
    public boolean isBreaking() {
        return isBreaking;
    }
    
    public void beginBreak() {
        isBreaking = true;
        actCount = 0;
        getWorld().addObject(healthBar, 0, 0);
    }
}
