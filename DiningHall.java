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
    public DiningHall (int[] prisonerPosIndexes, int[] guardPosIndexes, int[] dimensions) {
        super(prisonerPosIndexes, guardPosIndexes, dimensions);
    }
    
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
    
    public boolean isCooking(Person p) {
       // System.out.println(p instanceof Prisoner && ((Prisoner)p).getJob().equals("Cook") && 
           //(((MyWorld)getWorld()).getSchedule().getCurrentEvent().equals("JOB TIME")||((MyWorld)getWorld()).getSchedule().getCurrentEvent().equals("FREE TIME")));
        return p.getRoomPosition() == -2 || (p instanceof Prisoner && ((Prisoner)p).getJob().equals("Cook") && 
           (((MyWorld)getWorld()).getSchedule().getCurrentEvent().equals("JOB TIME")||((MyWorld)getWorld()).getSchedule().getCurrentEvent().equals("FREE TIME")));
    }
    
    public int enterRoom (Person p) {
        if(p instanceof MC && isCooking(p)) {
            ((MC)p).setAction("Poisoning Guard's Food");
            Guard.poisonGuards();
        }
        return isCooking(p) ? -2 : super.enterRoom(p);
    }
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
    
    public boolean checkEffectCondition (Person p) { // If person is at its assigned room position
        return isCooking(p) ? true : p.getCurNode().getIndex() == p.getCurRoom().getPositionIndex(p, p.getRoomPosition());
    }
}
