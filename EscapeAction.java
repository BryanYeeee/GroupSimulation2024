import java.util.ArrayList;
/**
 * EscapeAction is the same as action, but mainly for the methods of escape. 
 * This subclass was mainly created for organization purposes.
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class EscapeAction extends Action 
{
    private final static int BREAK_WALL_NODE = 71;
    private final static int BREAK_WALL_ESCAPE_NODE = 138;
    
    private final static int KEYCARD_GUARD_NODE = 110;
    private final static int VEHICLE_DOOR_NODE = 139;
    private final static int CAR_NODE = 146;
    private final static int CAR_ESCAPE_NODE = 147;
    
    private final static int GENERATOR_NODE = 142;
    private final static int CUT_FENCE_NODE = 150;
    private final static int CUT_FENCE_ESCAPE_NODE = 151;
    
    private final static int HOLE_ENTER_NODE = 149;
    private final static int HOLE_EXIT_NODE = 148;
    private final static int HOLE_ESCAPE_NODE = 152;
    
    private final static int EXPLOSION_NODE = 143;
    private final static int EXPLOSION_STEP_BACK_NODE = 108;
    private final static int EXPLOSION_ESCAPE_NODE = 144;
    
    private static SoundManager sm;
    
    /**
     * Return whether an MC can proceed to the next step of the breaking wall escape.
     * 
     * @param w         The simulation world the MC is in.
     * @param mc        The main character doing the escape.
     * @param followers The followers of the MC that will follow along the MC's route.
     * @return boolean  True if MC can proceed to the next step, and do that step, false if the requirements are not met.
     */
    public static boolean breakWall(MyWorld w, MC mc, MC[] followers, int step) {
        switch(step) {
            case 0:
                mc.setAction("Going to Break a Wall");
                if(mc.getCurNode().getIndex() == BREAK_WALL_NODE) return true;
                if(!mc.isMoving()) {
                    mc.goToNode(BREAK_WALL_NODE);
                }
                break;
            case 1:
                mc.setAction("Breaking The Wall");
                if(w.getBreakable(0).isBroken()) return true;
                if(!w.getBreakable(0).isBreaking()) {
                    mc.setDirection(-1);
                    mc.setMovingVertical(true);
                    w.getBreakable(0).beginBreak();
                }
                break;
            case 2:
                sm.playSound("WallBreak");
                mc.setAction("Escaping");
                if(mc.getCurNode().getIndex() == BREAK_WALL_ESCAPE_NODE) {
                    
                    return true;
                }
                if(!mc.isMoving()) {
                    mc.goToNode(BREAK_WALL_ESCAPE_NODE);
                }
                for(MC follower : followers) {
                    follower.setAction("Escaping");
                    if(!follower.isMoving()) {
                        follower.goToNode(BREAK_WALL_ESCAPE_NODE);
                    }
                }
                break;

        }
        return false;
    }
    /**
     * Return whether an MC can proceed to the next step of the car escape.
     * 
     * @param w         The simulation world the MC is in.
     * @param mc        The main character doing the escape.
     * @param followers The followers of the MC that will follow along the MC's route.
     * @return boolean  True if MC can proceed to the next step, and do that step, false if the requirements are not met.
     */
    public static boolean driveCar(MyWorld w, MC mc, MC[] followers, int step) {
        switch(step) {
            case 0:
                mc.setAction("Going to Steal a Keycard");
                if(mc.getCurNode().getIndex() == KEYCARD_GUARD_NODE) return true;
                if(!mc.isMoving()) {
                    mc.goToNode(KEYCARD_GUARD_NODE);
                }
                break;
            case 1:
                mc.giveItem(new Keycard());
                return true;
            case 2:
                mc.setAction("Going to The Exit Door");
                if(mc.getCurNode().getIndex() == VEHICLE_DOOR_NODE) return true;
                if(!mc.isMoving()) {
                    mc.goToNode(VEHICLE_DOOR_NODE);
                }
                break;
            case 3:
                mc.setAction("Opening The Exit Door");
                if(w.getBreakable(1).isBroken()) return true;
                if(!w.getBreakable(1).isBreaking()) {
                    w.getBreakable(1).beginBreak();
                }
                break;
            case 4:
                mc.setAction("Going to The Car");
                if(mc.getCurNode().getIndex() == CAR_NODE) {
                    mc.setSpeed(1.6);
                    boolean followersDone = true;
                    for(MC follower : followers) {
                        if(follower.getCurNode().getIndex() != CAR_NODE) {
                            follower.setAction("Going to The Car");
                            if(!follower.isMoving()) {
                                follower.goToNode(CAR_NODE);
                            }
                            followersDone = false;
                        } else {
                            if(!follower.isMoving()) follower.setSpeed(1.6);
                        }
                    }
                    return followersDone;
                }
                if(!mc.isMoving()) {
                    sm.playSound("DoorOpen");
                    mc.goToNode(CAR_NODE);
                }
                break;
            case 5:
                sm.playSound("CarStart");
                mc.setAction("Escaping");
                if(mc.getCurNode().getIndex() == CAR_ESCAPE_NODE) {
                    return true;
                }
                if(!mc.isMoving() && mc.getActCount() % 25 == 0) {
                    mc.goToNode(CAR_ESCAPE_NODE);
                }
                for(MC follower : followers) {
                    follower.setAction("Escaping");
                    if(follower.getCurNode().getIndex() != CAR_ESCAPE_NODE && !follower.isMoving() && mc.getActCount() % 25 == 0) {
                        follower.goToNode(CAR_ESCAPE_NODE);
                    }
                }
                break;
        }
        return false;
    }
    
    /**
     * Return whether an MC can proceed to the next step of the exploding wall escape.
     * 
     * @param w         The simulation world the MC is in.
     * @param mc        The main character doing the escape.
     * @param followers The followers of the MC that will follow along the MC's route.
     * @return boolean  True if MC can proceed to the next step, and do that step, false if the requirements are not met.
     */
    public static boolean explodeWall(MyWorld w, MC mc, MC[] followers, int step) {
        switch(step) {
            case 0:
                mc.setAction("Going to Explode a Wall");
                if(mc.getCurNode().getIndex() == EXPLOSION_NODE) return true;
                if(!mc.isMoving()) {
                    mc.goToNode(EXPLOSION_NODE);
                }
                break;
            case 1:
                w.addObject(new Explosive(), 184, 143);
                return true;
            case 2:
                //gtfo
                if(mc.getCurNode().getIndex() == EXPLOSION_STEP_BACK_NODE) return true;
                if(!mc.isMoving() && mc.getActCount() % 20 == 0) mc.goToNode(EXPLOSION_STEP_BACK_NODE);
                break;
            case 3: // MAKE BOMB DO THIS
                if(w.getBreakable(2).isBroken()) {
                    sm.playSound("bomb");
                    return true;
                }
                if(!w.getBreakable(2).isBreaking()) {
                    w.getBreakable(2).beginBreak();
                }
                break;
            case 4:
                mc.setAction("Escaping");
                if(mc.getCurNode().getIndex() == EXPLOSION_ESCAPE_NODE) {
                    return true;
                }
                if(!mc.isMoving()) mc.goToNode(EXPLOSION_ESCAPE_NODE);
                for(MC follower : followers) {
                    follower.setAction("Escaping");
                    if(!follower.isMoving()) follower.goToNode(EXPLOSION_ESCAPE_NODE);
                }
                break;
        }
        return false;
    }
    
    /**
     * Return whether an MC can proceed to the next step of the cutting fence escape.
     * 
     * @param w         The simulation world the MC is in.
     * @param mc        The main character doing the escape.
     * @param followers The followers of the MC that will follow along the MC's route.
     * @return boolean  True if MC can proceed to the next step, and do that step, false if the requirements are not met.
     */
    public static boolean cutFence(MyWorld w, MC mc, MC[] followers, int step) {
        switch(step) {
            case 0:
                mc.setAction("Turning Off Generator");
                if(mc.getCurNode().getIndex() == GENERATOR_NODE) {
                    w.generatorOff();
                    return true;
                }
                if(!mc.isMoving()) {
                    mc.goToNode(GENERATOR_NODE);
                }
                break;
            case 1:
                mc.setAction("Going to Cut The Fence");
                if(mc.getCurNode().getIndex() == CUT_FENCE_NODE) return true;
                if(!mc.isMoving()) {
                    mc.goToNode(CUT_FENCE_NODE);
                }
                break;
            case 2:
                mc.setAction("Cutting The Fence");
                if(w.getBreakable(5).isBroken()) return true;
                if(!w.getBreakable(5).isBreaking()) {
                    w.getBreakable(5).beginBreak();
                    sm.playSound("CutFence");
                }
                break;
            case 3:
                mc.setAction("Escaping");
                if(mc.getCurNode().getIndex() == CUT_FENCE_ESCAPE_NODE) {
                    return true;
                }
                if(!mc.isMoving()) {
                    sm.stopSoundLoop("CutFence");
                    mc.goToNode(CUT_FENCE_ESCAPE_NODE);
                }
                for(MC follower : followers) {
                    if(!follower.isMoving()) {
                        follower.setAction("Escaping");
                        follower.goToNode(CUT_FENCE_ESCAPE_NODE);
                    } 
                }
                break;
        }
        return false;
    }
    
    /**
     * Return whether an MC can proceed to the next step of the digging hole escape.
     * 
     * @param w         The simulation world the MC is in.
     * @param mc        The main character doing the escape.
     * @param followers The followers of the MC that will follow along the MC's route.
     * @return boolean  True if MC can proceed to the next step, and do that step, false if the requirements are not met.
     */
    public static boolean digHole(MyWorld w, MC mc, int step) {
        switch(step) {
            case 0:
                mc.setAction("Going to Dig a Hole");
                if(mc.getCurNode().getIndex() == HOLE_ENTER_NODE) return true;
                if(!mc.isMoving()) {
                    mc.goToNode(HOLE_ENTER_NODE);
                }
                break;
            case 1:
                mc.setAction("Digging a Hole");
                if(w.getBreakable(3).isBroken()) {
                    w.addObject(new WallCover("images/WallCover/KitchenTunnel.png"), 84, 304);
                    return true;
                }
                if(!w.getBreakable(3).isBreaking()) {
                    sm.playSound("ShovelDirt");
                    w.getBreakable(3).beginBreak();
                }
                
                break;
            case 2:
                mc.setAction("Tunneling");
                sm.stopSoundLoop("ShovelDirt");
                if(mc.getCurNode().getIndex() == HOLE_EXIT_NODE) return true;
                if(!mc.isMoving()) {
                    mc.goToNode(HOLE_EXIT_NODE);
                }
                break;
            case 3:
                mc.setAction("Digging Out of The Hole");
                if(w.getBreakable(4).isBroken()) {
                    WallCover wallCover = w.getObjectsAt(mc.getX(),mc.getY(), WallCover.class).get(0);
                    w.removeObject(wallCover);
                    return true;
                }
                if(!w.getBreakable(4).isBreaking()) {
                    sm.playSound("ShovelDirt");
                    w.getBreakable(4).beginBreak();
                }
                break;
            case 4:
                sm.stopSoundLoop("ShovelDirt");
                mc.setAction("Escaping");
                if(mc.getCurNode().getIndex() == HOLE_ESCAPE_NODE) {
                    
                    return true;
                }
                if(!mc.isMoving()) {
                    mc.goToNode(HOLE_ESCAPE_NODE);
                }
                break;
        }
        return false;
    }
    
    /**
     * Return an array of the MCs that are following an MC who is doing a main escape.
     * 
     * @param mcs           The array of main characters.
     * @param chosenEscapes The array of escapes.
     * @param index         The index of the main mc doing the main escape.
     * @return followersArr An array of MCs that will follow the MC doing an escape.
     */
    public static MC[] filterMC(MC[] mcs, String[] chosenEscapes, int index) {
        ArrayList<MC> followers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i != index && chosenEscapes[i].substring(1).equals(chosenEscapes[index])) {
                followers.add(mcs[i]);
            }
        }
        MC[] followersArr = new MC[followers.size()];
        int j = 0;
        for(MC follower : followers) {
            followersArr[j] = follower;
            j++;
        }
        return followersArr;
    }
}
