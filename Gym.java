import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Gym here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Gym extends Room
{
    
    public Gym (int[] prisonerPosIndexes, int[] guardPosIndexes, int[] dimensions) {
        super(prisonerPosIndexes, guardPosIndexes, dimensions);
    }
    
    public void doEffect (Person p) {
        //Do effect
        //System.out.println("eats");
        if(p.getActCount() % 120 == 0) {
            p.addStrength(1);
        }
    }
    
    public boolean checkEffectCondition (Person p) { // If person is at its assigned room position
        return p.getCurNode().getIndex() == p.getCurRoom().getPositionIndex(p, p.getRoomPosition());
    }
}
