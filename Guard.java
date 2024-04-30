import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Arrays;
import java.util.ArrayList;

/**
 * The guard subclass
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class Guard extends Person
{
    private boolean specialGuard;
    private static boolean guardsPoisoned;
    
    // For cutscene/intro done by Jamison H
    public Guard(int i, boolean inIntro){
        super(i, inIntro);
        GreenfootImage cutsceneImage = new GreenfootImage("images/guard/male_white/idle/D0.png");
        cutsceneImage.scale(120, 165);
        setImage(cutsceneImage);
        personType = "guard";
    }
    
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
    
    public static void poisonGuards() {
        guardsPoisoned = true;
    }
    
    public static void setGuardStats(MyWorld w) {
        if(guardsPoisoned) {
            for(Guard g : w.getGuards()) {
                g.setSpeed(1.2);
                // g.setStrength(g.getStrength()-2);
            }
        }
    }
    
    public void act() {
        super.act();
        if(!inIntro){
            if(((MyWorld)getWorld()).isEscapeTime()) {
                if(curNode.getIndex() == STARTING_NODE_INDEX && !isMoving() && isWalkingAround) {
                    Action.walkAround(this, false);
                }
        
                if(curNode.getIndex() == WALKING_NODE_INDEX && !isMoving() && isWalkingAround) {
                    Action.walkAround(this, true);
                }
              
                // Fight intersecting MCs
                if (!inFight && !isDead) {
                    MC mc = (MC)getOneIntersectingObject(MC.class);
                    if(mc != null) {
                    boolean dodged = mc.getSpeed() > 1.8 && Greenfoot.getRandomNumber(2)==0;
                    if (mc != null && !mc.isFighting() && !mc.isDead() && !dodged) {
                        setInFight(mc, true);
                        mc.setInFight(this, true);
                        noFights++;
                    }
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
    }

    public ArrayList<Integer> getAccessories(){
        ArrayList<Integer> result = new ArrayList<>();
        result.add(6);
        return result; 
    }
    
    public Accessory getAccessory(){
        return (Accessory) getOneIntersectingObject(Accessory.class);
    }
    
}
