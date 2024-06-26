import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.awt.Font;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Arrays;

/**
 * Prison Escape Simluation: The Escapers <p>
 * 
 * Plot: <p>
 * 4 prisoners are told they only have one day to live, thus they spend their last day gathering resources and increasing their stats to attempt an escape.
 * Then, in the night time, they execute their plan and try to escape the prison. <p>
 * 
 * Features: <p>
 * - Day-Night Cycle where prisoners obtain items and increase their stats in the day, and at night time they will attempt to escape <br>
 * - Fighting between inmates and guards
 * - A clock with a current event indicator <br>
 * - Character dialogue throughout the simluation that can be triggered by conditions such as obtaining an item <br>
 * - Pathing algorithm that allows for prisoners to nagivate the map around nodes efficiently <br>
 * - Unique accessories and underglow for each MC <br>
 * - Animations for eating, working out, roaming around, executing escapes, fighting <br>
 * - Items that can give various buffs and craft other items <br>
 * - A status bar that shows the 4 MCs' stats and current action <br>
 * <p>
 * Credits: <p>
 * Images/Backgrounds not credited here are drawn by chatGPT <p>
 * 
 * Font: 
 * https://www.fontsquirrel.com/fonts/list/classification/pixel VT323 by Peter Hull <br>
 * Intro Background:
 * https://www.deviantart.com/saulica/art/Prison-Cell-Pixel-Art-x4-Size-485929021 Prison Cell - Pixel Art x4 Size by Saulica <br>
 * 
 * Character Intro Advance Button:
 * https://www.vecteezy.com/vector-art/26996703-arrow-left-button-pixelated-rgb-color-ui-icon-move-back-previous-track-simplistic-filled-8bit-graphic-element-retro-style-design-for-arcade-video-game-art-editable-vector-isolated-image
 * Arrow left button pixelated RGB color ui icon… by bsd_studio<br>
 * 
 * Indicator:
 * https://iconduck.com/icons/257047/pop-up-blank <br>
 * 
 * Exclamation Mark:
 * https://emojipedia.org/exclamation-mark <br>
 * 
 * Question Mark:
 * https://www.cleanpng.com/png-question-mark-royalty-free-stock-photography-clip-1624671/ by latti<br>
 * 
 * Health Icon:
 * https://www.cleanpng.com/png-heart-icon-health-care-icon-health-care-icon-7679290/ by organizing<br>
 * 
 * Intelligence Icon:
 * https://wiki.hypixel.net/Intelligence <br>
 * 
 * Strength Icon:
 * https://www.freepik.com/icon/empowerment_9603955#fromView=keyword&page=1&position=82&uuid=07bc346f-9646-4f2e-9046-727c76e4a19f by cah nggunung<br>
 * 
 * Ending Background:
 * https://www.pinterest.com/pin/free-vector--1041246376317781659/ by Freepik<br>
 * <p>
 * Sounds: <p>
 * WoodBurning:
 * https://www.youtube.com/watch?v=-mZHANRv8T4<br>
 * 
 * Wall Break:
 * https://www.youtube.com/watch?v=oRwHxQnu-gs<br>
 * 
 * Metal Craft:
 * https://www.youtube.com/watch?v=LRaa9ejX5R8<br>
 * 
 * Roll Call:
 * https://www.youtube.com/watch?v=AftEpDVWzRw<br>
 * 
 * Shovel Dirt:
 * https://www.youtube.com/watch?v=JFr3YEz12H4<br>
 * 
 * Statscreen: 
 * https://www.youtube.com/watch?v=TkM88tuTYxA<br>
 * 
 * Titlescreen:
 * https://www.youtube.com/watch?v=Ujds4BkTvaI<br>
 * 
 * MainEscape:
 * https://www.youtube.com/watch?v=9LmNMqenFmk<br>
 * 
 * LightsOut:
 * https://www.youtube.com/watch?v=lV1dnpfyg9Q <br>
 * 
 * Fighting:
 * https://www.youtube.com/watch?v=KZcC1oK291I <br>
 * 
 * @author Bryan Y, Ainson Z, and Jeff G
 * @version April 2024
 */
public class MyWorld extends AllWorld
{
    public final static boolean SHOW_NODES = false;
    public final static int WORLD_WIDTH = 1200;
    public final static int WORLD_HEIGHT = 850;
    public static greenfoot.Font pixelFont;

    private GreenfootImage backgroundImg = new GreenfootImage("images/background.png");
    public static PathFinder pf;

    public final static String[] jobs = new String[]{"Janitor","Librarian","Librarian","Cook","Woodworker","Metalworker","Woodworker","Metalworker"};
    private Person[] people;
    public static int prisonerCount;
    public static Prisoner g;
    private Prisoner[] prisoners;

    public static int guardCount;
    private Guard[] guards;

    private MC[] mainPrisoners;
    private MC[] savedMainPrisoners;

    private EventDisplay eventDisplay;
    private Schedule schedule;
    private DiningHall diningHall;
    private RollCall rollCall;
    private Gym gym;
    private Library library;
    private Woodwork woodwork;
    private Metalwork metalwork;
    private JanitorCloset janitorCloset;
    private JailCell cell1;
    private JailCell cell2;
    private Generator generator;

    private Breakable[] breakables;

    private StatusBar statBar;

    private int actCount;

    private Escape escape;
    private boolean escapeTime;
    private int escapingMcs;
    /**
     * An alternate constructor of MyWorld for faster testing, as it doesn't require parameters.
     */
    public MyWorld() {
        this(Arrays.asList(
                "Leon,Metalworker,45,1.8,100,Brute",
                "Aron,Janitor,25,1.8,100,Weapons Dealer",
                "Waldo,Cook,8,1.9,100,Thief",
                "Reuben,Woodworker,5,1.8,100,Builder"
            ));
        sm.stopSoundLoop("MainEscape");
    }

    /**
     * The main constructor for objects of class MyWorld that sets the map and MCs.
     * 
     * @param selectedPrisoners A list of values from IntroWorld that give indicators of which MCs were chosen.
     */
    public MyWorld(List<String> serializedDataList)
    {   
        // Create a new world with 1200x850 cells with a cell size of 1x1 pixels.
        super(WORLD_WIDTH, WORLD_HEIGHT, 1); 
        setPaintOrder(Fade.class, Item.class, BannerIcon.class,  Dialogue.class, Announcement.class,Clock.class, 
            EventDisplay.class, NightTime.class, SuperStatBar.class, 
            ElectricFence.class, Explosive.class,WallCover.class, Generator.class, Vehicle.class, Accessory.class, 
            Person.class, Underglow.class, Tile.class, Breakable.class, Room.class);

        pf = new PathFinder(this); // Initialize this first
        SimulationFont.initalizeFont();
        Sprite.init();
        setBackground(backgroundImg);

        //LAG TRACKER by Jeff G
        PerformanceMeter meter = new PerformanceMeter();
        addObject(meter, 50, 25);

        // Initialize the people in the prison (MCs, prisoners, guards) by Ainson Z
        prisonerCount = 12;
        guardCount = 3;
        people = new Person[prisonerCount + guardCount + 4];
        Prisoner.setJobList(jobs);

        int index = 0;
        mainPrisoners = new MC[4];
        for(String serializedData : serializedDataList) {
            mainPrisoners[index] = new MC(index, this, serializedData.split(","));
            people[index] = mainPrisoners[index];
            addObject(mainPrisoners[index],0,0);
            index++;
        }

        Person.onGoingFights = 0;
        prisoners = new Prisoner[prisonerCount];
        for (int i = 0; i < prisonerCount; i++) {
            prisoners[i] = new Prisoner(i + 4);
            people[i + 4] = prisoners[i];
            addObject(prisoners[i], 0, 0);
        }

        guards = new Guard[guardCount];
        for (int i = 0; i < guardCount; i++) {
            guards[i] = new Guard(i + 4 + prisonerCount);
            people[i + prisonerCount + 4] = guards[i];
            addObject(guards[i], 0, 0);
        }
        // Test prisoner
        // g = new Prisoner(17);
        // addObject(g,0,0);

        // Initialize schedule and GUI by Bryan Y 
        schedule = new Schedule(this);
        addObject(new Clock("7:00"), 68, 50);
        eventDisplay = new EventDisplay("ROLLCALL");
        addObject(eventDisplay, 68, 94);
        statBar = new StatusBar();
        addObject(statBar, 600, 762);

        // Initialize the prison's rooms by Bryan Y 
        rollCall = new RollCall(new int[]{64, 65, 66, 67, 68, 59, 60, 61, 62, 63, 55, 56, 57, 50, 51, 52}, new int[]{69, 70, 71}, new int[]{213, 243});
        addObject(rollCall, 530, 279);

        diningHall = new DiningHall(new int[]{20, 32, 21, 33, 24, 28, 25, 29, 22, 34, 26, 30, 23, 35, 27, 31}, new int[]{10, 7, 17}, new int[]{244, 335});
        addObject(diningHall, 181, 294);

        gym = new Gym(new int[]{79, 95, 80, 96, 88, 97, 89, 98, 90, 103, 91, 104, 92, 105, 93, 106}, new int[]{94, 81, 87}, new int[]{304, 274});
        addObject(gym, 971, 538);

        library = new Library(new int[]{109, 111}, new int[]{213, 123});
        addObject(library, 743, 188);

        woodwork = new Woodwork(new int[]{116, 117}, new int[]{153, 155});
        addObject(woodwork, 317, 597);

        metalwork = new Metalwork(new int[]{122, 121}, new int[]{123, 153});
        addObject(metalwork, 728, 598);

        janitorCloset = new JanitorCloset(new int[]{142}, new int[]{93,63});
        addObject(janitorCloset, 773, 370);
        generator = new Generator();
        addObject(generator, 758, 370);

        cell1 = new JailCell(new int[]{132,134}, new int[]{95,125},1);
        addObject(cell1, 469, 583);
        cell2 = new JailCell(new int[]{135,137}, new int[]{95,125},2);
        addObject(cell2, 591, 583);

        // Add the wall covers to the world by Bryan Y 
        addObject(new WallCover("images/WallCover/cover1.png"), 317, 323);
        addObject(new WallCover("images/WallCover/cover2.png"), 409, 299);
        addObject(new WallCover("images/WallCover/cover3.png"), 652, 299);
        addObject(new WallCover("images/WallCover/cover4.png"), 834, 391);
        addObject(new WallCover("images/WallCover/SpawnCover.png"), 363, 79);
        addObject(new WallCover("images/WallCover/WoodworkCover.png"), 319, 600);
        addObject(new WallCover("images/WallCover/MetalworkCover.png"), 728, 601);
        addObject(new WallCover("images/WallCover/VehicleDoorCover.png"), 257, 482);

        // Breakables by Bryan Y 
        breakables = new Breakable[6];
        breakables[0] = new Breakable("images/Breakable/RollCallWall.png",100, 100);
        addObject(breakables[0], 558, 188);
        breakables[1] = new Breakable("images/Breakable/VehicleDoor.png",5, 5);
        addObject(breakables[1], 256, 467);
        breakables[2] = new Breakable("images/Breakable/DiningRoomExplosion.png",50, 50);        
        addObject(breakables[2], 184, 143);
        breakables[3] = new Breakable("images/Breakable/KitchenFloor.png",50, 50);        
        addObject(breakables[3], 135, 323);
        breakables[4] = new Breakable("images/Breakable/OutsideFloor.png",50, 50);        
        addObject(breakables[4], 33, 323);
        breakables[5] = new Breakable("images/Breakable/Fence.png",100, 100);        
        addObject(breakables[5], 882, 162);

        // Vehicles by Bryan Y 
        addObject(new Vehicle("Car.png"), 150, 492);

        //Electric Fence by Jeff
        addObject(new ElectricFence(27),1077,360);
        addObject(new ElectricFence(24),1077,320);
        addObject(new ElectricFence(21),1077,280);
        addObject(new ElectricFence(18),1077,240);
        addObject(new ElectricFence(15),1077,200);
        addObject(new ElectricFence(12,true),1040,160);
        addObject(new ElectricFence(9,true),1000,160);
        addObject(new ElectricFence(6,true),960,160);
        addObject(new ElectricFence(3,true),920,160);
        addObject(new ElectricFence(0,true),880,160);

        //Item testing
        // addObject(new Potion(),400, 299);
        // addObject(new Metal(),420, 299);
        // addObject(new Ladder(),440, 299);
        // addObject(new Sword(),460, 299);
        // addObject(new Keycard(),480, 299);
        // addObject(new Metal(),500, 299);
        // addObject(new Keycard(),360, 299);
        // addObject(new Wood(),380, 299);

        sm.playSound("MainEscape");
    }

    /**
     * A debugging method that offsets the prisoners by 20 pixels.
     */
    public void debug() {
        for (Prisoner p : prisoners) {
            p.offsetPos = -20;
        }
    }

    /**
     * The act method of the simulation, zSorts actors and relies on the act() of schedule and escape.
     * Once all prisoners have escaped, move onto the EndWorld automatically.
     */

    public void act() {
        schedule.act();

        if (escapeTime) {
            escape.act();
            int escapedMcs = 0;
            for(MC mc : mainPrisoners) {
                if(mc.getX() ==0 || mc.getY() ==0){
                    escapedMcs++;

                }
            }
            if(escapedMcs == 4) {
                Greenfoot.setWorld(new EndWorld(mainPrisoners));
            }
        }

        actCount++;
        zSort();
    }

    /**
     * Update the event display with the current event.
     * 
     * @param event The current event to be displayed.
     */
    public void updateEventDisplay(String event) {
        eventDisplay.update(event);
    }

    /**
     * Turn the generator off.
     */
    public void generatorOff() {
        generator.turnOff();
        sm.playSound("GeneratorOff");
    }

    /**
     * Start the escape section of the simulation, swap music as well.
     */
    public boolean doEscape() {

        escapingMcs++;
        if (escapingMcs == 4) { // All prisoners are ready to escap
            sm.stopSoundLoop("MainEscape");
            sm.playSound("LightsOut");
            escape = new Escape(this);
            escapeTime = true;
            Guard.setGuardStats(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return whether it is time to escape.
     * 
     * @return escapeTime   True if it is time to escape, false if it isn't.
     */
    public boolean isEscapeTime() {
        return escapeTime;
    }

    /**
     * Return the index of a breakable object.
     * 
     * @param index     The index of the breakable object.
     * @return breakables[index]    The breakable object.
     */
    public Breakable getBreakable(int index) {
        return breakables[index];
    }

    /**
     * Return the people (MC, other NPC prisoners, guards) within the simulation.
     * 
     * @return people   The people within the simulation.
     */
    public Person[] getPeople() {
        return people;
    }

    /**
     * Return the 4 main prisoners within the simulation.
     * 
     * @return mainPrisoners    The main prisoners within the simulation.
     */
    public MC[] getMainPrisoners() {
        return mainPrisoners;
    }

    /**
     * Return the prisoners (not MCs) within the simulation.
     * 
     * @return prisoners   The prisoners within the simulation.
     */
    public Prisoner[] getPrisoners() {
        return prisoners;
    }

    /**
     * Return the guards within the simulation.
     * 
     * @return guards   The guards within the simulation.
     */
    public Guard[] getGuards() {
        return guards;
    }

    /**
     * Return the schedule of the simluation.
     * 
     * @return schedule    The simluation schedule.
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Return the font used for text.
     * 
     * @return pixelFont    The font.
     */
    public greenfoot.Font getFont() {
        return pixelFont;
    }

    /**
     * Tell the schedule to start free time actions.
     */
    public void doFree() {
        schedule.doFree();
    }

    /**
     * Tell the schedule to start job time actions.
     */
    public void doJob() {
        schedule.doJob();
    }

    /**
     * Tell the schedule to start roll call actions.
     */
    public void doRoll() {
        schedule.doRoll();
    }

    /**
     * Tell the schedule to start night time actions.
     */
    public void doNight() {
        schedule.doNight();
    }

    /**
     * Return the world width.
     * 
     * @return WORLD_WIDTH  The world width.
     */
    public int getWidth() {
        return WORLD_WIDTH;
    }

    /**
     * Return the world height.
     * 
     * @return WORLD_WIDTH  The world height.
     */
    public int getHeight() {
        return WORLD_HEIGHT;
    }

    /**
     * Add an Dialogue object, a line of dialogue characters say.
     * 
     * @param text          The text of the dialogue.
     * @param seconds       The duration of the dialogue.
     * @param speakerName   The name of the charater speaking.
     */
    public  void dialogue(String text, int seconds, String speakerName){
        addObject(new Dialogue(text,seconds,speakerName),WORLD_WIDTH/2,50 ); 
    }

    /**
     * Z-Sort (re-order) all entities of the simulation.
     */
    public void zSort() {
        //Z-sorting by Jeff
        List<Entity> rawEntities = getObjects(Entity.class);
        ArrayList<ActorContent> acList = new ArrayList<ActorContent>();
        for (Entity entity : rawEntities) {
            acList.add(new ActorContent(entity, entity.getX(), entity.getY()));
        }
        Collections.sort(acList);
        for (ActorContent ac : acList) {
            Entity entity = (Entity) ac.getActor();
            removeObject(entity);
            addObject(entity, ac.getX(), ac.getY());
        }
    }

    /**
     * This interface is used for z-Sorting.
     */
    class ActorContent implements Comparable<ActorContent> {
        private Actor actor;
        private int xx, yy;

        public ActorContent(Actor actor, int xx, int yy) {
            this.actor = actor;
            this.xx = xx;
            this.yy = yy;
        }

        public void setLocation(int x, int y) {
            xx = x;
            yy = y;
        }

        public int getX() {
            return xx;
        }

        public int getY() {
            return yy;
        }

        public Actor getActor() {
            return actor;
        }

        public String toString() {
            return "Actor: " + actor + " at " + xx + ", " + yy;
        }

        public int compareTo(ActorContent a) {
            return this.yy - a.yy;
        }
    }
}
