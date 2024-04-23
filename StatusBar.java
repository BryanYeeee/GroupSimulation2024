import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Display for various stats and items
 * Does not auto update, will update after being set to update
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StatusBar extends Actor
{
    // Background
    private GreenfootImage background;

    // Colors
    private Color bgColor = new Color (84,84,84);
    private Color borderColor = Color.BLACK;
    private Color transparentColor = new Color(0, 0, 0, 0);

    // Visual values
    private int borderThickness = 8;
    private int lineThickness = 8;
    private int width = 1200;
    private int height = 175;
    private int offset;

    // Get values from a method in the MC's themselves, For now will be preset
    private String[] names = new String[4];// = {"MC1", "MC2", "MC3", "MC4"};
    private String[] specialties = new String[4];// = {"Thief", "Mad Scientist", "Weapons Dealer", "Explosive Expert"};

    // Get hp, int, str from a method in the MC's themselves, For now will be preset
    private String[] currentHPs = new String[4];// = {String.valueOf(10), String.valueOf(9), String.valueOf(5), String.valueOf(0)};
    private String[] maxHPs = new String[4];// = {String.valueOf(10), String.valueOf(10), String.valueOf(10), String.valueOf(10)};
    private String[] intelligences = new String[4];// = {String.valueOf(5), String.valueOf(5), String.valueOf(5), String.valueOf(5)};
    private String[] strs = new String[4];// = {String.valueOf(10), String.valueOf(10), String.valueOf(10), String.valueOf(10)};
    private String[] lucks = new String[4];
    
    // Get values from Schedule, variation during job time
    private String[] actions = new String[4]; 

    // The capacity of the array is the amount of text to be stored
    private SuperTextBox[] displayNames = new SuperTextBox[4];
    private SuperTextBox[] displaySpecialties = new SuperTextBox[4];
    private SuperTextBox[] displayHPs = new SuperTextBox[4];
    private SuperTextBox[] displayInts = new SuperTextBox[4];
    private SuperTextBox[] displayStrs = new SuperTextBox[4];
    private SuperTextBox[] displayActions = new SuperTextBox[4];
    private SuperTextBox[] displayLucks = new SuperTextBox[4];

    // Tell me when to update status (after any value/stat change);
    private static boolean update = false;
    int a = 0;
    public StatusBar(){
        redraw();
    }

    public void addedToWorld(World w){
        update = true;
    }    

    public void act()
    {
        if(update){
            updateStatus();
        }
    }
    // Fill SuperTextBox with all possible names
    
    private void updateStatus(){
        // Cover old values
        clear();

        // Get new values
        trackValues();

        // Loop through all 4 boxes in the status Bar
        for(int i = 0; i < 4; i++){
            // Character Images (TempBox for now)
            TempBox characterBox = new TempBox(70, 80);
            getWorld().addObject(characterBox, 75 + (300 * i), 752);

            // Item Images (TempBox for now)
            TempBox itemBox1 = new TempBox(50, 50);
            TempBox itemBox2 = new TempBox(50, 50);
            getWorld().addObject(itemBox1, 170 + (300 * i), 780);
            getWorld().addObject(itemBox2, 245 + (300 * i), 780);

            // Names
            displayNames[i] = new SuperTextBox(names[i], transparentColor, Color.WHITE, SimulationFont.loadCustomFont("VT323-Regular.ttf", 20), true, 100, 0, Color.BLACK); 
            getWorld().addObject(displayNames[i], 75 + (300 * i), 695);

            // Character
            displaySpecialties[i] = new SuperTextBox(specialties[i], transparentColor, Color.WHITE, SimulationFont.loadCustomFont("VT323-Regular.ttf", 20), true, 200, 0, Color.BLACK); 
            getWorld().addObject(displaySpecialties[i], 75 + (300 * i), 805);

            // HP
            displayHPs[i] = new SuperTextBox("HP: " + currentHPs[i] + "/" + maxHPs[i], transparentColor, Color.WHITE, SimulationFont.loadCustomFont("VT323-Regular.ttf", 20), false, 100, 0, Color.BLACK);
            getWorld().addObject(displayHPs[i], 189 + (300 * i), 695);
            
            // Int
            displayInts[i] = new SuperTextBox("INT: " + intelligences[i], transparentColor, Color.WHITE, SimulationFont.loadCustomFont("VT323-Regular.ttf", 20), false, 100, 0, Color.BLACK);
            getWorld().addObject(displayInts[i], 187 + (300 * i), 710);

            // Str
            displayStrs[i] = new SuperTextBox("STR: " + strs[i], transparentColor, Color.WHITE, SimulationFont.loadCustomFont("VT323-Regular.ttf", 20), false, 100, 0, Color.BLACK);
            getWorld().addObject(displayStrs[i], 188 + (300 * i), 725);
            
            // Luck
            displayLucks[i] = new SuperTextBox("Luck: " + lucks[i], transparentColor, Color.WHITE, SimulationFont.loadCustomFont("VT323-Regular.ttf", 20), false, 100, 0, Color.BLACK);
            getWorld().addObject(displayLucks[i], 188 + (300 * i), 740);
            

            // Current Action
            MyWorld world = (MyWorld) getWorld();
            Schedule schedule = world.getSchedule();
            actions[i] = schedule.getCurrentEvent();
            displayActions[i] = new SuperTextBox(actions[i], transparentColor, Color.WHITE, SimulationFont.loadCustomFont("VT323-Regular.ttf", 20), true, 200, 0, Color.BLACK);
            getWorld().addObject(displayActions[i], 150 + (300 * i), 825);
            
        }
        update = false;
    }
    

    public static void setUpdate(boolean state){
        update = state;
    }

    private void redraw(){
        GreenfootImage background = new GreenfootImage(width, height);
        // Draw background
        background.setColor(bgColor);
        background.fill();
        // Draw border
        background.setColor(borderColor);
        for (int i = 0; i < borderThickness; i++){
            background.drawRect (0 + i, 0 + i, width - 1 - (i * 2), height - 1 - (i*2));
        }
        // Draw lines that seperate characters
        for(int i = 1; i < 4; i++){
            background.drawRect(((width/4)*i - lineThickness/2), 0, lineThickness, height); 
            background.fillRect(((width/4)*i - lineThickness/2), 0, lineThickness, height); 
        }

        setImage(background);
    }
    // This method is for actually getting real values from prisoners, won't work now due to lack of methods

    private void trackValues(){
        // Prisoners
        // From MyWorld, get an array of MCs in the world
        MyWorld world = (MyWorld) getWorld();
        MC[] MCs = world.getMainPrisoners();
        for(int i = 0; i < 4; i++){
            names[i] = MCs[i].getName();
            specialties[i] = MCs[i].getSpecialty();
            currentHPs[i] = String.valueOf(MCs[i].getHealth());
            maxHPs[i] = String.valueOf(MCs[i].getMaxHealth());
            intelligences[i] = String.valueOf(MCs[i].getIntel());
            strs[i] = String.valueOf(MCs[i].getStrength());
            lucks[i] = String.valueOf(MCs[i].getLuck());
        }

        // Items
        // Don't know how they work so this is blank for now
    }

    // Clear all text
    private void clear(){
        for(SuperTextBox text : getIntersectingObjects(SuperTextBox.class)){
            getWorld().removeObject(text);
        }
    }
}
