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
        MC strongestMC = null, forkMC = null;
        for(MC mc : mcs) {
            totalStrength+=mc.getStrength();
            if(!mc.getSpecialty().equals("Thief") && strongestMC == null || strongestMC.getStrength() < mc.getStrength()) {
                strongestMC = mc;
            }
            for(Item i : mc.getItems()) {
                if(i instanceof Fork) {
                    forkMC = mc;
                }
            }
        }
        // if(totalStrength >= 90) {
            // possibleEscapes.add("Beat Cops");
        // }
            chosenEscapes[1] = "?Break Wall";
            chosenEscapes[0] = "?Break Wall";
            chosenEscapes[2] = "?Break Wall";
            chosenEscapes[3] = "?Break Wall";
        if(totalStrength >= 70 || true /*temp*/) {
            possibleEscapes.add("Car");
            chosenEscapes[strongestMC.getIndex()] = "Car";
        }
        if(forkMC != null) {
            possibleEscapes.add("Break Wall");
            chosenEscapes[forkMC.getIndex()] = "Break Wall";
        }
    }
    
    public void act() {
        for(int i = 0; i < 4; i++) {
            if(chosenEscapes[i].charAt(0) == '?') continue; // Following another MC's escape method
            switch(chosenEscapes[i]) {
                case "Break Wall":
                    System.out.println("BREAK WALL " + escapeSteps[i] + " " + i +" " + mcs[i].getSpecialty());
                    if(EscapeAction.breakWall(world, mcs[i], EscapeAction.filterMC(mcs,chosenEscapes,i), escapeSteps[i])) escapeSteps[i]++;
                    break;
                case "Car":
                    if(EscapeAction.driveCar(world, mcs[i], EscapeAction.filterMC(mcs,chosenEscapes,i), escapeSteps[i])) escapeSteps[i]++;
                    break;
            }
        }
            if(chosenEscapes[0].equals("Break Wall")) {
            }
    }

}
