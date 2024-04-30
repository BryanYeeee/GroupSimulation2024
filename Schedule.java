import java.util.HashMap;
import java.util.Map;
import greenfoot.*;
/**
 * The schedule class is in charge of making the prisoners do various actions based on the time
 * 
 * @author Bryan Y 
 * @version April 2024
 */
public class Schedule  
{
    //private static final int[] times = new int[] {0,10};
    private int actCount;
    private Map<Integer, String> messageTimings;
    private greenfoot.Font bannerFont;
    private int bannerPosX, bannerPosY;
    private String currentEvent;
    private MyWorld world;
    {
        messageTimings = new HashMap<>();
        // Beginning roll call
        messageTimings.put(0, "ATTENTION ALL INMATES,");
        messageTimings.put(120, "IT IS 730 HOURS.");
        messageTimings.put(240, "PROCEED TO THE MAIN HALL FOR ROLL CALL.");
        // // Breakfast
        // messageTimings.put(10*60, "IT IS 930 HOURS.");
        // messageTimings.put(820, "PROCEED TO THE DINING ROOM FOR BREAKFAST");
        // // Workout
        // messageTimings.put(25*60, "IT IS 930 HOURS.");
        // messageTimings.put(25*60+120, "PROCEED TO THE GYM TO WORKOUT");
        // // JOB
        // messageTimings.put(45*60, "IT IS 930 HOURS.");
        // messageTimings.put(45*60+120, "PROCEED TO THE YOUR DESIGNATED WORK ROOM");
        // // Roll call
        // messageTimings.put(65*60, "IT IS 930 HOURS.");
        // messageTimings.put(65*60+120, "PROCEED TO THE MAIN HALL FOR ROLL CALL");
        // // Free time
        // messageTimings.put(80*60, "IT IS 930 HOURS.");
        // messageTimings.put(80*60+120, "FREE TIME");
        // // Dinner
        // messageTimings.put(100*60, "IT IS 930 HOURS.");
        // messageTimings.put(100*60+120, "PROCEED TO THE DINING ROOM FOR DINNER");
        // // Roll call
        // messageTimings.put(120*60, "IT IS 930 HOURS.");
        // messageTimings.put(120*60+120, "PROCEED TO THE MAIN HALL FOR ROLL CALL");
    }
    
    /**
     * Constructor for objects of class Schedule
     */
    public Schedule(MyWorld w)
    {
        world = w;
        bannerFont = w.getFont();
        bannerPosX = w.WORLD_WIDTH/2;
        bannerPosY = 50;    
    }
    public int x = 0; // temp variable to track fighting amounts
    public void doFree() {
            world.updateEventDisplay("FREE TIME");
                currentEvent = "FREE TIME";
                Action.doFreeTime(world);    }
    public void doJob() {
                world.updateEventDisplay("JOB TIME");
                currentEvent = "JOB TIME";
                Action.doJob(world);
    }
    public void doRoll(){
                world.updateEventDisplay("ROLL CALL");
                currentEvent = "ROLL CALL";
                Action.doRollCall(world);
    }
    public void doNight(){
                world.addObject(new NightTime(),0,0);
                world.updateEventDisplay("LIGHTS OUT");
                 currentEvent = "LIGHTS OUT";
                Action.doLightsOut(world);
    }
    
    public void act()
    {
        if(actCount==10) {
           // actCount=135*60;
           // world.getMainPrisoners()[1].giveItem(new Fork());
           // world.getMainPrisoners()[0].giveItem(new Chemicals());
           // world.getMainPrisoners()[2].setStrength(35);
           // world.getMainPrisoners()[3].giveItem(new Shovel());
        }
        // if(actCount==20*60) {
        // }
        // actCount++;
                // if(true) {
             // return;
         // }
                
        if (messageTimings.containsKey(actCount)) {
            announce(messageTimings.get(actCount), 150);
        }
        switch (actCount) {
            case 0: // Beginning roll call, act 0
                world.updateEventDisplay("ROLL CALL");
                currentEvent = "ROLL CALL";
                Action.doRollCall(world);
                System.out.println("STARTING SIMULATION");
                //System.out.println(x + ": " + Person.noFights);
                MC[] mc = world.getMainPrisoners();
                for(MC m : mc) {
                    m.setStrength(Greenfoot.getRandomNumber(5)+5);
                    if(m.getSpecialty().equals("Brute")) m.setStrength(Greenfoot.getRandomNumber(3)+10);
                    System.out.println(m.getSpecialty() +": "+m.getStrength());
                }
                break;
            case 10*60: // Breakfast, act 600
                world.updateEventDisplay("BREAKFAST");
                currentEvent = "DINING HALL";
                Action.doDiningHall(world);
                //System.out.println(x + ": " + Person.noFights);
                break;
            case 25*60: // Workout, act 1200
                world.updateEventDisplay("GYM TIME");
                currentEvent = "GYM TIME";
                Action.doGym(world); 
                //System.out.println(x + ": " + Person.noFights);
                //actCount = 0;
                break;
            case 45*60:
                world.updateEventDisplay("JOB TIME");
                currentEvent = "JOB TIME";
                Action.doJob(world);
                //System.out.println(x + ": " + Person.noFights);
                break;
            case 65*60:
                world.updateEventDisplay("ROLL CALL");
                currentEvent = "ROLL CALL";
                Action.doRollCall(world);
                //System.out.println(x + ": " + Person.noFights);
                break;
            case 80*60:
                world.updateEventDisplay("FREE TIME");
                currentEvent = "FREE TIME";
                Action.doFreeTime(world);
                //System.out.println(x + ": " + Person.noFights);
                break;
            case 100*60:
                world.updateEventDisplay("DINNER");
                currentEvent = "DINING HALL";
                Action.doDiningHall(world);
                //System.out.println(x + ": " + Person.noFights);
                break;
            case 120*60:
                world.updateEventDisplay("ROLL CALL");
                currentEvent = "ROLL CALL";
                Action.doRollCall(world);
                //System.out.println(x + ": " + Person.noFights);
                break;
            case 135*60:
                world.addObject(new NightTime(),0,0);
                world.updateEventDisplay("LIGHTS OUT");
                currentEvent = "LIGHTS OUT";
                Action.doLightsOut(world);
                // actCount = 1;
                // System.out.println("ENDING SIMULATION");
                // //System.out.println(x + ": " + Person.noFights);
                // mc = world.getMainPrisoners();
                // for(MC m : mc) {
                    // System.out.println(m.getSpecialty() +": "+m.getStrength());
                    // m.setStrength(Greenfoot.getRandomNumber(5)+5);
                    // if(m.getSpecialty().equals("Brute")) m.setStrength(Greenfoot.getRandomNumber(3)+10);
                // }
                System.out.println("# of fights (npcs and mcs): " + Person.noFights);
                System.out.println();
                System.out.println();
                //x++;
                Person.noFights = 0;
        }
        if(actCount==440){
            dialogue("this bomb that I smuggled might help", 150, "scientist");
        }
        if(actCount==360){
            dialogue("I need to get out of here", 150, "thief");
        }
        if(actCount==440){
            dialogue("this bomb that I smuggled might help", 150, "scientist");
        }
        actCount++;
    }
    
    public void doCurrentEvent(Person p) {
        switch (currentEvent) {
            case "ROLL CALL":
                Action.goToRollCall(world,p);
                break;
            case "DINING HALL":
                Action.goToDiningHall(p);
                break;
            case "GYM TIME":
                Action.goToGym(p);
                break;
            case "JOB TIME":
                Action.goToJob(world, p, p instanceof Prisoner ? ((Prisoner)p).getJob() : "");
                break;
            case "FREE TIME":
                Action.goToFreeTime(world, p);
                break;
            case "LIGHTS OUT":
                Action.goToLightsOut(world, p);
                break;
        }
    }
    
    public String getCurrentEvent() {
        return currentEvent;
    }
    
    public int getActCount() {
        return actCount;
    }
    
    public void announce(String text, int acts){
        world.addObject(new Announcement(text,acts),bannerPosX,bannerPosY); 
    }
         
    public void dialogue(String text, int acts, String speakerName){
        world.addObject(new Dialogue(text,acts,speakerName),bannerPosX,bannerPosY); 
    }
}
