import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The roll call room is where the prisoners will go during roll call.
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class RollCall extends Room
{
    private SoundManager sm;
    /**
     * Constructor for RollCall
     * 
     * @param prisonerPosIndexes    The indexes of prsioners for nodes.
     * @param guardPosIndexes       The indexes of guards for nodes.
     * @param dimensions            The width and height of the room.
     */
    public RollCall (int[] prisonerPosIndexes, int[] guardPosIndexes, int[] dimensions) {
        super(prisonerPosIndexes, guardPosIndexes, dimensions);
    }
    
    /**
     * Do the effect of the RollCall room, make prisoners line up and set their rotation.
     * 
     * @param p     The person being affected.
     */
    public void doEffect (Person p) {
        //Face upward when standing in rollcall position
        if(p instanceof Prisoner && !p.isMoving()) {
            p.setDirection(-1);
            p.setMovingVertical(true);
        }
        if(p instanceof Guard && !p.isMoving()) {
            p.setDirection(1);
            p.setMovingVertical(true);
        }
        
        /*
        if(p.getActCount() % 480 == 0) {
            sm.playSound("RollCall");
        }
        */
    }
    
    /**
     * Return if a person is at its assigned room position node.
     * 
     * @param p         The person to be checked.
     * @return boolean  True if at the position, false if not.
     */
    public boolean checkEffectCondition (Person p) { // If person is at its assigned room position
        return p.getCurNode().getIndex() == p.getCurRoom().getPositionIndex(p, p.getRoomPosition());
    }
}
