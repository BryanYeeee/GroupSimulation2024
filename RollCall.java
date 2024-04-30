import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The roll call room is where the prisoners will go during roll call
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class RollCall extends Room
{
    
    public RollCall (int[] prisonerPosIndexes, int[] guardPosIndexes, int[] dimensions) {
        super(prisonerPosIndexes, guardPosIndexes, dimensions);
    }
    
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
    }
    
    public boolean checkEffectCondition (Person p) { // If person is at its assigned room position
        return p.getCurNode().getIndex() == p.getCurRoom().getPositionIndex(p, p.getRoomPosition());
    }
}
