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
    //private static ArrayList<String> jobList;
    protected String jobTitle;
    public Prisoner(int i) {
        super(i);
        /*
        if(!jobList.isEmpty()) {
            jobTitle = jobList.get(Greenfoot.getRandomNumber(jobList.size()));
            //System.out.println(jobTitle);
            jobList.remove(jobTitle);
        }
        */
            //System.out.println(jobList);
    }
    /*
    public Prisoner(int i, String jobTitle) {
        super(i);
        this.jobTitle = jobTitle;
        jobList.remove(jobTitle);
    }
    */
    
    public void act() {
        super.act();
        
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
    
    /*
    public static void setJobList(String[] jobs) {
        jobList = new ArrayList<String>(Arrays.asList(jobs));
    }
    
    public String getJob() {
        return jobTitle;
    }
    
    public boolean hasJob() {
        return !jobTitle.equals("");
    }
    */
}
