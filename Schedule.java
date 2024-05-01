import java.util.HashMap;
import java.util.Map;
import greenfoot.*;
import java.util.List;

/**
 * The schedule class is in charge of making the prisoners do various actions based on the time.
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
    
    public int x = 0; // temp variable to track fighting amounts
    /**
     * A map of the timing of messages and the text of the messages.
     */
    {
        messageTimings = new HashMap<>();
        // Beginning roll call
        messageTimings.put(60, "GOOD MORNING INMATES");
        messageTimings.put(120, "PROCEED TO THE MAIN HALL FOR ROLL CALL.");
        // // Breakfast

         messageTimings.put(820, "GET BREAKFAST");
        // // Workout

         messageTimings.put(25*60, "GYM TIME");
        // // JOB

         messageTimings.put(45*60+120, "PROCEED TO THE YOUR DESIGNATED WORK ROOM");
        // // Roll call
 
         messageTimings.put(65*60+120, "ROLL CALL");
        // // Free time
        
        messageTimings.put(80*60+120, "FREE TIME");
        // // Dinner
        // messageTimings.put(100*60, "IT IS 930 HOURS.");
         messageTimings.put(100*60+120, "TIME FOR DINNER");
        // // Roll call
        // messageTimings.put(120*60, "IT IS 930 HOURS.");
        messageTimings.put(120*60+120, "FINAL ROLL CALL BEFORE LIGHTS OUT");
    }

    /**
     * Constructor for objects of class Schedule.
     * Initalize my values for my banner using some world values.
     * 
     * @param w     The simulation world the schedule will be running in.
     */
    public Schedule(MyWorld w)
    {
        world = w;
        bannerFont = w.getFont();
        bannerPosX = w.WORLD_WIDTH/2;
        bannerPosY = 50;    
    }
    
    /**
     * Update the world to show and do free time actions.
     */
    public void doFree() {
        world.updateEventDisplay("FREE TIME");
        currentEvent = "FREE TIME";
        Action.doFreeTime(world);    }

    /**
     * Update the world to show and do job time actions.
     */
    public void doJob() {
        world.updateEventDisplay("JOB TIME");
        currentEvent = "JOB TIME";
        Action.doJob(world);
    }
    
    /**
     * Update the world to show and do roll call actions.
     */
    public void doRoll(){
        world.updateEventDisplay("ROLL CALL");
        currentEvent = "ROLL CALL";
        Action.doRollCall(world);
    }

    /**
     * Update the world to show and do night time actions.
     */
    public void doNight(){
        world.addObject(new NightTime(),0,0);
        world.updateEventDisplay("LIGHTS OUT");
        currentEvent = "LIGHTS OUT";
        Action.doLightsOut(world);
    }

    /**
     * The act method of Schedule.
     * Display character dialogue, annoucements, also update the world to do and show the current event depending on act count.
     */
    public void act()
    {
        if(actCount==10) {
            actCount=120*60;
            world.getMainPrisoners()[1].giveItem(new Knife());
            world.getMainPrisoners()[3].giveItem(new Wood());

            world.getMainPrisoners()[2].giveItem(new Fork());

        }
        /**
        if(actCount==60){
        world.getMainPrisoners()[2].giveItem(new Metal());
        }
        if(actCount==400){
        world.getMainPrisoners()[2].giveItem(new Shovel());
        }
         **/
        // if(actCount==20*60) {
        // }
        // actCount++;
        // if(true) {
        // return;
        // }

        if (messageTimings.containsKey(actCount)) {
            announce(messageTimings.get(actCount), 2);
        }
        switch (actCount) {
            case 0: // Beginning roll call, act 0
                world.updateEventDisplay("ROLL CALL");
                currentEvent = "ROLL CALL";
                Action.doRollCall(world);
                System.out.println("STARTING SIMULATION");
                
                //System.out.println(x + ": " + Person.noFights);
                break;
            
            case 4*60:
                if(Greenfoot.getRandomNumber(2)==0){
                    say("Don't be suspicious...",3);
                }
                else{
                    say("Everybody act normal.",3);
                }
                break;
                
            case 10*60: // Breakfast, act 600
                world.updateEventDisplay("BREAKFAST");
                currentEvent = "DINING HALL";
                
                Action.doDiningHall(world);
                //System.out.println(x + ": " + Person.noFights);
                break;
            
            case 16*60:
                
                if(Greenfoot.getRandomNumber(2)==0){
                    say("Delicious enough...",3);
                }
                else{
                    say("This tastes awful.",3);
                }
                break;
            
                
            case 25*60: // Workout, act 1200
                world.updateEventDisplay("GYM TIME");
                currentEvent = "GYM TIME";
                Action.doGym(world); 
                //System.out.println(x + ": " + Person.noFights);
                //actCount = 0;
                break;
            
            case 33*60:
                say("To get swole is the goal.",3);
                break;
            case 37*60:
                say("The guards stand no chance against us now.",3);
                break;
                
            case 45*60:
                world.updateEventDisplay("JOB TIME");
                currentEvent = "JOB TIME";
                Action.doJob(world);
                //System.out.println(x + ": " + Person.noFights);
                break;
                
            case 55*60:
                say("I guess you could call this a dead end job...",5);
                break;
                
            case 59*60:
                say("Not funny.",2);
                break;

            
            case 65*60:
                world.updateEventDisplay("ROLL CALL");
                currentEvent = "ROLL CALL";
                Action.doRollCall(world);
                //System.out.println(x + ": " + Person.noFights);
                break;
                
            case 75*60:
                say("Does everyone in here have the same barber?",3);
                break;
                
            case 80*60:
                world.updateEventDisplay("FREE TIME");
                currentEvent = "FREE TIME";
                Action.doFreeTime(world);
                //System.out.println(x + ": " + Person.noFights);
                break;
                
            case 90*60:
                say("Everyone make your final preparations",3);
                break;
                
            case 93*60:
                say("But don't make it too obvious",2);
                break;
                
            case 100*60:
                world.updateEventDisplay("DINNER");
                
                currentEvent = "DINING HALL";
                Action.doDiningHall(world);
                //System.out.println(x + ": " + Person.noFights);
                break;
            
            case 110*60:
                
                say("Wait, aren't we supposed to pick our last meal?",5);
                break;
                
            case 120*60:
                world.updateEventDisplay("ROLL CALL");
                currentEvent = "ROLL CALL";
                Action.doRollCall(world);
                //System.out.println(x + ": " + Person.noFights);
                break;
                
            case 124*60:
                say("Guys I'm nervous...",3);
                break;
            case 126*60:
                say("Do we have everything ready?",3);
                break;
            case 130*60:
                say("Only one way to find out.",3);
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
                break;
                
            case 138*60:
                say("Show time.",3);
                break;
        }
        actCount++;
    }

    /**
     * Tell a person to do an action corresponding with the current event.
     * 
     * @param p   The person doing the current event.
     */
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

    /**
     * Return the world's current event.
     * 
     * @return currentEvent The current event.
     */
    public String getCurrentEvent() {
        return currentEvent;
    }

    /**
     * Return the schedule's act count.
     * 
     * @return actCount     The act count.
     */
    public int getActCount() {
        return actCount;
    }

    /**
     * Add an annoucement to the simulation world.
     * 
     * @param text  The text within the annoucement.
     * @param acts  The number of acts the annoucment will appear for.
     */
    public void announce(String text, int acts){
        world.addObject(new Announcement(text,acts),bannerPosX,bannerPosY); 
    }

    /**
     * Add a character dialogue to the simulation world.
     * 
     * @param text          The text within the dialogue.
     * @param acts          The number of acts the dialogue will appear for.
     * @param speakerName   The character speaking the dialogue.
     */
    public void dialogue(String text, int seconds, String speakerName){
        world.addObject(new Dialogue(text,seconds,speakerName),bannerPosX,bannerPosY); 
    }

    /**
     * Return a random MC within the simulation world. 
     * 
     * @return MC   The random MC, null if no MCs in the world.
     */
    public MC getRandomMC()
    {
        
        List<MC> mcs = world.getObjects(MC.class);
        if (mcs.isEmpty())
        {
            return null; // no MC actors found
        }
        return mcs.get(Greenfoot.getRandomNumber(mcs.size()));
    }
    
    /**
     * Run the dialogue function.
     * 
     * @param text      The text of the dialogue.
     * @param seconds   The seconds the dialogue will display for.
     */
    public void say(String text, int seconds){
        dialogue(text,seconds, getRandomMC().getSpecialty().toLowerCase());
    }
}
