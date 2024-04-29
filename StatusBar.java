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
    
    public StatusBar(){
        redraw();
    }

    public void addedToWorld(World w){
        update = true;
        // For character images, will only be done once as characters won't change
        // Don't need to put in trackValues() method
        MyWorld world = (MyWorld) getWorld();
        MCs = world.getMainPrisoners();
        int iteration = 0;
        // Depending on specialty, have a different image for each MC
        for(MC m : MCs){
            if(m.getSpecialty().equals("Thief")){
                // Need to create a class to hold this image with setImage()
                // ImageHolder characterPhoto = new ImageHolder("thief");
                // world.addObject(characterPhoto, x + (300 * iteration), y);
            } else if (m.getSpecialty().equals("Brute")){
                
            } else if (m.getSpecialty().equals("Scientist")){
                
            } else if (m.getSpecialty().equals("Weapons Dealer")){
                
            } else if (m.getSpecialty().equals("Explosive Expert")){
                
            } else {
                
            }
            iteration++;
        }
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
            TempBox characterBox = new TempBox(70, 80, boxColor, borderColor, 3);
            getWorld().addObject(characterBox, 75 + (300 * i), 752);

            // Item Images (TempBox for now)
            TempBox itemBox1 = new TempBox(50, 50, boxColor, borderColor, 3);
            TempBox itemBox2 = new TempBox(50, 50, boxColor, borderColor, 3);
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
            MyWorld world = (MyWorld) getWorld();
            Schedule schedule = world.getSchedule();
            actions[i] = schedule.getCurrentEvent();
            displayActions[i] = new SuperTextBox(actions[i], transparentColor, textColor, SimulationFont.loadCustomFont("VT323-Regular.ttf", 20), true, 200, 0, Color.BLACK);
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
        }
    }

    // Clear all text
    private void clear(){
        for(SuperTextBox text : getIntersectingObjects(SuperTextBox.class)){
            getWorld().removeObject(text);
        }
        for(TempBox box : getIntersectingObjects(TempBox.class)){
            getWorld().removeObject(box);
        }
    }
    
}
