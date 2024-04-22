import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RollCall here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
