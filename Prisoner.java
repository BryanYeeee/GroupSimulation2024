import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Arrays;
import java.util.ArrayList;
/**
 * Write a description of class Prisoner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Prisoner extends Person
{
    private static ArrayList<String> jobList;
    protected String jobTitle = "";
    // For cutscene/intro world
    public Prisoner(int i, boolean inIntro){
        super(i, inIntro);
    }
    
    public Prisoner(int i) {
        super(i);
        if(!jobList.isEmpty()) {
            jobTitle = jobList.get(Greenfoot.getRandomNumber(jobList.size()));
            //System.out.println(jobTitle);
            jobList.remove(jobTitle);
        }
            //System.out.println(jobList);
    }
    public Prisoner(int i, String jobTitle) {
        super(i);
        this.jobTitle = jobTitle;
        jobList.remove(jobTitle);
    }
    
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
    
    public static void setJobList(String[] jobs) {
        jobList = new ArrayList<String>(Arrays.asList(jobs));
    }
    
    public String getJob() {
        return jobTitle;
    }
    
    public boolean hasJob() {
        return !jobTitle.equals("");
    }
}
