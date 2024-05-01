import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The gym class is a type of room where prisoners will go to increase their strength.
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class Gym extends Room
{
    /**
     * Constructor for DiningHall
     * 
     * @param prisonerPosIndexes    The indexes of prsioners for nodes.
     * @param guardPosIndexes       The indexes of guards for nodes.
     * @param dimensions            The width and height of the room.
     */
    public Gym (int[] prisonerPosIndexes, int[] guardPosIndexes, int[] dimensions) {
        super(prisonerPosIndexes, guardPosIndexes, dimensions);
    }
    
    /**
     * Do the effect of the Gym room, adding strength every 2 seconds.
     * 
     * @param p     The person being affected.
     */
    public void doEffect (Person p) {
        //Do effect
        //System.out.println("eats");
        if(p.getActCount() % 120 == 0) {
            p.addStrength(1);
        }
    }
    
    /**
     * Return if a person is at its assigned room position node.
     * 
     * @param p         The person to be checked.
     * @return boolean  True if at the position, false if not.
     */
    public boolean checkEffectCondition (Person p) {
        return p.getCurNode().getIndex() == p.getCurRoom().getPositionIndex(p, p.getRoomPosition());
    }
}
