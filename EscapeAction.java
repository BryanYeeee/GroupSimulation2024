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
    
    private static int[] startActs = new int[4];
    
    public static boolean breakWall(MyWorld w, MC forkMC, MC[] followers, int step) {
        switch(step) {
            case 0:
                if(forkMC.getCurNode().getIndex() == BREAK_WALL_NODE) return true;
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
    
    public static MC[] filterMC(MC[] mcs, String[] chosenEscapes, int index) {
        MC[] followers = new MC[3];
        for (int i = 0, j = 0; i < 4; i++) {
            if (i != index && chosenEscapes[i].equals(chosenEscapes[index])) {
                followers[j] = mcs[i];
                j++;
            }
        }
        return followers;
    }
}
