import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Arrays;
import java.util.ArrayList;
/**
 * The prisoner subclass
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class Prisoner extends Person
{
    private static ArrayList<String> jobList;
    protected String jobTitle = "";
    
    // For cutscene/intro world by Jamison H
    /**
     * Constructor for Prisoner used for cutscene/intro world
     */
    public Prisoner(int i, boolean inIntro){
        super(i, inIntro);
    }
    
    /**
     * Constructor for Prisoner
     */
    public Prisoner(int i) {
        super(i);
        if(!jobList.isEmpty()) {
            jobTitle = jobList.get(Greenfoot.getRandomNumber(jobList.size()));
            System.out.println(jobTitle);
            jobList.remove(jobTitle);
        }
        System.out.println(jobList);
    }
    /**
     * Constructor for Prisoner
     */
    public Prisoner(int i, String jobTitle) {
        super(i);
        this.jobTitle = jobTitle;
        jobList.remove(jobTitle);
        System.out.println(jobList);
    }

    /**
     * Act Method for Prisoner
     */
    public void act() {
        super.act();
        if(!inIntro){
            // Conditions for when a fight should not start: 1.curNode is the spawn 2: Prisoner removed from world 3: Currently in LIGHTS OUT
            if(curNode.getIndex() == STARTING_NODE_INDEX || getWorld() == null || ((MyWorld)getWorld()).getSchedule().getCurrentEvent().equals("LIGHTS OUT")) return;
            
            // Fight intersecting prisoners at random
            if (getWorld()!= null && onGoingFights <= MAX_FIGHTS && curRoom == null && !inFight && !isDead) {
                Prisoner p = (Prisoner)getOneIntersectingObject(Prisoner.class);
                if (p != null && !(p instanceof MC) && !p.isFighting() && p.getCurRoom() == null && !p.isDead() && Greenfoot.getRandomNumber(1000 * (int)Math.pow(3,onGoingFights)) == 0) {
                    setInFight(p, true);
                    p.setInFight(this, true);
                    //curPath.clear();
                    noFights++;
                }
            }
        }
    }
    
    
    /**
     * Takes in a list of jobs and sets the joblist of all Prisoners
     * 
     * @param jobs                      List of different jobs
     */
    public static void setJobList(String[] jobs) {
        jobList = new ArrayList<String>(Arrays.asList(jobs));
    }
    
    /**
     * Return the job of Prisoner
     * 
     * @return jobTitle                 Job of Prisoners
     */
    public String getJob() {
        return jobTitle;
    }
    
    /**
     * Returns if Prisoner has a job or not
     * 
     * @return !jobTitle.equals("None") Checks if Prisoner has a job or not
     */
    public boolean hasJob() {
        return !jobTitle.equals("None");
    }
}
