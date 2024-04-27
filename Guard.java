import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Write a description of class Guard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Guard extends Person
{
    private boolean specialGuard;
    public Guard(int i) {
        super(i);
        personType = "guard";
    }
    public Guard() {
        this(-1);
        specialGuard = true;
    }
    
    public void doCurrentEvent() {
        if(specialGuard) return;
        super.doCurrentEvent();
    }
    
    public void act() {
        super.act();
        if(((MyWorld)getWorld()).isEscapeTime()) {
            // Fight intersecting MCs
            if (!inFight && !isDead) {
                MC mc = (MC)getOneIntersectingObject(MC.class);
                if (mc != null && !mc.isFighting() && !mc.isDead()) {
                    setInFight(mc, true);
                    mc.setInFight(this, true);
                    //curPath.clear();
                    noFights++;
                }
            }
        }
        if(((MyWorld)getWorld()).getSchedule().getCurrentEvent().equals("LIGHTS OUT")) {
            
            if (curRoom != null) { // Leaving a room
                curRoom.exitRoom(this, roomPosition);
                curRoom = null;
                roomPosition = -1;
            } 
            
            if(curNode.getIndex() == STARTING_NODE_INDEX && !isMoving() && isWalkingAround) {
                Action.walkAround(this, false);
            }
    
            if(curNode.getIndex() == WALKING_NODE_INDEX && !isMoving() && isWalkingAround) {
                Action.walkAround(this, true);
            }
        }
    }

    public ArrayList<Integer> getAccessories(){
        ArrayList<Integer> result = new ArrayList<>();
        result.add(6);
        return result; 
    }
}
