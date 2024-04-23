import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DiningHall here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
        return isCooking(p) ? -2 : super.enterRoom(p);
    }
    public void exitRoom (Person p, int roomPosition) {
        p.showHealthBar(false);
        if (roomPosition >= 0) super.exitRoom(p, roomPosition);
        if(p instanceof MC && ((MC)p).getSpecialty().equals("Thief") && Greenfoot.getRandomNumber(3) == 0) {
            ((MC)p).giveItem(new Fork());
        }
    }
    
    public boolean checkEffectCondition (Person p) { // If person is at its assigned room position
        return isCooking(p) ? true : p.getCurNode().getIndex() == p.getCurRoom().getPositionIndex(p, p.getRoomPosition());
    }
}
