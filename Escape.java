import java.util.ArrayList;
/**
 * Write a description of class Escape here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Escape  
{
    private ArrayList<String> possibleEscapes = new ArrayList<>();
    private MC[] mcs;
    private Guard[] guards;

    /**
     * Constructor for objects of class Escape
     */
    public Escape(MyWorld w)
    {
        mcs = w.getMainPrisoners();   
        guards = w.getGuards();
        
        int totalStrength = 0;
        MC forkMC = null;
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
        if(totalStrength >= 70) {
            possibleEscapes.add("Beat Cops");
        }
        if(totalStrength >= 90) {
            possibleEscapes.add("Beat Cops");
        }
        if(forkMC != null) {
            possibleEscapes.add("Break Wall");
        }
    }

}
