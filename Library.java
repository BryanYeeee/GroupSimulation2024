import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The library room is where prisoners will go to increase their intelligence stat.
 * 
 * @author Bryan Y 
 * @version April 2024
 */
public class Library extends Room
{
    private static final int libraryCapacity = 2;
    private static int peopleInLibrary = 0;
    /**
     * Constructor for Library, no guards can enter.
     * 
     * @param prisonerPosIndexes    The indexes of prsioners for nodes.
     * @param dimensions            The width and height of the room.
     */
    public Library (int[] prisonerPosIndexes, int[] dimensions) {
        super(prisonerPosIndexes, new int[]{}, dimensions);
        peopleInLibrary = 0;
    }

    /**
     * Do the effect of the Library room, adding intelligence every 0.5 seconds.
     * 
     * @param p     The person being affected.
     */
    public void doEffect (Person p) {
        //Do effect
        //System.out.println("callroll");
        if(p.getActCount() % 30 == 0) {
            p.addIntel(1);
        }
    }

    /**
     * Return if someone can enter the library, depends on current and max capacity of people.
     * 
     * @return boolean  True if able to enter, false if not.
     */
    public static boolean canEnterLibrary() {
        return peopleInLibrary < libraryCapacity;
    }

    /**
     * Increment the tracker for number of people in the Library.
     */
    public static void addPeopleInLibrary() {
        peopleInLibrary++;
    }

    /**
     * Actions that happen when a person is leaving.
     * If a person dies before he enters the library and never enters, then the static variable will never deplete <bug.
     */
    public void exitRoom (Person p, int roomPosition) {
        peopleInLibrary--;
        super.exitRoom(p, roomPosition);
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
