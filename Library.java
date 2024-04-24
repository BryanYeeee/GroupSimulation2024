import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Library here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Library extends Room
{
    private static final int libraryCapacity = 2;
    private static int peopleInLibrary = 0;
    public Library (int[] prisonerPosIndexes, int[] dimensions) {
        super(prisonerPosIndexes, new int[]{}, dimensions);
        peopleInLibrary = 0;
    }
    
    public void doEffect (Person p) {
        //Do effect
        //System.out.println("callroll");
        if(p.getActCount() % 30 == 0) {
            p.addIntel(1);
        }
    }
    
    public static boolean canEnterLibrary() {
        return peopleInLibrary < libraryCapacity;
    }
    
    public static void addPeopleInLibrary() {
        peopleInLibrary++;
    }
    
    // if a guy dies before he enters the library and never enters, then the static variable will never deplete <bug
    public void exitRoom (Person p, int roomPosition) {
        peopleInLibrary--;
        super.exitRoom(p, roomPosition);
    }
    
    public boolean checkEffectCondition (Person p) { // If person is at its assigned room position
        return p.getCurNode().getIndex() == p.getCurRoom().getPositionIndex(p, p.getRoomPosition());
    }
}
