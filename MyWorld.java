import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.awt.Font;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    public final static boolean SHOW_NODES = true;
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
    
    private Breakable[] breakables;
    
    private StatusBar statBar;

    private int actCount;
        
    private Escape escape;
    private boolean escapeTime;
    private int escapingMcs;


    /**
     * Constructor for objects of class MyWorld.
     */
    public MyWorld()
    {    
        // Create a new world with 1200x850 cells with a cell size of 1x1 pixels.
        super(WORLD_WIDTH, WORLD_HEIGHT, 1); 
        setPaintOrder(Item.class, BannerIcon.class,  Banner.class, Clock.class, 
        EventDisplay.class, Alarm.class, NightTime.class, SuperStatBar.class, 
        WallCover.class, Vehicle.class, Accessory.class, Person.class, Underglow.class, 
        Tile.class, Breakable.class, Room.class);
        
        pf = new PathFinder(this); // Initialize this first
        SimulationFont.initalizeFont();
        Sprite.init();
        
        //TEMPP
        //setBackground(backgroundImg);

        // Initialize the people in the prison (MCs, prisoners, guards)
        prisonerCount = 12;
        guardCount = 3;
        people = new Person[prisonerCount + guardCount + 4];
        Prisoner.setJobList(jobs);

        mainPrisoners = new MC[4];
        mainPrisoners[0] = new MC(0, this, "Janitor", "Scientist");
        mainPrisoners[1] = new MC(1, this, "Librarian", "Thief");
        mainPrisoners[2] = new MC(2, this, "", "Brute");;
        mainPrisoners[3] = new MC(3, this, "Metalworker", "Weapons Dealer");
        for (int i = 0; i < 4; i++) {
            people[i] = mainPrisoners[i];
            addObject(mainPrisoners[i], 0, 0);
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
        
        // Initialize schedule and GUI
        schedule = new Schedule(this);
        addObject(new Clock("7:00"), 68, 50);
        eventDisplay = new EventDisplay("ROLLCALL");
        addObject(eventDisplay, 68, 94);
        statBar = new StatusBar();
        addObject(statBar, 600, 762);

        // Initialize the prison's rooms
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
        
        cell1 = new JailCell(new int[]{132,134}, new int[]{95,125},1);
        addObject(cell1, 469, 583);
        cell2 = new JailCell(new int[]{135,137}, new int[]{95,125},2);
        addObject(cell2, 591, 583);
        
        // Add the wall covers to the world
        addObject(new WallCover("images/WallCover/cover1.png"), 317, 323);
        addObject(new WallCover("images/WallCover/cover2.png"), 409, 299);
        addObject(new WallCover("images/WallCover/cover3.png"), 652, 299);
        addObject(new WallCover("images/WallCover/SpawnCover.png"), 363, 79);
        addObject(new WallCover("images/WallCover/WoodworkCover.png"), 319, 600);
        addObject(new WallCover("images/WallCover/MetalworkCover.png"), 728, 601);
        addObject(new WallCover("images/WallCover/VehicleDoorCover.png"), 257, 482);
        
        // Breakables
        breakables = new Breakable[3];
        breakables[0] = new Breakable("images/Breakable/RollCallWall.png",100, 100);
        addObject(breakables[0], 558, 188);
        breakables[1] = new Breakable("images/Breakable/VehicleDoor.png",5, 5);
        addObject(breakables[1], 256, 467);
        breakables[2] = new Breakable("images/Breakable/DiningRoomExplosion.png",1, 1);        
        addObject(breakables[2], 184, 143);
        
        // Vehicles
        addObject(new Vehicle("Car.png"), 150, 492);
        
        /**
         * DEMO
         */
        // addObject(new Potion(),400, 299);
        // addObject(new Metal(),420, 299);
        // addObject(new Ladder(),440, 299);
        // addObject(new Sword(),460, 299);
        // addObject(new Keycard(),480, 299);
        // addObject(new Metal(),500, 299);
        // addObject(new Keycard(),360, 299);
        // addObject(new Wood(),380, 299);
    }

    public void started(){
        Greenfoot.setSpeed(50);
    }
    public void debug() {
        for (Prisoner p : prisoners) {
            p.offsetPos = -20;
        }
    }

    public void act() {
        schedule.act();
        
        if (escapeTime) {
            escape.act();
        }
        
        actCount++;
        zSort();
    }

    public void updateEventDisplay(String event) {
        eventDisplay.update(event);
    }
    
    public boolean doEscape() {
        escapingMcs++;
        if (escapingMcs == 4) { // All prisoners are ready to escape
            escape = new Escape(this);
            escapeTime = true;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isEscapeTime() {
        return escapeTime;
    }
    
    public Breakable getBreakable(int index) {
        return breakables[index];
    }
    
    private void initalizeFont(){
        File f = new File("VT323-Regular.ttf");
        try {
            FileInputStream in = new FileInputStream(f);
            Font dynamicFont, dynamicFont32;

            dynamicFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(f));
            dynamicFont32 = dynamicFont.deriveFont(26f);

            java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(dynamicFont32);
            pixelFont = new greenfoot.Font(dynamicFont32.getName(), dynamicFont32.getStyle() % 2 == 1, dynamicFont32.getStyle() / 2 == 1, dynamicFont32.getSize());
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
    }
    
    public Person[] getPeople() {
        return people;
    }

    public MC[] getMainPrisoners() {
        return mainPrisoners;
    }

    public Prisoner[] getPrisoners() {
        return prisoners;
    }

    public Guard[] getGuards() {
        return guards;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public greenfoot.Font getFont() {
        return pixelFont;
    }

    public void doFree() {
        schedule.doFree();
    }

    public void doJob() {
        schedule.doJob();
    }

    public void doRoll() {
        schedule.doRoll();
    }

    public void doNight() {
        schedule.doNight();
    }

    public int getWidth() {
        return WORLD_WIDTH;
    }

    public int getHeight() {
        return WORLD_HEIGHT;
    }

    public void zSort() {
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
