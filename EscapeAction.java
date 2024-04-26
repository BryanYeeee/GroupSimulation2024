/**
 * Write a description of class EscapeAction here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EscapeAction extends Action 
{
    private final static int BREAK_WALL_NODE = 71;
    private final static int BREAK_WALL_ESCAPE_NODE = 138;
    
    private final static int KEYCARD_GUARD_NODE = 110;
    private final static int VEHICLE_DOOR_NODE = 139;
    
    private static int[] startActs = new int[4];
    
    public static boolean breakWall(MyWorld w, MC forkMC, MC[] followers, int step) {
        switch(step) {
            case 0:
                if(forkMC.getCurNode().getIndex() == BREAK_WALL_NODE) return true;
                System.out.println(forkMC.isMoving());
                if(!forkMC.isMoving()) {
                    forkMC.goToNode(BREAK_WALL_NODE);
                    startActs[forkMC.getIndex()] = forkMC.getActCount();
                }
                break;
            case 1:
                if(w.getBreakable(0).isBroken()) return true;
                if(!w.getBreakable(0).isBreaking()) {
                    forkMC.setDirection(-1);
                    forkMC.setMovingVertical(true);
                    w.getBreakable(0).beginBreak();
                }
                break;
            case 2:
                if(!forkMC.isMoving()) {
                    forkMC.goToNode(BREAK_WALL_ESCAPE_NODE);
                }

        }
        return false;
    }
    
    public static boolean driveCar(MyWorld w, MC strongestMC, MC[] followers, int step) {
        switch(step) {
            case 0:
                if(strongestMC.getCurNode().getIndex() == KEYCARD_GUARD_NODE) return true;
                if(!strongestMC.isMoving()) {
                    strongestMC.goToNode(KEYCARD_GUARD_NODE);
                    startActs[strongestMC.getIndex()] = strongestMC.getActCount();
                }
                break;
            case 1:
                Item item = new Keycard();
                w.addObject(item, strongestMC.getX(), strongestMC.getY());
                item.pickup(strongestMC);
                return true;
            case 2:
                if(strongestMC.getCurNode().getIndex() == VEHICLE_DOOR_NODE) return true;
                if(!strongestMC.isMoving()) strongestMC.goToNode(VEHICLE_DOOR_NODE);
                break;
            case 3:
                if(w.getBreakable(1).isBroken()) return true;
                if(!w.getBreakable(1).isBreaking()) w.getBreakable(1).beginBreak();
                break;
        }
        return false;
    }
    
    public static MC[] filterMC(MC[] mcs, String[] chosenEscapes, int index) {
        MC[] followers = new MC[3];
        for (int i = 0, j = 0; i < 4; i++) {
            if (i != index && chosenEscapes[i].substring(1).equals(chosenEscapes[index])) {
                followers[j] = mcs[i];
                j++;
            }
        }
        return followers;
    }
}
