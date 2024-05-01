import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The DiningHall is a type of room where prisoners heal when they eat here. 
 * It is also the place where the cook will go during job time
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class DiningHall extends Room
{
    private final int JOB_NODE_1 = 107;
    private final int JOB_NODE_2 = 108;
    private final int HEAL_RATE = 3;
    /**
     * Constructor for DiningHall
     * 
     * @param prisonerPosIndexes    The indexes of prsioners for nodes.
     * @param guardPosIndexes       The indexes of guards for nodes.
     * @param dimensions            The width and height of the room.
     */
    public DiningHall (int[] prisonerPosIndexes, int[] guardPosIndexes, int[] dimensions) {
        super(prisonerPosIndexes, guardPosIndexes, dimensions);
    }
    
    /**
     * Do the effect of the DiningHall room, healing prisoners or do a cook job.
     * 
     * @param p     The person being affected.
     */
    public void doEffect (Person p) {
        //Do effect
        //System.out.println("eats");
        if(p.getRoomPosition() == -2 && ((MyWorld)getWorld()).getSchedule().getCurrentEvent().equals("DINING HALL")) {
            if(p instanceof MC) ((MC)p).setAction("Going to Dining Hall");
            p.setRoomPosition(-1);
            p.setRoomPosition(this.enterRoom(p));
        }
        if (isCooking(p) && !p.isMoving() && p.getActCount() % 120 == 0) {
            switch(p.getCurNode().getIndex()) {
                case JOB_NODE_1:
                    p.goToNode(JOB_NODE_2);
                    break;
                case JOB_NODE_2:
                    p.goToNode(JOB_NODE_1);
                    break;
            }
        }
        if(!isCooking(p)) {
            if(p.getActCount() % 15 == 0){
                p.healHp(5);
                p.showHealthBar(p.getHealth() < p.getMaxHealth());
            }
        }
    }
    
    /**
     * Return whether this person is cooking.
     * 
     * @param p         The person who might be cooking.
     * @return boolean  True if the person is cooking, false if not.
     */
    public boolean isCooking(Person p) {
       // System.out.println(p instanceof Prisoner && ((Prisoner)p).getJob().equals("Cook") && 
           //(((MyWorld)getWorld()).getSchedule().getCurrentEvent().equals("JOB TIME")||((MyWorld)getWorld()).getSchedule().getCurrentEvent().equals("FREE TIME")));
        return p.getRoomPosition() == -2 || (p instanceof Prisoner && ((Prisoner)p).getJob().equals("Cook") && 
           (((MyWorld)getWorld()).getSchedule().getCurrentEvent().equals("JOB TIME")||((MyWorld)getWorld()).getSchedule().getCurrentEvent().equals("FREE TIME")));
    }
    
    /**
     * Return a value to determine the action of a Person when they enter.
     * 
     * @param p     The entering person.
     * @return int  -2  if cooking, else return the roomPosition node.
     */
    public int enterRoom (Person p) {
        if(p instanceof MC && isCooking(p)) {
            ((MC)p).setAction("Poisoning Guard's Food");
            Guard.poisonGuards();
        }
        return isCooking(p) ? -2 : super.enterRoom(p);
    }
    
    /**
     * Actions that happen when a person is leaving.
     * 
     * @param p             The person leaving.
     * @param roomPositon   The node the person left.
     */
    public void exitRoom (Person p, int roomPosition) {
        p.showHealthBar(false);
        if (roomPosition >= 0) super.exitRoom(p, roomPosition);
        
        // MAKE 1 to 3, ITS 1 FOR TESTING TO MAKE GARUNTEE
        if(p instanceof MC && ((MC)p).getSpecialty().equals("Thief") && Greenfoot.getRandomNumber(p.getChance()) == 0) {
            ((MC)p).giveItem(new Fork());
        }
        if(p instanceof MC && ((MC)p).getSpecialty().equals("Scientist") && roomPosition == -2 && Greenfoot.getRandomNumber(p.getChance()) == 0) {
            ((MC)p).giveItem(new Food());
        }
    }
    
    /**
     * Return if a person is at its assigned room position node.
     * 
     * @param p         The person to be checked.
     * @return boolean  True if at the position, false if not.
     */
    public boolean checkEffectCondition (Person p) { 
        return isCooking(p) ? true : p.getCurNode().getIndex() == p.getCurRoom().getPositionIndex(p, p.getRoomPosition());
    }
}
