import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The janitor closet is where the janitor job will go during job time.
 * It is also where the generator is located.
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class JanitorCloset extends Room
{
    /**
     * Constructor for JanitorCloset, no guards can enter.
     * 
     * @param prisonerPosIndexes    The indexes of prsioners for nodes.
     * @param dimensions            The width and height of the room.
     */
    public JanitorCloset (int[] prisonerPosIndexes, int[] dimensions) {
        super(prisonerPosIndexes, new int[]{}, dimensions);
    }

    /**
     * There is no special effect for a person being in this room.
     */
    public void doEffect (Person p) {
    }

    /**
     * Actions that happen when a person is leaving.
     * 
     * @param p             The person leaving.
     * @param roomPositon   The node the person left.
     */
    public void exitRoom(Person p, int roomPosition) {
        if (p instanceof MC && Greenfoot.getRandomNumber(p.getChance()) == 0) {
            ((MC)p).giveItem(new Chemicals());
        }
        super.exitRoom(p, roomPosition);
    }
    
    /**
     * Return if a person is at its assigned room position node.
     * 
     * @param p         The person to be checked.
     * @return boolean  Defaulted to be true, always will be at position.
     */
    public boolean checkEffectCondition (Person p) { // If person is at its assigned room position
        //return p.getCurNode().getIndex() == p.getCurRoom().getPositionIndex(p, p.getRoomPosition());
        return true;
    }
}
