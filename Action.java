import greenfoot.*;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * The Action is a helper class that stores the functions for the prisoner's actions.
 * The actions are usually called by the Schedule class.
 * 
 * @author Bryan Y
 * @version April 2024
 */
public class Action  
{
    protected final static int DININGHALL_NODE = 14;
    
    protected final static int ROLLCALL_NODE_1 = 54;
    protected final static int ROLLCALL_NODE_2 = 72;
    protected final static int ROLLCALL_NODE_3 = 58;
    
    protected final static int GYM_NODE = 73;
    
    protected final static int JANITOR_NODE = 142;
    protected final static int LIBRARIAN_NODE = 110;
    protected final static int COOK_NODE = 108;
    protected final static int WOODWORKER_NODE = 4;
    protected final static int METALWORKER_NODE = 118;
    
    protected final static int CELL_1 = 133;
    protected final static int CELL_2 = 136;
    
    private static SoundManager sm;
    /**
     * Tell the people in the simulation world to do roll call actions.
     * 
     * @param w     The simulation world.
     */
    public static void doRollCall(MyWorld w) {
        sm.playSound("RollCall");
        Person[] getPeople = w.getPeople();
        for (Person p : getPeople) {
            //System.out.println(p);
            Action.goToRollCall(w,p);
        }
    }
    
    /**
     * Tell a person in the simluation to move to the roll call area.
     * 
     * @param w     The simulation world.
     * @param p     The person to be moved.
     */
    public static void goToRollCall(MyWorld w, Person p)
    {
        p.clearPath();
        p.setWalking(false);
        Node curNode = p.getCurNode();
        // Go to roll call from the closest entrance to curNode
        int dist1 = w.pf.getNode(ROLLCALL_NODE_1).calcDist(curNode);
        int dist2 = w.pf.getNode(ROLLCALL_NODE_2).calcDist(curNode);
        int dist3 = w.pf.getNode(ROLLCALL_NODE_3).calcDist(curNode);
        if (p instanceof MC) ((MC)p).setAction("Going to Roll Call");
        if (dist1 < dist2 && dist1 < dist3) {
            p.goToNode(ROLLCALL_NODE_1);
        } else if (dist2 < dist1 && dist2 < dist3) {
            p.goToNode(ROLLCALL_NODE_2);
        } else {
            p.goToNode(ROLLCALL_NODE_3);
        }
    }
    
    /**
     * Tell the people in the simulation world to do dining hall actions.
     * 
     * @param w     The simulation world.
     */
    public static void doDiningHall(MyWorld w) {
        Person[] getPeople = w.getPeople();
        //System.out.println(Arrays.toString(getPeople));
        for (Person p : getPeople) {
            Action.goToDiningHall(p);
        }
    }
    
    /**
     * Tell a person in the simulation world to move to the dining hall area.
     * 
     * @param p     The person to be moved.
     */
    public static void goToDiningHall(Person p)
    {
        if(p.getCurRoom() instanceof DiningHall) return;
        if (p instanceof MC) ((MC)p).setAction("Going to Dining Hall");
        p.goToNode(DININGHALL_NODE);
    }
    
    /**
     * Tell the people in the simulation world to do gym actions.
     * 
     * @param w     The simulation world.
     */
    public static void doGym(MyWorld w) {
        Person[] getPeople = w.getPeople();
        for (Person p : getPeople) {
            Action.goToGym(p);
        }
    }
    
    /**
     * Tell a person in the simulation world to move to the gym area. 
     * 
     * @param p     The person to move.
     */
    public static void goToGym(Person p)
    {
        if(p.getCurRoom() instanceof Gym) return;
        if (p instanceof MC) ((MC)p).setAction("Going to Gym");
        p.goToNode(GYM_NODE);
    }
    
    /**
     * Tell the people in the simulation world to do job actions.
     * 
     * @param w     The simulation world.
     */
    public static void doJob(MyWorld w) {
        Person[] getPeople = w.getPeople();
        for (Person p : getPeople) {
            if(p instanceof Guard) {
                continue;
            }
            if(!((Prisoner)p).hasJob()) {
                Action.goToFreeTime(w, p);
                continue;
            }
            //System.out.println(((Prisoner)p).getJob());
            Action.goToJob(w, p, ((Prisoner)p).getJob());
           
            if(p instanceof MC) {
                if(!((MC)p).hasJob()) continue;
                System.out.println(((MC)p).getJob());
                Action.goToJob(w, p, ((MC)p).getJob());
            }
        }
    }
    
    /**
     * Tell a person in the simulation world to go their job area.
     * 
     * @param w         The simulation world.
     * @param p         The person to move.
     * @param jobTitle  The name of the job.
     */
    public static void goToJob(MyWorld w, Person p, String jobTitle) {
        if(p.isWalking()) {
            p.clearPath();
            p.setWalking(false);
        }
        switch (jobTitle) {
            case "Janitor":
                p.goToNode(JANITOR_NODE);
                break;
            case "Librarian":
                if(p.getCurRoom() instanceof Library) return;
                Action.goToLibrary(w, p);
                break;
            case "Cook":
                if(p.getCurRoom() instanceof DiningHall) return;
                p.goToNode(COOK_NODE);
                break;
            case "Woodworker":
                if(p.getCurRoom() instanceof Woodwork) return;
                p.goToNode(WOODWORKER_NODE);
                break;
            case "Metalworker":
                if(p.getCurRoom() instanceof Metalwork) return;
                p.goToNode(METALWORKER_NODE);
                break;
            default:
                Action.goToFreeTime(w,p);
                break;
        }
        if (p instanceof MC) ((MC)p).setAction("Going to " + ((MC)p).getJob() + " Job");
    }
    
    /**
     * Tell a person in the simulation world to go to the library.
     * 
     * @param w     The simulation world.
     * @param p     The person to be moved.
     */
    public static boolean goToLibrary(MyWorld w,Person p) {
        if(Library.canEnterLibrary()) {
            if (p instanceof MC) ((MC)p).setAction("Going to The Library");
            p.goToNode(LIBRARIAN_NODE);
            Library.addPeopleInLibrary();
            return true;
        }
        return false;
    }
    
    /**
     * Tell the people in the simulation world to do free time actions.
     * 
     * @param w     The simulation world.
     */
    public static void doFreeTime(MyWorld w) {
        Person[] getPeople = w.getPeople();
        for (Person p : getPeople) {
            Action.goToFreeTime(w, p);
        }
    }
    
    /**
     * Tell a person in the simulation world to move to where they should spend their free time.
     * 
     * @param w     The simulation world.
     * @param p     The person to be moved.
     */
    public static void goToFreeTime(MyWorld w, Person p) {
        if(p.isWalking()) {
            p.clearPath();
            p.setWalking(false);
        }
         if(p instanceof MC && ((MC)p).getSpecialty().equals("Brute")) {
             Action.goToGym(p);
             return;
        }
            // if(((MC)p).getSpecialty().equals("Brute")) {
                // Action.goToGym(p);
                // return;
            // }
            // System.out.println(p.getIndex() + ": MCJOB");
            // Action.goToJob(w, p, ((Prisoner)p).getJob());
            // return;
        // }
        if(p instanceof Prisoner) {
            switch (Greenfoot.getRandomNumber(4)+ (((Prisoner)p).hasJob()?1:0)) {
                case 3:
            //System.out.println(p.getIndex() + ": LIB");
                    if(Action.goToLibrary(w, p)) break;
                    if(p instanceof MC) {
                        Action.goToFreeTime(w, p);
                        return;
                    }
                case 0:
                    // walk around
            //System.out.println(p.getIndex() + ": WALK");
                    p.setWalking(true);
                    switch(Greenfoot.getRandomNumber(2)) {
                        case 0:
                            p.goToNode(Person.STARTING_NODE_INDEX);
                            break;
                        case 1:
                            p.goToNode(Person.WALKING_NODE_INDEX);
                            break;
                    }
                    break;
                case 1:
            //System.out.println(p.getIndex() + ": EAT");
                    Action.goToDiningHall(p);
                    break;
                case 2:
            //System.out.println(p.getIndex() + ": GYM");
                    Action.goToGym(p);
                    break;
                case 4:
            //System.out.println(p.getIndex() + ": JOB");
                    Action.goToJob(w, p, ((Prisoner)p).getJob());
                    break;
                
            }
        }
        if(p instanceof Guard) {
            p.setWalking(true);
            switch(Greenfoot.getRandomNumber(2)) {
                case 0:
                    p.goToNode(Person.STARTING_NODE_INDEX);
                    break;
                case 1:
                    p.goToNode(Person.WALKING_NODE_INDEX);
                    break;
            }
        }
    }
    
    /**
     * Generate a random path from the spawn to the top right node, or vice versa upon modifying the parameter toSpawn.
     * In addition, tell a person to move through this path.
     * 
     * @param p         The person to be moved.
     * @param toSpawn   True if the generated path shoudl be from spawn to top right, false if top right to spawn.
     */
    public static void walkAround(Person p, boolean toSpawn) {
        if (toSpawn) {
            switch (Greenfoot.getRandomNumber(4)) {
                case 0:
                    p.goToNode(39);
                    if (Greenfoot.getRandomNumber(2) == 0) {
                        p.goToNode(66);
                    } else {
                        p.goToNode(3);
                    }
                    break;
                case 1:
                    p.goToNode(66);
                    p.goToNode(72);
                    p.goToNode(3);
                    break;
            }
            //System.out.println("TOSPAWN");
            p.goToNode(Person.STARTING_NODE_INDEX);
        } else {
            switch (Greenfoot.getRandomNumber(2)) {
                case 0:
                    p.goToNode(1);
                    if (Greenfoot.getRandomNumber(2) == 0) {
                        p.goToNode(72);
                    }
                    break;
                case 1:
                    p.goToNode(3);
                    if (Greenfoot.getRandomNumber(2) == 0) {
                        p.goToNode(72);
                        p.goToNode(58);
                    }
                    break;
            }
            //System.out.println("NOOSPAWN");
            p.goToNode(Person.WALKING_NODE_INDEX);
        }
    }
    
    /**
     * Tell the people in the simulation world to do lights out actions.
     * 
     * @param w     The simulation world.
     */
    public static void doLightsOut(MyWorld w) {
        Person[] getPeople = w.getPeople();
        for (Person p : getPeople) {
            Action.goToLightsOut(w,p);
        }
        Guard keycardGuard = new Guard();
        w.addObject(keycardGuard, 0, 0);
        keycardGuard.goToNode(110);
    }
    
    /**
     * Tell a person in the simulation world to move to their lights out area.
     * This will be their own cell if they are an MC, or their starting area if they aren't.
     * 
     * @param w     The simulation world.
     * @param p     The person to be moved.
     */
    public static void goToLightsOut(MyWorld w, Person p) {
        if (p instanceof Prisoner && !(p instanceof MC)) {
            p.clearPath();
            p.goToNode(Person.STARTING_NODE_INDEX);
        }
        if (p instanceof MC) {
            ((MC)p).setAction("Going to Jail Cell");
            switch(p.getIndex()) {
                case 0:
                case 1:
                    p.goToNode(CELL_1);
                    break;
                case 2:
                case 3:
                    p.goToNode(CELL_2);
                    break;
            }
        }
        if(p instanceof Guard) {
            p.setWalking(true);
            switch(Greenfoot.getRandomNumber(2)) {
                case 0:
                    p.goToNode(Person.STARTING_NODE_INDEX);
                    break;
                case 1:
                    p.goToNode(Person.WALKING_NODE_INDEX);
                    break;
            }
        }
    }
}
