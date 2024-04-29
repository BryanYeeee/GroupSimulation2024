import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class JanitorCloset here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JanitorCloset extends Room
{

    public JanitorCloset (int[] prisonerPosIndexes, int[] dimensions) {
        super(prisonerPosIndexes, new int[]{}, dimensions);
    }

    public void doEffect (Person p) {
    }

    public void exitRoom(Person p, int roomPosition) {
        if (p instanceof MC && Greenfoot.getRandomNumber(p.getChance()) == 0) {
            ((MC)p).giveItem(new Chemicals());
        } else {
            super.exitRoom(p, roomPosition);
        }
    }

    public boolean checkEffectCondition (Person p) { // If person is at its assigned room position
        //return p.getCurNode().getIndex() == p.getCurRoom().getPositionIndex(p, p.getRoomPosition());
        return true;
    }
}
