import java.util.ArrayList;
import greenfoot.*;
/**
 * The escape class will calculate the chosen escape methods for each of the MCs based on certain conditions.
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class Escape  
{
    private MyWorld world;
    private ArrayList<ArrayList<String>> possibleEscapes = new ArrayList<>();
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
        
        // Possible escape methods for each prisoner
        possibleEscapes.add(new ArrayList<String>());
        possibleEscapes.add(new ArrayList<String>());
        possibleEscapes.add(new ArrayList<String>());
        possibleEscapes.add(new ArrayList<String>());
        
        MC strongestMC = null;
        for(MC mc : mcs) {
            if(strongestMC == null || strongestMC.getStrength() < mc.getStrength()) {
                strongestMC = mc;
            }
            if(Greenfoot.getRandomNumber(mc.getChance()) != 0) continue;
            for(Item i : mc.getItems()) {
                if(i instanceof Fork) {
                    possibleEscapes.get(mc.getIndex()).add("2 Break a Wall");
                } else if(i instanceof Bomb) {
                    possibleEscapes.get(mc.getIndex()).add("3 Explode a Wall");
                } else if(i instanceof Knife) {
                    possibleEscapes.get(mc.getIndex()).add("1 Cut The Fence");
                } else if(i instanceof Shovel) {
                    possibleEscapes.get(mc.getIndex()).add("4 Dig a Hole");
                }
            }
        }
        
        boolean hasEscape = false;
        for(ArrayList<String> escapes : possibleEscapes) {
            if(!escapes.isEmpty()) hasEscape = true;
        }
        if(!hasEscape || Greenfoot.getRandomNumber(5)==0)possibleEscapes.get(strongestMC.getIndex()).add("5 Drive The Car");
        
        int j = 0;
        for(ArrayList<String> escapes : possibleEscapes) {
            System.out.println(j + ": " +escapes);
            int highestPriority = 0;
            for(String escape : escapes) {
                int priority = Integer.parseInt(escape.substring(0, 1));
                if(priority > highestPriority) {
                    chosenEscapes[j] = escape.substring(2);
                    highestPriority = priority;
                }
            }
            for(ArrayList<String> escapesArl : possibleEscapes) {
                escapesArl.remove(chosenEscapes[j]);
            }
            j++;
        }
        
        System.out.println("ESCAPES: [");
        for(int i = 0; i < 4; i++) {
            String leaderMC = ""; // The MC that the current MC will follow if no escape condition
            if(chosenEscapes[i] == null) {
                for(int k = 0; k < 4; k++) {
                    if(chosenEscapes[k] != null && chosenEscapes[k].charAt(0) != '?' && !chosenEscapes[k].equals("Dig Hole")) {
                        chosenEscapes[i] = "?"+chosenEscapes[k];
                        leaderMC = mcs[k].getName();
                        break;
                    }
                }
            }
            System.out.print(chosenEscapes[i] + " ,");
            mcs[i].setEscapeMethod(chosenEscapes[i]);
            if(!leaderMC.equals("")) {
                mcs[i].setAction("Escaping with " + leaderMC);
            } else {
                mcs[i].setAction("Going to " + chosenEscapes[i]);
            }
        }
        System.out.println("]");

    }
    
    public void act() {
        for(int i = 0; i < 4; i++) {
            if(chosenEscapes[i].charAt(0) == '?') continue; // Following another MC's escape method
            switch(chosenEscapes[i]) {
                case "Break a Wall":
                    if(EscapeAction.breakWall(world, mcs[i], EscapeAction.filterMC(mcs,chosenEscapes,i), escapeSteps[i])) escapeSteps[i]++;
                    break;
                case "Explode a Wall":
                    if(EscapeAction.explodeWall(world, mcs[i], EscapeAction.filterMC(mcs,chosenEscapes,i), escapeSteps[i])) escapeSteps[i]++;
                    break;
                case "Cut The Fence":
                    if(EscapeAction.cutFence(world, mcs[i], EscapeAction.filterMC(mcs,chosenEscapes,i), escapeSteps[i])) escapeSteps[i]++;
                    break;
                case "Dig a Hole":
                    if(EscapeAction.digHole(world, mcs[i], escapeSteps[i])) escapeSteps[i]++;
                    break;
                case "Drive The Car":
                    if(EscapeAction.driveCar(world, mcs[i], EscapeAction.filterMC(mcs,chosenEscapes,i), escapeSteps[i])) escapeSteps[i]++;
                    break;
            }
        }
    }

}
