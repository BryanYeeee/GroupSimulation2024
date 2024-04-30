import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The gym class is a type of room where prisoners will go to increase their strength
 * 
 * @author Bryan Y
 * @version April 2024
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
