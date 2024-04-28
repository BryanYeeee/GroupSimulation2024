import java.util.ArrayList;
/**
 * Write a description of class Escape here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Escape  
{
    private MyWorld world;
    private ArrayList<String> possibleEscapes = new ArrayList<>();
    private MC[] mcs;
    private Guard[] guards;
    
    private String[] chosenEscapes;
    private int[] escapeSteps;

    private MC forkMC;
    /**
     * Constructor for objects of class Escape
     */
    public Escape(MyWorld w)
    {
        world = w;
        mcs = w.getMainPrisoners();   
        guards = w.getGuards();
        chosenEscapes = new String[4];
        escapeSteps = new int[4];
        
        int totalStrength = 0;
        MC ladderMC = null;
        for(MC mc : mcs) {
            totalStrength+=mc.getStrength();
            for(Item i : mc.getItems()) {
                if(i instanceof Fork) {
                    forkMC = mc;
                } else if(i instanceof Ladder) {
                    ladderMC = mc;
                }
            }
        }
        // if(totalStrength >= 70) {
            // possibleEscapes.add("Beat Cops");
        // }
        // if(totalStrength >= 90) {
            // possibleEscapes.add("Beat Cops");
        // }
        if(forkMC != null) {
            possibleEscapes.add("Break Wall");
            chosenEscapes[0] = "Break Wall";
            chosenEscapes[1] = "Break Wall";
            chosenEscapes[2] = "Break Wall";
            chosenEscapes[3] = "Break Wall";
        }
    }
    
    public void act() {
        if(chosenEscapes[0].equals("Break Wall")) {
            if(EscapeAction.breakWall(world, forkMC, 
                EscapeAction.filterMC(mcs,chosenEscapes,0), escapeSteps[0])) escapeSteps[0]++;;
        }
    }

}
