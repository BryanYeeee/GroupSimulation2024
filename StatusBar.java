import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Display for various stats and items in the main world.
 * Note: Does not auto update, it will update after being told/set to update.
 * 
 * @author Jamison H
 * @version April 2024
 */
public class StatusBar extends Actor
{
    // Background
    private GreenfootImage background;

    // Colors, 4th parameter = transparency from 0 (transparent) - 255 (opaque)
    private Color bgColor = new Color(119, 136, 153, 200);
    private Color borderColor = new Color(192, 192, 192);
    private Color transparentColor = new Color(0, 0, 0, 0);
    private Color textColor = new Color(250, 249, 246); //Color.WHITE;
    private Color boxColor = new Color(105, 105, 105);

    // Visual values
    private int borderThickness = 8;
    private int lineThickness = 8;
    private int width = 1200;
    private int height = 175;

    // Get values from a method in the MC's themselves, For now will be preset
    private String[] names = new String[4];// = {"MC1", "MC2", "MC3", "MC4"};
    private String[] specialties = new String[4];// = {"Thief", "Mad Scientist", "Weapons Dealer", "Explosive Expert"};
    private Color[] colors = {new Color(255,64,56), new Color(255,231,22),new Color(34,121,227), new Color(45,247,38)};
    // Get hp, int, str from a method in the MC's themselves, For now will be preset
    private String[] currentHPs = new String[4];// = {String.valueOf(10), String.valueOf(9), String.valueOf(5), String.valueOf(0)};
    private String[] maxHPs = new String[4];// = {String.valueOf(10), String.valueOf(10), String.valueOf(10), String.valueOf(10)};
    private String[] intelligences = new String[4];// = {String.valueOf(5), String.valueOf(5), String.valueOf(5), String.valueOf(5)};
    private String[] strs = new String[4];// = {String.valueOf(10), String.valueOf(10), String.valueOf(10), String.valueOf(10)};
    
    // Get values from Schedule, variation during job time
    private String[] actions = new String[4]; 

    // The capacity of the array is the amount of text to be stored
    private SuperTextBox[] displayNames = new SuperTextBox[4];
    private SuperTextBox[] displaySpecialties = new SuperTextBox[4];
    private SuperTextBox[] displayHPs = new SuperTextBox[4];
    private SuperTextBox[] displayInts = new SuperTextBox[4];
    private SuperTextBox[] displayStrs = new SuperTextBox[4];
    private SuperTextBox[] displayActions = new SuperTextBox[4];

    // Tell me when to update status (after any value/stat change);
    private static boolean update = false;

    // Array of MCs in the world
    private MC[] MCs;
    /**
     * Constructor for StatusBar, draw the background image and borders.
     */
    public StatusBar(){
        redraw();
    }

    /**
     * Update the values of the bar once added to the world
     * 
     * @param w     The world being added to.
     */
    public void addedToWorld(World w){
        update = true;       
    }    

    /**
     * The act method of the StatusBar, update values once told to.
     */
    public void act()
    {
        if(update){
            updateStatus();
        }
    }
    
    /**
     * Update values of the bar by adding text to the world.
     */
    private void updateStatus(){
        // Cover old values
        clear();

        // Get new values
        trackValues();
        
        // MC[] of MCs in the World
        MCs = ((MyWorld)getWorld()).getMainPrisoners();
        
        // Loop through all 4 boxes in the status Bar
        for(int i = 0; i < 4; i++){
            
            // Character Images 
            TempBox characterBox = new TempBox(90, 90, boxColor, colors[i], 3);
            getWorld().addObject(characterBox, 75 + (300 * i), 752);


            // Item Images 
            TempBox itemBox1 = new TempBox(42, 42, boxColor, borderColor, 3);
            TempBox itemBox2 = new TempBox(42, 42, boxColor, borderColor, 3);
            getWorld().addObject(itemBox1, 170 + (300 * i), 780);
            getWorld().addObject(itemBox2, 245 + (300 * i), 780);

            // Names
            displayNames[i] = new SuperTextBox(names[i], transparentColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 20), true, 100, 0, Color.BLACK); 
            getWorld().addObject(displayNames[i], 75 + (300 * i), 695);

            // Character
            displaySpecialties[i] = new SuperTextBox(specialties[i], transparentColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 20), true, 200, 0, Color.BLACK); 
            getWorld().addObject(displaySpecialties[i], 75 + (300 * i), 805);

            // HP
            displayHPs[i] = new SuperTextBox("HP: " + currentHPs[i] + "/" + maxHPs[i], transparentColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 20), false, 100, 0, Color.BLACK);
            getWorld().addObject(displayHPs[i], 189 + (300 * i), 695);

            // Int
            displayInts[i] = new SuperTextBox("INT: " + intelligences[i], transparentColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 20), false, 100, 0, Color.BLACK);
            getWorld().addObject(displayInts[i], 187 + (300 * i), 715);

            // Str
            displayStrs[i] = new SuperTextBox("STR: " + strs[i], transparentColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 20), false, 100, 0, Color.BLACK);
            getWorld().addObject(displayStrs[i], 188 + (300 * i), 735);

            // Current Action
            actions[i] = MCs[i].getCurrentAction();
            if(actions[i] == null){
                actions[i] = "Roaming Around";
            }
            displayActions[i] = new SuperTextBox(actions[i], transparentColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 20), true, 200, 0, Color.BLACK);
            getWorld().addObject(displayActions[i], 150 + (300 * i), 825);
        }
        
        // Things that don't need iteration to complete down here
        // Depending on specialty, have a different image for each MC
        SavedPrisoner image1 = new SavedPrisoner(true,MCs[0].getSpecialty());
        SavedPrisoner image2 = new SavedPrisoner(true,MCs[1].getSpecialty());
        SavedPrisoner image3 = new SavedPrisoner(true,MCs[2].getSpecialty());
        SavedPrisoner image4 = new SavedPrisoner(true,MCs[3].getSpecialty());

        getWorld().addObject(image1, 75, 752);
        getWorld().addObject(image2, 375, 752);
        getWorld().addObject(image3, 675, 752);
        getWorld().addObject(image4, 975, 752);
        
        // Current Action
        update = false;
    }

    /**
     * Set when the StatusBar should update.
     * 
     * @param state     Typically true, setting the bar to update, false means no update happens.
     */
    public static void setUpdate(boolean state){
        update = state;
    }

    /**
     * Draw the background and borders of the StatusBar.
     */
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
    
    /**
     * Track the values of the main prisoners within the simluation world.
     */
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
        }
    }

    /**
     * Clear the StatusBar of text and background boxes (will be readded in updateStatus).
     * This is used to stop text being drawn over each other and too many TempBoxes being in the world causing lag.
     */
    private void clear(){
        for(SuperTextBox text : getIntersectingObjects(SuperTextBox.class)){
            getWorld().removeObject(text);
        }
        for(TempBox box : getIntersectingObjects(TempBox.class)){
            getWorld().removeObject(box);
        }
    }

}
