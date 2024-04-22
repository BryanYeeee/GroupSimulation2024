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
        for(MC mc : mcs) {
            totalStrength+=mc.getStrength();
        }
        if(totalStrength > 125) {
            possibleEscapes.add("Beat Cops");
        }
    }

}
