import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The metal work room is where the metalworker job will go to during job time
 * 
 * @author Bryan Y 
 * @version April 2024
 */
public class Metalwork extends Room
{
    private final int JOB_NODE_1 = 119;
    private final int JOB_NODE_2 = 123;
    private final int JOB_NODE_3 = 121;

    private final int JOB_NODE_4 = 120;
    private final int JOB_NODE_5 = 124;
    private final int JOB_NODE_6 = 122;
    
    private SoundManager sm;

    public Metalwork (int[] prisonerPosIndexes, int[] dimensions) {
        super(prisonerPosIndexes, new int[]{}, dimensions);
    }

    public void doEffect (Person p) {
        if (!p.isMoving() && p.getActCount() % 120 == 0) {
            if(p.getActCount() % 300 == 0) {
                sm.playSound("MetalCraft");
            }
            //System.out.println(p.getCurNode().getIndex());
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

    public void exitRoom(Person p, int roomPosition) {
        if (p instanceof MC && Greenfoot.getRandomNumber(p.getChance()) == 0) {
            ((MC)p).giveItem(new Metal());
        }            
        super.exitRoom(p, roomPosition);
        
    }

    public boolean checkEffectCondition (Person p) { // If person is at its assigned room position
        //return p.getCurNode().getIndex() == p.getCurRoom().getPositionIndex(p, p.getRoomPosition());
        return true;
    }
}
