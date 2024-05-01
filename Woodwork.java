import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The woodwork room is where the woodworker job will go during job time.
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class Woodwork extends Room
{
    private final int JOB_NODE_1 = 113;
    private final int JOB_NODE_2 = 114;
    private final int JOB_NODE_3 = 116;
    
    private final int JOB_NODE_4 = 112;
    private final int JOB_NODE_5 = 115;
    private final int JOB_NODE_6 = 117;
    
    private SoundManager sm;
    
    /**
     * Constructor for Woodwork, no guards can enter.
     * 
     * @param prisonerPosIndexes    The indexes of prsioners for nodes.
     * @param dimensions            The width and height of the room.
     */
    public Woodwork (int[] prisonerPosIndexes, int[] dimensions) {
        super(prisonerPosIndexes, new int[]{}, dimensions);
    }
    
    /**
     * Do the effect of the Woodwork room, do woodworker job.
     * 
     * @param p     The person being affected.
     */
    public void doEffect (Person p) {
            //System.out.println(p.getCurNode().getIndex() + " " + p.getActCount());
        if (!p.isMoving() && p.getActCount() % 120 == 0) {
            if(p.getActCount() % 240 == 0) {
                sm.playSound("WoodCraft");
            }
            switch(p.getCurNode().getIndex()) {
                case JOB_NODE_1:
                    p.goToNode(JOB_NODE_2);
                    break;
                case JOB_NODE_2:
                case JOB_NODE_3:
                    p.goToNode(JOB_NODE_1);
                    break;
                case JOB_NODE_4:
                    p.goToNode(JOB_NODE_5);
                    break;
                case JOB_NODE_5:
                case JOB_NODE_6:
                    p.goToNode(JOB_NODE_4);
                    break;
            }
        }
    }
    
    /**
     * Actions that happen when a person is leaving.
     * 
     * @param p             The person leaving.
     * @param roomPositon   The node the person left.
     */
    public void exitRoom(Person p, int roomPosition) {
        if (p instanceof MC && Greenfoot.getRandomNumber(p.getChance()) == 0) {
            ((MC)p).giveItem(new Wood());
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
